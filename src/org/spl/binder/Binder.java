package org.spl.binder;

import org.spl.binder.exception.IncorrectTypeDeclarationException;
import org.spl.binder.exception.MultipleDeclarationException;
import org.spl.binder.exception.UndeclaredUseException;
import org.spl.codegenerator.CodeGeneratorMaps;
import org.spl.common.Nonterminal;
import org.spl.common.PredefinedFunctions;
import org.spl.common.Symbol;
import org.spl.common.Token;
import org.spl.common.structure.*;
import org.spl.common.type.Type;

import org.spl.common.ASTNode;

import java.util.Enumeration;
import java.util.Stack;

public class Binder {

    private Stack<Scope> m_stack;

    public Binder() {
        m_stack = new Stack<Scope>();
    }

    private VariableDeclaration parseFunctionArgument(ASTNode node, Scope scope)
            throws IncorrectTypeDeclarationException {
        Symbol nodeSymbol = node.getSymbol();

        if (nodeSymbol != Nonterminal.DeclSingleArg) {
            throw new RuntimeException();
        }

        ASTNode typeNode = (ASTNode) node.getChildAt(0);
        ASTNode identifierNode = (ASTNode) node.getChildAt(1);

        Type type = TypeFactory.buildForDeclaration(typeNode);
        identifierNode.setType(type);

        VariableDeclaration variableDeclaration = new VariableDeclaration(type, identifierNode.getString(), false);
        variableDeclaration.setNode(identifierNode);

        return variableDeclaration;
    }

    private VariableDeclaration parseVariableDeclaration(ASTNode node, Scope scope, boolean isGlobal)
            throws IncorrectTypeDeclarationException {
        Symbol nodeSymbol = node.getSymbol();

        if (nodeSymbol != Nonterminal.GlobalVarDecl && nodeSymbol != Nonterminal.VarDecl) {
            throw new RuntimeException();
        }

        ASTNode typeNode = (ASTNode) node.getChildAt(0);
        ASTNode identifierNode = (ASTNode) node.getChildAt(1);

        Type type = TypeFactory.buildForDeclaration(typeNode);
        identifierNode.setType(type);

        VariableDeclaration variableDeclaration = new VariableDeclaration(type, identifierNode.getString(), isGlobal);
        variableDeclaration.setNode(identifierNode);

        return variableDeclaration;
    }

    private FunctionDeclaration parseFunctionDeclaration(ASTNode node, Scope globalScope)
            throws MultipleDeclarationException, IncorrectTypeDeclarationException {
        Symbol nodeSymbol = node.getSymbol();

        if (nodeSymbol != Nonterminal.FuncDecl) {
            throw new RuntimeException();
        }

        ASTNode typeNode = (ASTNode) node.getChildAt(0);
        ASTNode identifierNode = (ASTNode) node.getChildAt(1);
        ASTNode argsNode = (ASTNode) node.getChildAt(2);
        ASTNode bodyNode = (ASTNode) node.getChildAt(3);

        Type type = TypeFactory.buildForDeclaration(typeNode);
        FunctionDeclaration functionDeclaration = new FunctionDeclaration(type, identifierNode.getString(), globalScope, node);
        functionDeclaration.setNode(identifierNode);

        Scope scope = functionDeclaration.getScopeObject();

        // Adds function variables to the scope if applicable
        Symbol argsSymbol = argsNode != null ? argsNode.getSymbol() : null;
        if (argsSymbol == Nonterminal.DeclArgs) {
            for (Enumeration e = argsNode.children(); e.hasMoreElements(); ) {
                VariableDeclaration argument = parseFunctionArgument((ASTNode) e.nextElement(), functionDeclaration.getScopeObject());

                if (!functionDeclaration.getArguments().contains(argument)) {
                    functionDeclaration.addArgument(argument);
                } else {
                    throw new MultipleDeclarationException(argument.getIdentifier(), argument.getNode());
                }
            }
        }

        return functionDeclaration;
    }

    private void processScopes(ASTNode node, Scope scope)
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
            Scope newScope;

            // 1. Parses function declaration
            // 2. Parses else statement
            if (parentSymbol == Nonterminal.FuncDecl) {
                // Adds function variables to the scope if applicable
                ASTNode identifier = (ASTNode) parent.getChildAt(1);

                if (identifier != null) {
                    FunctionDeclaration functionDeclaration = (FunctionDeclaration) m_stack.peek().findByIdentifier(identifier.getString());
                    newScope = functionDeclaration.getScopeObject();
                } else {
                    throw new RuntimeException();
                }
            } else if (parentSymbol == Nonterminal.ElseStmt) {
                try {
                    ASTNode ifBody = (ASTNode) parent.getPreviousSibling();
                    newScope = new Scope(ifBody.getScopeObject(), (Nonterminal) parentSymbol);
                } catch (ClassCastException e) {
                    throw new RuntimeException();
                }
            } else {
                try {
                    newScope = new Scope(m_stack.peek(), (Nonterminal) parentSymbol);
                } catch (ClassCastException e) {
                    throw new RuntimeException();
                }
            }

            m_stack.push(newScope);
        } else if (nodeSymbol == Nonterminal.VarDecl) {
            Scope newScope = m_stack.peek();
            VariableDeclaration declaration = parseVariableDeclaration(node, newScope, false);

            // Adds declaration to scope object
            if (!newScope.addDeclaration(declaration)) {
                throw new MultipleDeclarationException(declaration.getIdentifier(), declaration.getNode());
            }
        } else if (nodeToken == Token.IDENTIFIER &&
                nodeSymbol != Nonterminal.PolymorphicType &&
                parentSymbol != Nonterminal.GlobalVarDecl &&
                parentSymbol != Nonterminal.DeclSingleArg &&
                parentSymbol != Nonterminal.VarDecl &&
                parentSymbol != Nonterminal.TupleType) {
            StructureObject structureObject = m_stack.peek().findByIdentifier(nodeString);

            if (structureObject != null) {
                node.setType(structureObject.getType());
            } else {
                throw new UndeclaredUseException(nodeString, node);
            }
        } else if (nodeSymbol == Nonterminal.ReturnStmt) {
            Scope newScope = m_stack.peek();
            if (!newScope.containsReturnStatement()) {
                newScope.setReturnStatementNode(node);
            }
        }

        // Iterates over children
        for (Enumeration e = node.children(); e.hasMoreElements(); ) {
            processScopes((ASTNode) e.nextElement(), m_stack.peek());
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
        Scope globalScope = new Scope(PredefinedFunctions.all());
        root.setScopeObject(globalScope);

        // Iterates over children
        for (Enumeration e = root.children(); e.hasMoreElements(); ) {
            ASTNode child = (ASTNode) e.nextElement();
            Symbol childSymbol = child.getSymbol();

            if (childSymbol == Nonterminal.GlobalVarDecl) {
                VariableDeclaration variableDeclaration = parseVariableDeclaration(child, globalScope, true);
//                globalScope.addStructure(variableDeclaration);

                if (!globalScope.addDeclaration(variableDeclaration)) {
                    throw new MultipleDeclarationException(variableDeclaration.getIdentifier(), variableDeclaration.getNode());
                }
            } else if (childSymbol == Nonterminal.FuncDecl) {
                FunctionDeclaration functionDeclaration = parseFunctionDeclaration(child, globalScope);
//                globalScope.addStructure(functionDeclaration);

                if (!globalScope.addDeclaration(functionDeclaration)) {
                    throw new MultipleDeclarationException(functionDeclaration.getIdentifier(), functionDeclaration.getNode());
                }
            }
        }

        // Pushes global scope object to the stack
        m_stack.push(globalScope);
    }

    public Scope run(ASTNode root)
            throws MultipleDeclarationException, UndeclaredUseException, IncorrectTypeDeclarationException {
        processGlobal(root);
        processScopes(root, m_stack.peek());

        IRBuilder irBuilder = new IRBuilder();
        irBuilder.run(root);

        return m_stack.peek();
    }
}
