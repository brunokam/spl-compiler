package org.spl.codegenerator;

import org.spl.common.Token;

import java.util.ArrayList;
import java.util.HashMap;

public class CodeGeneratorMaps {

    public final static ArrayList<Token> constantTokenList = new ArrayList<Token>();

    public final static HashMap<Token, String> unaryOperatorTokenMap = new HashMap<Token, String>();
    public final static HashMap<Token, String> binaryOperatorTokenMap = new HashMap<Token, String>();

    static {
        constantTokenList.add(Token.NUMERIC);

        unaryOperatorTokenMap.put(Token.OPERATOR_SUBTRACT, "sub");
        unaryOperatorTokenMap.put(Token.OPERATOR_NEGATE, "neg");

        binaryOperatorTokenMap.put(Token.OPERATOR_ADD, "add");
        binaryOperatorTokenMap.put(Token.OPERATOR_SUBTRACT, "sub");
        binaryOperatorTokenMap.put(Token.OPERATOR_MULTIPLY, "mul");
        binaryOperatorTokenMap.put(Token.OPERATOR_DIVIDE, "div");
        binaryOperatorTokenMap.put(Token.OPERATOR_MODULO, "mod");
        binaryOperatorTokenMap.put(Token.OPERATOR_EQUAL, "eq");
        binaryOperatorTokenMap.put(Token.OPERATOR_NOT_EQUAL, "ne");
        binaryOperatorTokenMap.put(Token.OPERATOR_LESS_THAN, "lt");
        binaryOperatorTokenMap.put(Token.OPERATOR_GREATER_THAN, "gt");
        binaryOperatorTokenMap.put(Token.OPERATOR_LESS_THAN_OR_EQUAL, "le");
        binaryOperatorTokenMap.put(Token.OPERATOR_GREATER_THAN_OR_EQUAL, "ge");
        binaryOperatorTokenMap.put(Token.OPERATOR_AND, "and");
        binaryOperatorTokenMap.put(Token.OPERATOR_OR, "or");
    }
}
