package org.spl.binder;

import org.spl.codegenerator.CodeGeneratorMaps;
import org.spl.common.*;
import org.spl.common.structure.*;
import org.spl.common.structure.FunctionCall;

import java.util.Enumeration;
import java.util.Stack;

public class IRBuilder {

    private Stack<Scope> m_stack;

    public IRBuilder() {
        m_stack = new Stack<Scope>();
    }

    private WhileStatement parseWhileStatement(ASTNode node, Scope parentScope) {
        Symbol nodeSymbol = node.getSymbol();

        if (nodeSymbol != Nonterminal.WhileStmt) {
            throw new RuntimeException();
        }

        ASTNode conditionalExpressionNode = (ASTNode) node.getChildAt(1);
        ASTNode bodyNode = (ASTNode) node.getChildAt(2);

        Expression conditionalExpression = parseExpression(conditionalExpressionNode, parentScope);

        WhileStatement whileStatement = new WhileStatement(conditionalExpression, parentScope);
        Scope scope = whileStatement.getScope();

        // Iterates over children
        for (Enumeration e = bodyNode.children(); e.hasMoreElements(); ) {
            ASTNode child = (ASTNode) e.nextElement();
            Symbol childSymbol = child.getSymbol();

            if (childSymbol == Nonterminal.FuncCall) {
                scope.addStructure(parseFunctionCall(child, scope));
            } else if (childSymbol == Nonterminal.Stmt) {
                scope.addStructure(parseStatement(child, scope));
            } else if (childSymbol == Nonterminal.IfStmt) {
                scope.addStructure(parseIfStatement(child, scope));
            } else if (childSymbol == Nonterminal.WhileStmt) {
                scope.addStructure(parseWhileStatement(child, scope));
            } else if (childSymbol == Nonterminal.ReturnStmt) {
                scope.addStructure(parseReturnStatement(child, scope));
            }
        }

        return whileStatement;
    }

    private ElseStatement parseElseStatement(ASTNode node, Scope parentScope) {
        Symbol nodeSymbol = node.getSymbol();

        if (nodeSymbol != Nonterminal.ElseStmt) {
            throw new RuntimeException();
        }

        ASTNode bodyNode = (ASTNode) node.getChildAt(1);

        ElseStatement elseStatement = new ElseStatement(parentScope);
        Scope scope = elseStatement.getScope();

        // Iterates over children
        for (Enumeration e = bodyNode.children(); e.hasMoreElements(); ) {
            ASTNode child = (ASTNode) e.nextElement();
            Symbol childSymbol = child.getSymbol();

            if (childSymbol == Nonterminal.FuncCall) {
                scope.addStructure(parseFunctionCall(child, scope));
            } else if (childSymbol == Nonterminal.Stmt) {
                scope.addStructure(parseStatement(child, scope));
            } else if (childSymbol == Nonterminal.IfStmt) {
                scope.addStructure(parseIfStatement(child, scope));
            } else if (childSymbol == Nonterminal.WhileStmt) {
                scope.addStructure(parseWhileStatement(child, scope));
            } else if (childSymbol == Nonterminal.ReturnStmt) {
                scope.addStructure(parseReturnStatement(child, scope));
            }
        }

        return elseStatement;
    }

    private IfStatement parseIfStatement(ASTNode node, Scope parentScope) {
        Symbol nodeSymbol = node.getSymbol();

        if (nodeSymbol != Nonterminal.IfStmt) {
            throw new RuntimeException();
        }

        ASTNode conditionalExpressionNode = (ASTNode) node.getChildAt(1);
        ASTNode bodyNode = (ASTNode) node.getChildAt(2);
        ASTNode elseNode = node.getChildCount() == 4 ? (ASTNode) node.getChildAt(3) : null;

        Expression conditionalExpression = parseExpression(conditionalExpressionNode, parentScope);

        IfStatement ifStatement = new IfStatement(conditionalExpression, parentScope);
        Scope scope = ifStatement.getScope();

        // Iterates over children
        for (Enumeration e = bodyNode.children(); e.hasMoreElements(); ) {
            ASTNode child = (ASTNode) e.nextElement();
            Symbol childSymbol = child.getSymbol();

            if (childSymbol == Nonterminal.FuncCall) {
                scope.addStructure(parseFunctionCall(child, scope));
            } else if (childSymbol == Nonterminal.Stmt) {
                scope.addStructure(parseStatement(child, scope));
            } else if (childSymbol == Nonterminal.IfStmt) {
                scope.addStructure(parseIfStatement(child, scope));
            } else if (childSymbol == Nonterminal.WhileStmt) {
                scope.addStructure(parseWhileStatement(child, scope));
            } else if (childSymbol == Nonterminal.ReturnStmt) {
                scope.addStructure(parseReturnStatement(child, scope));
            }
        }

        if (elseNode != null) {
            ifStatement.setElseStatement(parseElseStatement(elseNode, scope));
        }

        return ifStatement;
    }

    private ReturnStatement parseReturnStatement(ASTNode node, Scope scope) {
        Symbol nodeSymbol = node.getSymbol();

        if (nodeSymbol != Nonterminal.ReturnStmt) {
            throw new RuntimeException();
        }

        ASTNode expressionNode = (ASTNode) node.getChildAt(1);
        return new ReturnStatement(parseExpression(expressionNode, scope), scope);
    }

    private Assignment parseStatement(ASTNode node, Scope scope) {
        Symbol nodeSymbol = node.getSymbol();

        if (nodeSymbol != Nonterminal.Stmt) {
            throw new RuntimeException();
        }

        ASTNode identifierNode = (ASTNode) node.getChildAt(0);
        ASTNode assignmentNode = (ASTNode) node.getChildAt(1);
        ASTNode expressionNode = (ASTNode) node.getChildAt(2);

        if (assignmentNode.getToken() != Token.OPERATOR_ASSIGN) {
            throw new RuntimeException();
        }

        VariableDeclaration variableDeclaration = (VariableDeclaration) scope.findByIdentifier(identifierNode.getString());

        Expression expression = parseExpression(expressionNode, scope);

        return new Assignment(variableDeclaration, expression);
    }

    private Expression parseCallArgument(ASTNode node, Scope scope) {
        Symbol nodeSymbol = node.getSymbol();

        if (nodeSymbol != Nonterminal.CallSingleArg) {
            throw new RuntimeException();
        }

        ASTNode expressionNode = (ASTNode) node.getChildAt(0);
        return parseExpression(expressionNode, scope);
    }

    private FunctionCall parseFunctionCall(ASTNode node, Scope scope) {
        Symbol nodeSymbol = node.getSymbol();

        if (nodeSymbol != Nonterminal.FuncCall) {
            throw new RuntimeException();
        }

        ASTNode identifierNode = (ASTNode) node.getChildAt(0);
        ASTNode callArguments = (ASTNode) node.getChildAt(1);

        FunctionDeclaration functionDeclaration = (FunctionDeclaration) scope.findByIdentifier(identifierNode.getString());

        FunctionCall functionCall = new FunctionCall(functionDeclaration);

        // Iterates over children
        for (Enumeration e = callArguments.children(); e.hasMoreElements(); ) {
            ASTNode child = (ASTNode) e.nextElement();
            functionCall.addArgument(parseCallArgument(child, scope));
        }

        return functionCall;
    }

    private Expression parseExpression(ASTNode node, Scope scope) {
        Symbol nodeSymbol = node.getSymbol();
        Symbol nodeToken = node.getToken();
        String nodeString = node.getString();

        if (nodeSymbol != Nonterminal.Exp &&
                nodeSymbol != Nonterminal.TupleExp &&
                nodeSymbol != Nonterminal.ConditionalExp &&
                nodeSymbol != Nonterminal.FuncCall &&
                nodeSymbol != Token.NUMERIC &&
                nodeSymbol != Token.IDENTIFIER) {
            throw new RuntimeException();
        }

        if (nodeToken == Token.NUMERIC) {
            return new BasicConstant(node);
        } else if (nodeToken == Token.BOOL_TRUE || nodeToken == Token.BOOL_FALSE) {
            return new BasicConstant(node);
        } else if (nodeToken == Token.CHARACTER) {
            return new BasicConstant(node);
        } else if (nodeToken == Token.EMPTY_ARRAY) {
            return new EmptyList(node);
        } else if (nodeSymbol == Nonterminal.FuncCall) {
            return parseFunctionCall(node, scope);
        } else if (nodeToken == Token.IDENTIFIER) {
            StructureObject structureObject = scope.findByIdentifier(nodeString);

            if (structureObject instanceof VariableDeclaration) {
                return new Variable((VariableDeclaration) structureObject);
            } else if (structureObject instanceof FunctionDeclaration) {
                return new FunctionCall((FunctionDeclaration) structureObject);
            } else {
                throw new RuntimeException();
            }
        }

        int operatorNodeIndex = node.getChildCount() == 3 ? 1 : 0;
        ASTNode operatorNode = (ASTNode) node.getChildAt(operatorNodeIndex);
        Token operatorNodeToken = operatorNode.getToken();

        ASTNode getterNode = (ASTNode) node.getChildAt(1);
        Symbol getterNodeSymbol = getterNode.getSymbol();

        Expression expression = new Expression(node);

        if (node.getChildCount() == 3 && CodeGeneratorMaps.binaryOperatorTokenMap.containsKey(operatorNodeToken)) {
            ASTNode leftExp = (ASTNode) node.getChildAt(0);
            ASTNode rightExp = (ASTNode) node.getChildAt(2);

            expression.addExpression(parseExpression(leftExp, scope));
            expression.addExpression(parseExpression(rightExp, scope));
            expression.setToken(operatorNodeToken);
        } else if (node.getChildCount() == 2 && CodeGeneratorMaps.unaryOperatorTokenMap.containsKey(operatorNodeToken)) {
            ASTNode exp = (ASTNode) node.getChildAt(1);

            expression.addExpression(parseExpression(exp, scope));
            expression.setToken(operatorNodeToken);
        } else if (node.getChildCount() == 3 && operatorNodeToken == Token.OPERATOR_CONCATENATE) {
            ASTNode leftExp = (ASTNode) node.getChildAt(0);
            ASTNode rightExp = (ASTNode) node.getChildAt(2);

            expression.addExpression(parseExpression(leftExp, scope));
            expression.addExpression(parseExpression(rightExp, scope));
            expression.setToken(operatorNodeToken);
        } else if (nodeSymbol == Nonterminal.TupleExp) {
            ASTNode leftExp = (ASTNode) node.getChildAt(0);
            ASTNode rightExp = (ASTNode) node.getChildAt(1);

            expression.addExpression(parseExpression(leftExp, scope));
            expression.addExpression(parseExpression(rightExp, scope));
            expression.setToken(Token.TUPLE_TYPE);
        } else if (node.getChildCount() == 2 && getterNodeSymbol == Nonterminal.Getter) {
            Token getterNodeToken = getterNode.getToken();

            FunctionDeclaration functionDeclaration;
            if (getterNodeToken == Token.GETTER_FIRST) {
                functionDeclaration = (FunctionDeclaration) scope.findByIdentifier("fst");
            } else if (getterNodeToken == Token.GETTER_SECOND) {
                functionDeclaration = (FunctionDeclaration) scope.findByIdentifier("snd");
            } else if (getterNodeToken == Token.GETTER_HEAD) {
                functionDeclaration = (FunctionDeclaration) scope.findByIdentifier("hd");
            } else if (getterNodeToken == Token.GETTER_TAIL) {
                functionDeclaration = (FunctionDeclaration) scope.findByIdentifier("tl");
            } else {
                throw new RuntimeException();
            }

            FunctionCall functionCall = new FunctionCall(functionDeclaration);
            functionCall.setToken(getterNodeToken);

            ASTNode leftExpression = (ASTNode) getterNode.getPreviousSibling();

            expression.addExpression(parseExpression(leftExpression, scope));
            expression.addExpression(functionCall);
            expression.setToken(getterNodeToken);
            expression.setParentToken(operatorNodeToken);
        }

        return expression;
    }

    private VariableDeclaration parseFunctionArgument(ASTNode node, Scope scope) {
        Symbol nodeSymbol = node.getSymbol();

        if (nodeSymbol != Nonterminal.DeclSingleArg) {
            throw new RuntimeException();
        }

        ASTNode identifierNode = (ASTNode) node.getChildAt(1);

        return (VariableDeclaration) scope.findByIdentifier(identifierNode.getString());
    }

    private VariableDeclaration parseVariableDeclaration(ASTNode node, Scope scope, boolean isGlobal) {
        Symbol nodeSymbol = node.getSymbol();

        if (nodeSymbol != Nonterminal.GlobalVarDecl && nodeSymbol != Nonterminal.VarDecl) {
            throw new RuntimeException();
        }

        ASTNode typeNode = (ASTNode) node.getChildAt(0);
        ASTNode identifierNode = (ASTNode) node.getChildAt(1);
        ASTNode expressionNode = (ASTNode) node.getChildAt(3);

        VariableDeclaration variableDeclaration = (VariableDeclaration) scope.findByIdentifier(identifierNode.getString());

        Expression expression = parseExpression(expressionNode, scope);
        Assignment assignment = new Assignment(variableDeclaration, expression);

        variableDeclaration.setDeclarationAssignment(assignment);

        return variableDeclaration;
    }

    private FunctionDeclaration parseFunctionDeclaration(ASTNode node, Scope globalScope) {
        Symbol nodeSymbol = node.getSymbol();

        if (nodeSymbol != Nonterminal.FuncDecl) {
            throw new RuntimeException();
        }

        ASTNode typeNode = (ASTNode) node.getChildAt(0);
        ASTNode identifierNode = (ASTNode) node.getChildAt(1);
        ASTNode argsNode = (ASTNode) node.getChildAt(2);
        ASTNode bodyNode = (ASTNode) node.getChildAt(3);

        FunctionDeclaration functionDeclaration = (FunctionDeclaration) globalScope.findByIdentifier(identifierNode.getString());
        functionDeclaration.setNode(identifierNode);

        Scope scope = functionDeclaration.getScopeObject();

        // Iterates over children
        for (Enumeration e = bodyNode.children(); e.hasMoreElements(); ) {
            ASTNode child = (ASTNode) e.nextElement();
            Symbol childSymbol = child.getSymbol();

            if (childSymbol == Nonterminal.VarDecl) {
                scope.addStructure(parseVariableDeclaration(child, scope, false));
            } else if (childSymbol == Nonterminal.FuncCall) {
                scope.addStructure(parseFunctionCall(child, scope));
            } else if (childSymbol == Nonterminal.Stmt) {
                scope.addStructure(parseStatement(child, scope));
            } else if (childSymbol == Nonterminal.ReturnStmt) {
                scope.addStructure(parseReturnStatement(child, scope));
            } else if (childSymbol == Nonterminal.IfStmt) {
                scope.addStructure(parseIfStatement(child, scope));
            } else if (childSymbol == Nonterminal.ElseStmt) {
                scope.addStructure(parseElseStatement(child, scope));
            } else if (childSymbol == Nonterminal.WhileStmt) {
                scope.addStructure(parseWhileStatement(child, scope));
            }
        }

        return functionDeclaration;
    }

    private void processGlobal(ASTNode root) {
        Symbol rootSymbol = root.getSymbol();

        if (rootSymbol != Nonterminal.SPL) {
            throw new RuntimeException();
        }

        Scope globalScope = root.getScopeObject();

        // Iterates over children
        for (Enumeration e = root.children(); e.hasMoreElements(); ) {
            ASTNode child = (ASTNode) e.nextElement();
            Symbol childSymbol = child.getSymbol();

            if (childSymbol == Nonterminal.GlobalVarDecl) {
                VariableDeclaration variableDeclaration = parseVariableDeclaration(child, globalScope, true);
                globalScope.addStructure(variableDeclaration);
            } else if (childSymbol == Nonterminal.FuncDecl) {
                FunctionDeclaration functionDeclaration = parseFunctionDeclaration(child, globalScope);
                globalScope.addStructure(functionDeclaration);
            }
        }

        // Pushes global scope object to the stack
        m_stack.push(globalScope);
    }

    public Scope run(ASTNode root) {
        processGlobal(root);
//        System.out.println(m_stack.peek().toString());
        return m_stack.peek();
    }
}
