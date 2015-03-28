package org.spl.binder;

import org.spl.binder.exception.IncorrectTypeDeclarationException;
import org.spl.binder.exception.MultipleDeclarationException;
import org.spl.binder.exception.UndeclaredUseException;
import org.spl.common.Nonterminal;
import org.spl.common.PredefinedFunctions;
import org.spl.common.Symbol;
import org.spl.common.Token;
import org.spl.common.structure.BindingObject;
import org.spl.common.structure.FunctionObject;
import org.spl.common.structure.ScopeObject;
import org.spl.common.structure.VariableObject;
import org.spl.common.type.Type;

import org.spl.common.ASTNode;

import java.util.Enumeration;
import java.util.Stack;

public class Binder {

    private Stack<ScopeObject> m_stack;

    public Binder() {
        m_stack = new Stack<ScopeObject>();
    }

    private VariableObject parseVariableDeclaration(ASTNode parent)
            throws IncorrectTypeDeclarationException {
        Symbol parentSymbol = parent.getSymbol();

        if (parentSymbol != Nonterminal.GlobalVarDecl &&
                parentSymbol != Nonterminal.DeclSingleArg &&
                parentSymbol != Nonterminal.VarDecl) {
            throw new RuntimeException();
        }

        ASTNode typeNode = (ASTNode) parent.getChildAt(0);
        ASTNode identifierNode = (ASTNode) parent.getChildAt(1);

        Type type = TypeFactory.buildForDeclaration(typeNode);
        VariableObject variableObject = new VariableObject(type, identifierNode.getString());
        variableObject.setNode(identifierNode);

        identifierNode.setType(type);

        return variableObject;
    }

    private FunctionObject parseFunctionDeclaration(ASTNode parent, ScopeObject globalScopeObject)
            throws MultipleDeclarationException, IncorrectTypeDeclarationException {
        Symbol parentSymbol = parent.getSymbol();

        if (parentSymbol != Nonterminal.FuncDecl) {
            throw new RuntimeException();
        }

        ASTNode typeNode = (ASTNode) parent.getChildAt(0);
        ASTNode identifierNode = (ASTNode) parent.getChildAt(1);

        Type type = TypeFactory.buildForDeclaration(typeNode);
        FunctionObject functionObject = new FunctionObject(type, identifierNode.getString(), globalScopeObject, parent);
        functionObject.setNode(identifierNode);

        // Adds function variables to the scope if applicable
        ASTNode args = (ASTNode) parent.getChildAt(2);
        Symbol argsSymbol = args != null ? args.getSymbol() : null;
        if (argsSymbol == Nonterminal.DeclArgs) {
            for (Enumeration e = args.children(); e.hasMoreElements(); ) {
                VariableObject argument = parseVariableDeclaration((ASTNode) e.nextElement());

                if (!functionObject.getArguments().contains(argument)) {
                    functionObject.addArgument(argument);
                } else {
                    throw new MultipleDeclarationException(argument.getIdentifier(), argument.getNode());
                }
            }
        }

        return functionObject;
    }

    private void processFunctions(ASTNode node)
            throws MultipleDeclarationException, UndeclaredUseException, IncorrectTypeDeclarationException {
        Symbol nodeSymbol = node.getSymbol();
        Token nodeToken = node.getToken();
        String nodeString = node.getString();

        ASTNode parent = (ASTNode) node.getParent();
        Symbol parentSymbol = parent != null ? parent.getSymbol() : null;


        // 1. Creates a new scope (function, if, else, while) to be processed
        // 2. Binds variable declarations
        // 3. Binds variable/function uses
        // 4. Sets return statement node to the scope
        if (nodeSymbol == Nonterminal.Body) {
            ScopeObject scopeObject;

            // 1. Parses function declaration
            // 2. Parses else statement
            if (parentSymbol == Nonterminal.FuncDecl) {
                // Adds function variables to the scope if applicable
                ASTNode identifier = (ASTNode) parent.getChildAt(1);

                if (identifier != null) {
                    FunctionObject functionObject = (FunctionObject) m_stack.peek().findByIdentifier(identifier.getString());
                    scopeObject = functionObject.getScopeObject();
                } else {
                    throw new RuntimeException();
                }
            } else if (parentSymbol == Nonterminal.ElseStmt) {
                try {
                    ASTNode ifBody = (ASTNode) parent.getPreviousSibling();
                    scopeObject = new ScopeObject(ifBody.getScopeObject(), (Nonterminal) parentSymbol);
                } catch (ClassCastException e) {
                    throw new RuntimeException();
                }
            } else {
                try {
                    scopeObject = new ScopeObject(m_stack.peek(), (Nonterminal) parentSymbol);
                } catch (ClassCastException e) {
                    throw new RuntimeException();
                }
            }

            m_stack.push(scopeObject);
        } else if (nodeSymbol == Nonterminal.VarDecl) {
            ScopeObject scopeObject = m_stack.peek();
            VariableObject declaration = parseVariableDeclaration(node);

            // Adds declaration to scope object
            if (!scopeObject.addDeclaration(declaration)) {
                throw new MultipleDeclarationException(declaration.getIdentifier(), declaration.getNode());
            }
        } else if (nodeToken == Token.IDENTIFIER &&
                nodeSymbol != Nonterminal.PolymorphicType &&
                parentSymbol != Nonterminal.GlobalVarDecl &&
                parentSymbol != Nonterminal.DeclSingleArg &&
                parentSymbol != Nonterminal.VarDecl &&
                parentSymbol != Nonterminal.TupleType) {
            BindingObject bindingObject = m_stack.peek().findByIdentifier(nodeString);

            if (bindingObject != null) {
                node.setType(bindingObject.getType());
            } else {
                throw new UndeclaredUseException(nodeString, node);
            }
        } else if (nodeSymbol == Nonterminal.ReturnStmt) {
            ScopeObject scopeObject = m_stack.peek();
            if (!scopeObject.containsReturnStatement()) {
                scopeObject.setReturnStatementNode(node);
            }
        }

        // Iterates over children
        for (Enumeration e = node.children(); e.hasMoreElements(); ) {
            processFunctions((ASTNode) e.nextElement());
        }

        // Sets scope object to the current node
        node.setScopeObject(m_stack.peek());

        // Removes last scope from the stack
        if (nodeSymbol == Nonterminal.Body) {
            m_stack.pop();
        }
    }

    private void processGlobal(ASTNode root)
            throws MultipleDeclarationException, IncorrectTypeDeclarationException {
        Symbol rootSymbol = root.getSymbol();

        if (rootSymbol != Nonterminal.SPL) {
            throw new RuntimeException();
        }

        // Creates a global scope with predefined functions included
        ScopeObject globalScopeObject = new ScopeObject(PredefinedFunctions.all());
        root.setScopeObject(globalScopeObject);

        // Iterates over children
        for (Enumeration e = root.children(); e.hasMoreElements(); ) {
            ASTNode child = (ASTNode) e.nextElement();
            Symbol childSymbol = child.getSymbol();

            if (childSymbol == Nonterminal.GlobalVarDecl) {
                VariableObject variableObject = parseVariableDeclaration(child);

                if (!globalScopeObject.addDeclaration(variableObject)) {
                    throw new MultipleDeclarationException(variableObject.getIdentifier(), variableObject.getNode());
                }
            } else if (childSymbol == Nonterminal.FuncDecl) {
                FunctionObject functionObject = parseFunctionDeclaration(child, globalScopeObject);

                if (!globalScopeObject.addDeclaration(functionObject)) {
                    throw new MultipleDeclarationException(functionObject.getIdentifier(), functionObject.getNode());
                }
            }
        }

        // Pushes global scope object to the stack
        m_stack.push(globalScopeObject);
    }

    public void run(ASTNode root)
            throws MultipleDeclarationException, UndeclaredUseException, IncorrectTypeDeclarationException {
        processGlobal(root);
        processFunctions(root);
    }
}
