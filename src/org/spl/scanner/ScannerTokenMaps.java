package org.spl.scanner;

import org.spl.common.Token;

import java.util.HashMap;

public class ScannerTokenMaps {

    public final static HashMap<ScannerState, ScannerPreToken> preTokenMap = new HashMap<ScannerState, ScannerPreToken>();
    public final static HashMap<ScannerPreToken, HashMap<String, Token>> finalTokenMap =
            new HashMap<ScannerPreToken, HashMap<String, Token>>();
    public final static String CUSTOM_TOKEN = "CUSTOM_TOKEN";

    static {
        // Preliminary token map
        preTokenMap.put(ScannerState.IDENTIFIER, ScannerPreToken.IDENTIFIER);
        preTokenMap.put(ScannerState.NUMERIC, ScannerPreToken.NUMERIC);
        preTokenMap.put(ScannerState.CHARACTER, ScannerPreToken.CHARACTER);
        preTokenMap.put(ScannerState.BRACKET, ScannerPreToken.BRACKET);
        preTokenMap.put(ScannerState.PUNCTUATION, ScannerPreToken.PUNCTUATION);
        preTokenMap.put(ScannerState.OPERATOR, ScannerPreToken.OPERATOR);
        preTokenMap.put(ScannerState.ASSIGNMENT, ScannerPreToken.OPERATOR);
        preTokenMap.put(ScannerState.LESS_THAN, ScannerPreToken.OPERATOR);
        preTokenMap.put(ScannerState.GREATER_THAN, ScannerPreToken.OPERATOR);
        preTokenMap.put(ScannerState.EXCLAMATION_MARK, ScannerPreToken.OPERATOR);
        preTokenMap.put(ScannerState.MINUS, ScannerPreToken.OPERATOR);
        preTokenMap.put(ScannerState.EQUAL, ScannerPreToken.OPERATOR);
        preTokenMap.put(ScannerState.LESS_THAN_OR_EQUAL, ScannerPreToken.OPERATOR);
        preTokenMap.put(ScannerState.GREATER_THAN_OR_EQUAL, ScannerPreToken.OPERATOR);
        preTokenMap.put(ScannerState.NOT_EQUAL, ScannerPreToken.OPERATOR);
        preTokenMap.put(ScannerState.AND, ScannerPreToken.OPERATOR);
        preTokenMap.put(ScannerState.OR, ScannerPreToken.OPERATOR);
        preTokenMap.put(ScannerState.SLASH, ScannerPreToken.OPERATOR);
        preTokenMap.put(ScannerState.STAR, ScannerPreToken.OPERATOR);
        preTokenMap.put(ScannerState.FIELD, ScannerPreToken.FIELD);


        HashMap<String, Token> map;

        // Final token map
        map = new HashMap<String, Token>();
        map.put(CUSTOM_TOKEN, Token.IDENTIFIER);
        map.put("Void", Token.TP_VOID);
        map.put("Int", Token.TP_INT);
        map.put("Bool", Token.TP_BOOL);
        map.put("Char", Token.TP_CHAR);
        map.put("False", Token.BL_FALSE);
        map.put("True", Token.BL_TRUE);
        map.put("if", Token.CND_IF);
        map.put("else", Token.CND_ELSE);
        map.put("while", Token.CND_WHILE);
        map.put("return", Token.RETURN);
        map.put("(", Token.GET_HEAD);
        map.put(")", Token.GET_TAIL);
        map.put("[", Token.GET_FIRST);
        map.put("]", Token.GET_SECOND);
        finalTokenMap.put(ScannerPreToken.IDENTIFIER, map);

        map = new HashMap<String, Token>();
        map.put(CUSTOM_TOKEN, Token.NUMERIC);
        finalTokenMap.put(ScannerPreToken.NUMERIC, map);

        map = new HashMap<String, Token>();
        map.put(CUSTOM_TOKEN, Token.CHARACTER);
        finalTokenMap.put(ScannerPreToken.CHARACTER, map);

        map = new HashMap<String, Token>();
        map.put("+", Token.OP_ADD);
        map.put("-", Token.OP_SUBTRACT);
        map.put("*", Token.OP_MULTIPLY);
        map.put("/", Token.OP_DIVIDE);
        map.put("%", Token.OP_MODULO);
        map.put("==", Token.OP_EQUAL);
        map.put("!=", Token.OP_NOT_EQUAL);
        map.put("<", Token.OP_LESS_THAN);
        map.put(">", Token.OP_GREATER_THAN);
        map.put("<=", Token.OP_LESS_THAN_OR_EQUAL);
        map.put(">=", Token.OP_GREATER_THAN_OR_EQUAL);
        map.put("&&", Token.OP_AND);
        map.put("||", Token.OP_OR);
        map.put("=", Token.OP_ASSIGN);
        map.put("!", Token.OP_NEGATE);
        map.put(":", Token.OP_CONCATENATE);
        finalTokenMap.put(ScannerPreToken.OPERATOR, map);

        map = new HashMap<String, Token>();
        map.put("(", Token.BR_ROUND_START);
        map.put(")", Token.BR_ROUND_END);
        map.put("[", Token.BR_SQUARE_START);
        map.put("]", Token.BR_SQUARE_END);
        map.put("{", Token.BR_CURLY_START);
        map.put("}", Token.BR_CURLY_END);
        finalTokenMap.put(ScannerPreToken.BRACKET, map);

        map = new HashMap<String, Token>();
        map.put(",", Token.PNC_COMA);
        map.put(";", Token.PNC_SEMICOLON);
        finalTokenMap.put(ScannerPreToken.PUNCTUATION, map);

        map = new HashMap<String, Token>();
        map.put(".", Token.FIELD);
        finalTokenMap.put(ScannerPreToken.FIELD, map);
    }
}
