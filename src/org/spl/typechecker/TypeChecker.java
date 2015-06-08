package org.spl.typechecker;

import org.spl.common.ASTNode;
import org.spl.common.structure.StructureObject;
import org.spl.common.FunctionCall;
import org.spl.common.structure.FunctionDeclaration;
import org.spl.common.structure.Scope;
import org.spl.common.type.TupleType;
import org.spl.binder.*;
import org.spl.common.Nonterminal;
import org.spl.common.Symbol;
import org.spl.common.Token;
import org.spl.common.type.Type;
import org.spl.typechecker.exception.*;

import java.util.ArrayList;
import java.util.Enumeration;

public class TypeChecker {

    private TypeCheckerPolymorphic m_typeCheckerPolymorphic;
    private ReturnPathChecker m_returnPathChecker;

    public TypeChecker() {
        m_typeCheckerPolymorphic = new TypeCheckerPolymorphic();
        m_returnPathChecker = new ReturnPathChecker();
    }

    private void processTupleExpression(ASTNode tupleExp) throws IncorrectTypeException {

        ASTNode first = (ASTNode) tupleExp.getChildAt(0);
        Type firstType = first.getType();

        ASTNode second = (ASTNode) tupleExp.getChildAt(1);
        Type secondType = second.getType();

//        System.out.println(firstType + " " + secondType);
        if (firstType == null) {
            throw new IncorrectTypeException(first);
        } else if (secondType == null) {
            throw new IncorrectTypeException(second);
        }

        tupleExp.setType(new TupleType(firstType, secondType));
    }

    private void checkCallSingleArg(ASTNode node) throws IncorrectTypeException {

        ASTNode child = (ASTNode) node.getChildAt(0);
        Type childType = child.getType();
        Type childTempType = child.getTempType();

        if (childType == null) {
            throw new IncorrectTypeException(node);
        }

        node.setType(childType)
                .setTempType(childTempType);
    }

    private void processFunctionCall(ASTNode call)
            throws IncorrectTypeException, IncorrectReturnTypeException, IncorrectFunctionCallArguments {
        Scope callScope = call.getScopeObject();

        ASTNode identifier = (ASTNode) call.getFirstChild();
        Type identifierType = identifier.getType();
        String identifierString = identifier.getString();

        if (identifierType == null) {
            throw new IncorrectTypeException(call);
        }

        ASTNode callArgs = (ASTNode) call.getChildAt(1);
        ArrayList<Type> callArgumentTypeList = new ArrayList<Type>();
        for (Enumeration e = callArgs.children(); e.hasMoreElements(); ) {
            ASTNode singleArg = (ASTNode) e.nextElement();
            checkCallSingleArg(singleArg);

            Type type = singleArg.getType();
            Type tempType = singleArg.getTempType();
            callArgumentTypeList.add(type);
        }

//        System.out.println("@ " + identifierString + "(" + callArgumentTypeList.toString().substring(1, callArgumentTypeList.toString().length() - 1) + ") " + identifier.getLineNumber() + ":" + identifier.getColumnNumber());

        FunctionCall functionCall = new FunctionCall(identifierString, callArgumentTypeList);
        FunctionDeclaration functionDeclaration = callScope.match(functionCall);

        if (functionDeclaration == null) {
            throw new IncorrectFunctionCallArguments(identifier.getString(), identifier);
        } else if (functionDeclaration.isPolymorphic() && !functionCall.containsPolymorphicTypes()) {
//            System.out.print("# " + identifierString + ": ");
//            HashMap<String, Type> polymorphicMap = functionObject.getArgumentTypeMap(functionCall);
//            System.out.println(polymorphicMap);
            try {
                Type returnType = m_typeCheckerPolymorphic.run(functionDeclaration, functionCall);
                call.setTempType(returnType);
//                System.out.println("# " + returnType);
            } catch (EmptyException e) {
                throw new IncorrectFunctionCallArguments(identifier.getString(), identifier);
            }
        }

        call.setType(identifierType);
    }

    private void checkUnaryOperator(ASTNode operator) throws IncorrectTypeException {
        String operatorString = operator.getString();
        Scope operatorScope = operator.getScopeObject();

        ASTNode argument = (ASTNode) operator.getNextSibling();
        Type argumentType = argument.getType();
        Type argumentTempType = argument.getTempType();

        ArrayList<Type> operatorArgumentList = new ArrayList<Type>();
        operatorArgumentList.add(argumentTempType != null ? argumentTempType : argumentType);

//        System.out.println(operatorString + " " + argumentType);
        FunctionDeclaration functionDeclaration = operatorScope.match(new FunctionCall(operatorString, operatorArgumentList));
        if (functionDeclaration != null) {
            ASTNode parent = (ASTNode) operator.getParent();

            parent.setType(functionDeclaration.getType());
        } else {
            throw new IncorrectTypeException(operator);
        }
    }

    private void checkBinaryOperator(ASTNode operator)
            throws IncorrectTypeException, IncorrectReturnTypeException, IncorrectFunctionCallArguments {
        Token operatorToken = operator.getToken();
        String operatorString = operator.getString();
        Scope operatorScope = operator.getScopeObject();

        ASTNode leftArgument = (ASTNode) operator.getPreviousSibling();
        Type leftArgumentType = leftArgument.getType();
        Type leftArgumentTempType = leftArgument.getTempType();

        ASTNode rightArgument = (ASTNode) operator.getNextSibling();
        Type rightArgumentType = rightArgument.getType();
        Type rightArgumentTempType = rightArgument.getTempType();

        ArrayList<Type> operatorArgumentList = new ArrayList<Type>();
//        operatorArgumentList.add(leftArgumentType);
        operatorArgumentList.add(leftArgumentTempType != null ? leftArgumentTempType : leftArgumentType);
//        operatorArgumentList.add(rightArgumentType);
        operatorArgumentList.add(rightArgumentTempType != null ? rightArgumentTempType : rightArgumentType);

//        System.out.println("@ " + operatorString + "(" + operatorArgumentList.get(0) + ", " + operatorArgumentList.get(1) + ") " + operator.getLineNumber() + ":" + operator.getColumnNumber());
        FunctionCall functionCall = new FunctionCall(operatorString, operatorArgumentList);
        FunctionDeclaration functionDeclaration = operatorScope.match(functionCall);

        if (functionDeclaration != null) {
            ASTNode parent = (ASTNode) operator.getParent();

//            System.out.println(functionObject.containsPolymorphicTypes() + " " + functionCall.containsPolymorphicTypes());
            /*if (!functionObject.containsPolymorphicTypes()) {
                parent.setType(functionObject.getType());
            } else */
            if (functionDeclaration.isPolymorphic() && !functionCall.containsPolymorphicTypes()) {
//                System.out.println(operator.getLineNumber() + " " + operator.getColumnNumber());
//                HashMap<String, Type> polymorphicMap = functionObject.getArgumentTypeMap(functionCall);
//                System.out.print("# <" + operatorString + ">: ");
//                System.out.println(polymorphicMap + "\n");
//                System.out.println("- " + functionCall);
                try {
                    Type returnType = m_typeCheckerPolymorphic.run(functionDeclaration, functionCall);
                    parent.setTempType(returnType);
                } catch (EmptyException e) {
                    throw new IncorrectTypeException(operator);
                }
//
//                parent
//                        .setType(functionObject.getType())
//                        .setTempType(functionObject.getTempType());
            }

            parent
                    .setType(functionDeclaration.getType())
                    .setLineNumber(operator.getLineNumber())
                    .setColumnNumber(operator.getColumnNumber());
        } /*else if (operatorToken == Token.OPERATOR_ASSIGN && operatorArgumentList.get(0).unify(operatorArgumentList.get(1))) {
            ASTNode parent = (ASTNode) operator.getParent();

            parent.setType(new BasicType("Bool"));
        } */ else {
            throw new IncorrectTypeException(operator);
        }
    }

    private void checkGetter(ASTNode getter)
            throws IncorrectTypeException, IncorrectReturnTypeException, IncorrectFunctionCallArguments {
        Scope getterScope = getter.getScopeObject();
        String getterString = getter.getString();

        ASTNode argument = (ASTNode) getter.getPreviousSibling();
        Type argumentType = argument.getType();
        Type argumentTempType = argument.getTempType();

        ArrayList<Type> getterArgumentList = new ArrayList<Type>();
        getterArgumentList.add(argumentTempType != null ? argumentTempType : argumentType);

//        System.out.println("@ " + getterString + "(" + getterArgumentList.get(0) + ")");
        FunctionCall functionCall = new FunctionCall(getterString, getterArgumentList);
        FunctionDeclaration functionDeclaration = getterScope.match(functionCall);
        if (functionDeclaration != null) {
            ASTNode expression = (ASTNode) getter.getParent();

            if (!functionCall.containsPolymorphicTypes()) {
                try {
                    Type returnType = m_typeCheckerPolymorphic.run(functionDeclaration, functionCall);
                    getter.setTempType(returnType);
                    expression.setTempType(returnType);
                } catch (EmptyException e) {
                    throw new IncorrectTypeException(getter);
                }
            }

            getter.setType(functionDeclaration.getType());
            expression
                    .setType(functionDeclaration.getType())
                    .setLineNumber(getter.getLineNumber())
                    .setColumnNumber(getter.getColumnNumber());
        } else {
            throw new IncorrectTypeException(getter);
        }
    }

    private void checkReturnType(ASTNode returnStmt) throws IncorrectReturnTypeException, ExcessReturnStatement {

        ASTNode child = (ASTNode) returnStmt.getChildAt(1);
        Type childType = child != null ? child.getType() : null;

        FunctionDeclaration functionDeclaration = ((Scope) returnStmt.getScopeObject()).getFunctionObject();

        if (childType == null || !childType.unify(functionDeclaration.getType())) {
            if (!functionDeclaration.isVoid()) {
                throw new IncorrectReturnTypeException(functionDeclaration.getIdentifier(), child);
            } else {
                throw new ExcessReturnStatement(functionDeclaration.getIdentifier(), child);
            }
        }

        returnStmt
                .setType(childType)
                .setLineNumber(child.getLineNumber())
                .setColumnNumber(child.getColumnNumber());
    }

    private void process(ASTNode node)
            throws IncorrectTypeException, IncorrectReturnTypeException, ExcessReturnStatement,
            IncorrectFunctionCallArguments {
        // Iterates over node's children
        for (Enumeration e = node.children(); e.hasMoreElements(); ) {
            process((ASTNode) e.nextElement());
        }

        Symbol nodeSymbol = node.getSymbol();

        ASTNode leftSibling = (ASTNode) node.getPreviousSibling();
        Symbol leftSiblingSymbol = leftSibling != null ? leftSibling.getSymbol() : null;

        if (nodeSymbol == Nonterminal.FuncCall) {
            processFunctionCall(node);
        } else if (nodeSymbol == Nonterminal.Getter) {
            checkGetter(node);
        } else if (nodeSymbol == Nonterminal.ReturnStmt) {
            checkReturnType(node);
        } else if (nodeSymbol == Nonterminal.TupleExp) {
            processTupleExpression(node);
        }

        if (leftSiblingSymbol == Nonterminal.Op1) {
            checkUnaryOperator(leftSibling);
        } else if (leftSiblingSymbol == Nonterminal.Op2) {
            checkBinaryOperator(leftSibling);
        }
    }

    private void initialise(ASTNode node) throws IncorrectTypeException {
        for (Enumeration e = node.children(); e.hasMoreElements(); ) {
            initialise((ASTNode) e.nextElement());
        }

        Token nodeToken = node.getToken();

        if (TypeCheckerMaps.literalList.contains(nodeToken)) {
            node.setType(TypeFactory.buildForLiteral(nodeToken));
        } else if (nodeToken == Token.IDENTIFIER) {
            StructureObject structureObject = node.getScopeObject().findByIdentifier(node.getString());

            if (structureObject != null) {
                node.setType(structureObject.getType());
            }
        }
    }

    public void run(ASTNode root)
            throws IncorrectTypeException, IncorrectReturnTypeException, IncorrectFunctionCallArguments,
            MissingReturnStatement, ExcessReturnStatement {
        initialise(root);
        process(root);

        m_returnPathChecker.run(root.getScopeObject());
    }
}
