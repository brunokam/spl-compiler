package org.spl.typechecker;

import org.spl.common.ASTNode;
import org.spl.common.Nonterminal;
import org.spl.common.Symbol;
import org.spl.common.Token;
import org.spl.common.structure.FunctionCall;
import org.spl.common.type.TupleType;
import org.spl.common.type.Type;
import org.spl.common.structure.BindingObject;
import org.spl.common.structure.FunctionObject;
import org.spl.common.structure.ScopeObject;
import org.spl.typechecker.exception.EmptyException;
import org.spl.typechecker.exception.IncorrectFunctionCallArguments;
import org.spl.typechecker.exception.IncorrectReturnTypeException;
import org.spl.typechecker.exception.IncorrectTypeException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

public class TypeCheckerPolymorphic {

    private void processTupleExpression(ASTNode tupleExp, final HashMap<String, Type> polymorphicMap)
            throws IncorrectTypeException {
        ASTNode first = (ASTNode) tupleExp.getChildAt(0);
        Type firstType = first.getType();
        Type firstTempType = first.getTempType();

        ASTNode second = (ASTNode) tupleExp.getChildAt(1);
        Type secondType = second.getType();
        Type secondTempType = second.getTempType();

//        System.out.println(firstType + " " + secondType);
        if (firstTempType == null) {
            throw new IncorrectTypeException(first);
        } else if (secondTempType == null) {
            throw new IncorrectTypeException(second);
        }

        tupleExp.setTempType(new TupleType(firstTempType, secondTempType));
    }

    private void checkCallSingleArg(ASTNode node, final HashMap<String, Type> polymorphicMap)
            throws IncorrectTypeException {
        ASTNode child = (ASTNode) node.getChildAt(0);
        Type childType = child.getType();
        Type childTempType = child.getTempType();

        if (childType == null) {
            throw new IncorrectTypeException(node);
        }

        node
                .setType(childType)
                .setTempType(childTempType);
    }

    private void processFunctionCall(ASTNode call, final HashMap<String, Type> polymorphicMap)
            throws IncorrectTypeException, IncorrectReturnTypeException, IncorrectFunctionCallArguments {
        ScopeObject callScopeObject = call.getScopeObject();

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
            checkCallSingleArg(singleArg, polymorphicMap);

            Type type = singleArg.getType();
            Type tempType = singleArg.getTempType();
            callArgumentTypeList.add(tempType != null ? tempType : type);
        }

//        System.out.println("@@ " + identifierString + "(" + callArgumentTypeList.toString().substring(1, callArgumentTypeList.toString().length() - 1) + ") " + identifier.getLineNumber() + ":" + identifier.getColumnNumber());

        FunctionCall functionCall = new FunctionCall(identifierString, callArgumentTypeList);
        FunctionObject functionObject = callScopeObject.match(functionCall);

        if (functionObject == null) {
            throw new IncorrectFunctionCallArguments(identifier.getString(), identifier);
        } else if (functionObject.isPolymorphic() && !functionCall.containsPolymorphicTypes()) {
//            System.out.print("# " + identifierString + ": ");
//            HashMap<String, Type> polymorphicMap = functionObject.getArgumentTypeMap(functionCall);
//            System.out.println(polymorphicMap);
            try {
                Type returnType = run(functionObject, functionCall);
                call.setTempType(returnType);
//                System.out.println("## " + returnType);
            } catch (EmptyException e) {
                throw new IncorrectFunctionCallArguments(identifier.getString(), identifier);
            }
        }

        call.setType(identifierType);
    }

    private void checkUnaryOperator(ASTNode operator, final HashMap<String, Type> polymorphicMap)
            throws IncorrectTypeException {
        String operatorString = operator.getString();
        ScopeObject operatorScopeObject = operator.getScopeObject();

        ASTNode argument = (ASTNode) operator.getNextSibling();
        Type argumentTempType = argument.getTempType();

        ArrayList<Type> operatorArgumentList = new ArrayList<Type>();
        operatorArgumentList.add(argumentTempType);

//        System.out.println(operatorString + " " + argumentType);
        FunctionCall functionCall = new FunctionCall(operatorString, operatorArgumentList);
        FunctionObject functionObject = operatorScopeObject.match(functionCall);
        if (functionObject != null) {
            ASTNode parent = (ASTNode) operator.getParent();

//            parentObject.setTempType(functionObject.getTempType());
            try {
                HashMap<String, Type> operatorPolymorphicMap = functionObject.getArgumentTypeMap(functionCall);
                parent.setTempType(functionObject.getType().replace(operatorPolymorphicMap));
            } catch (EmptyException e) {
                throw new IncorrectTypeException(operator);
            }
        } else {
            throw new IncorrectTypeException(operator);
        }
    }

    private void checkBinaryOperator(ASTNode operator, final HashMap<String, Type> polymorphicMap)
            throws IncorrectTypeException {
        Token operatorToken = operator.getToken();
        String operatorString = operator.getString();
        ScopeObject operatorScopeObject = operator.getScopeObject();

        ASTNode leftArgument = (ASTNode) operator.getPreviousSibling();
        Type leftArgumentType = leftArgument.getType();
        Type leftArgumentTempType = leftArgument.getTempType();

        ASTNode rightArgument = (ASTNode) operator.getNextSibling();
        Type rightArgumentType = rightArgument.getType();
        Type rightArgumentTempType = rightArgument.getTempType();

        ArrayList<Type> operatorArgumentList = new ArrayList<Type>();
        operatorArgumentList.add(leftArgumentTempType != null ? leftArgumentTempType : leftArgumentType);
        operatorArgumentList.add(rightArgumentTempType != null ? rightArgumentTempType : rightArgumentType);

//        System.out.println("@@ " + operatorString + "(" + operatorArgumentList.get(0) + ", " + operatorArgumentList.get(1) + ")");
        FunctionCall functionCall = new FunctionCall(operatorString, operatorArgumentList);
        FunctionObject functionObject;
        try {
            functionObject = operatorScopeObject.match(functionCall, polymorphicMap);
        } catch (EmptyException e) {
            throw new IncorrectTypeException(operator);
        }

        if (functionObject != null) {
            ASTNode parent = (ASTNode) operator.getParent();

            try {
                HashMap<String, Type> operatorPolymorphicMap = functionObject.getArgumentTypeMap(functionCall);
                parent.setTempType(functionObject.getType().replace(operatorPolymorphicMap));
            } catch (EmptyException e) {
                throw new IncorrectTypeException(operator);
            }
//            } else if (!functionCall.containsPolymorphicTypes()) {
//                System.out.println(operator.getLineNumber() + " " + operator.getColumnNumber());
//                HashMap<String, Type> polymorphicMap = functionObject.getArgumentTypeMap(functionCall);
//                System.out.print("# <" + operatorString + ">: ");
//                System.out.println(polymorphicMap + "\n");
//                m_typeCheckerPolymorphic.run(functionObject, polymorphicMap);
//
//                parentObject
//                        .setType(functionObject.getType())
//                        .setTempType(functionObject.getTempType());
//            } else {
//                throw new RuntimeException();
//            }
        }/* else if (operatorToken == Token.OPERATOR_ASSIGN && leftArgumentTempType.unify(rightArgumentTempType)) {
            ASTNode parent = (ASTNode) operator.getParent();
            ASTNodeObject parentObject = (ASTNodeObject) parent.getUserObject();

            parentObject.setTempType(new BasicType("Bool"));
        } */ else {
            throw new IncorrectTypeException(operator);
        }
    }

    private void checkGetter(ASTNode getter, final HashMap<String, Type> polymorphicMap)
            throws IncorrectTypeException {
        ScopeObject getterScopeObject = getter.getScopeObject();
        String getterString = getter.getString();

        ASTNode argument = (ASTNode) getter.getPreviousSibling();
        Type argumentType = argument.getType();
        Type argumentTempType = argument.getTempType();

        ArrayList<Type> getterArgumentList = new ArrayList<Type>();
        getterArgumentList.add(argumentTempType != null ? argumentTempType : argumentType);

//        System.out.println("@@ " + getterString + "(" + getterArgumentList.get(0) + ")");
        FunctionCall functionCall = new FunctionCall(getterString, getterArgumentList);
        FunctionObject functionObject = getterScopeObject.match(functionCall);
        if (functionObject != null) {
            ASTNode expression = (ASTNode) getter.getParent();

            try {
                HashMap<String, Type> getterPolymorphicMap = functionObject.getArgumentTypeMap(functionCall);
                getter.setTempType(functionObject.getType().replace(getterPolymorphicMap));
                expression.setTempType(functionObject.getType().replace(getterPolymorphicMap));
            } catch (EmptyException e) {
                throw new IncorrectTypeException(getter);
            }
        } else {
            throw new IncorrectTypeException(getter);
        }
    }

    private void checkReturnType(ASTNode returnStmt, final HashMap<String, Type> polymorphicMap)
            throws IncorrectReturnTypeException {
        ASTNode child = (ASTNode) returnStmt.getChildAt(1);
        Type childType = child != null ? child.getType() : null;
        Type childTempType = child != null ? child.getTempType() : null;

        FunctionObject functionObject = ((ScopeObject) returnStmt.getScopeObject()).getFunctionObject();

//        System.out.println("@@ " + functionObject.getTempType() + " " + functionObject.getIdentifier() + " returns " + (childTempType != null ? childTempType : childType));
        if (childType == null || !functionObject.getTempType().unify(childTempType != null ? childTempType : childType)) {
            throw new IncorrectReturnTypeException(functionObject.getIdentifier(), child);
        }

//        returnStmtObject.setTempType(childType.replace(polymorphicMap)); // Changed
    }

    private void process(ASTNode node, final HashMap<String, Type> polymorphicMap)
            throws IncorrectTypeException, IncorrectReturnTypeException, IncorrectFunctionCallArguments {
        // Iterates over node's children
        for (Enumeration e = node.children(); e.hasMoreElements(); ) {
            process((ASTNode) e.nextElement(), polymorphicMap);
        }

        Symbol nodeSymbol = node.getSymbol();

        ASTNode leftSibling = (ASTNode) node.getPreviousSibling();
        Symbol leftSiblingSymbol = leftSibling != null ? leftSibling.getSymbol() : null;

        if (nodeSymbol == Nonterminal.FuncCall) {
//            node.setTempType(null);
            processFunctionCall(node, polymorphicMap);
        } else if (nodeSymbol == Nonterminal.Getter) {
            checkGetter(node, polymorphicMap);
        } else if (nodeSymbol == Nonterminal.ReturnStmt) {
            checkReturnType(node, polymorphicMap);
        } else if (nodeSymbol == Nonterminal.TupleExp) {
            processTupleExpression(node, polymorphicMap);
        }

        if (leftSiblingSymbol == Nonterminal.Op1) {
            leftSibling.setTempType(null);
            checkUnaryOperator(leftSibling, polymorphicMap);
        } else if (leftSiblingSymbol == Nonterminal.Op2) {
            leftSibling.setTempType(null);
            checkBinaryOperator(leftSibling, polymorphicMap);
        }
    }

    private void initialise(ASTNode node, final HashMap<String, Type> polymorphicMap)
            throws IncorrectTypeException {
        for (Enumeration e = node.children(); e.hasMoreElements(); ) {
            initialise((ASTNode) e.nextElement(), polymorphicMap);
        }

        Token nodeToken = node.getToken();

        if (TypeCheckerMaps.literalList.contains(nodeToken)) {
            node.setTempType(node.getType().replace(polymorphicMap));
        } else if (nodeToken == Token.IDENTIFIER) {
            BindingObject bindingObject = node.getScopeObject().findByIdentifier(node.getString());

            if (bindingObject != null) {
                node.setTempType(bindingObject.getType().replace(polymorphicMap));
            }
        }
    }

    public Type run(FunctionObject functionObject, final FunctionCall functionCall)
            throws IncorrectTypeException, IncorrectReturnTypeException, IncorrectFunctionCallArguments,
            EmptyException {
        HashMap<String, Type> polymorphicMap = functionObject.getArgumentTypeMap(functionCall);
        functionObject.setTempType(functionObject.getType().replace(polymorphicMap));

        initialise(functionObject.getDeclarationNode(), polymorphicMap);
        process(functionObject.getDeclarationNode(), polymorphicMap);
        return functionObject.getTempType();
    }
}
