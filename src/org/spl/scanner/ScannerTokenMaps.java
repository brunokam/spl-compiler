package org.spl.scanner;

import java.util.HashMap;

public class ScannerTokenMaps {

    public final static HashMap<ScannerState, ScannerToken.Preliminary> preTokenMap = new HashMap<ScannerState, ScannerToken.Preliminary>();
    public final static HashMap<ScannerToken.Preliminary, HashMap<String, ScannerToken.Final>> finalTokenMap =
            new HashMap<ScannerToken.Preliminary, HashMap<String, ScannerToken.Final>>();
    public final static String CUSTOM_TOKEN = "CUSTOM_TOKEN";

    static {
        // Preliminary token map
        preTokenMap.put(ScannerState.IDENTIFIER, ScannerToken.Preliminary.IDENTIFIER);
        preTokenMap.put(ScannerState.NUMERIC, ScannerToken.Preliminary.NUMERIC);
        preTokenMap.put(ScannerState.CHARACTER, ScannerToken.Preliminary.CHARACTER);
        preTokenMap.put(ScannerState.BRACKET, ScannerToken.Preliminary.BRACKET);
        preTokenMap.put(ScannerState.PUNCTUATION, ScannerToken.Preliminary.PUNCTUATION);
        preTokenMap.put(ScannerState.OPERATOR, ScannerToken.Preliminary.OPERATOR);
        preTokenMap.put(ScannerState.ASSIGNMENT, ScannerToken.Preliminary.OPERATOR);
        preTokenMap.put(ScannerState.LESS_THAN, ScannerToken.Preliminary.OPERATOR);
        preTokenMap.put(ScannerState.GREATER_THAN, ScannerToken.Preliminary.OPERATOR);
        preTokenMap.put(ScannerState.EXCLAMATION_MARK, ScannerToken.Preliminary.OPERATOR);
        preTokenMap.put(ScannerState.MINUS, ScannerToken.Preliminary.OPERATOR);
        preTokenMap.put(ScannerState.EQUAL, ScannerToken.Preliminary.OPERATOR);
        preTokenMap.put(ScannerState.LESS_THAN_OR_EQUAL, ScannerToken.Preliminary.OPERATOR);
        preTokenMap.put(ScannerState.GREATER_THAN_OR_EQUAL, ScannerToken.Preliminary.OPERATOR);
        preTokenMap.put(ScannerState.NOT_EQUAL, ScannerToken.Preliminary.OPERATOR);
        preTokenMap.put(ScannerState.AND, ScannerToken.Preliminary.OPERATOR);
        preTokenMap.put(ScannerState.OR, ScannerToken.Preliminary.OPERATOR);
        preTokenMap.put(ScannerState.SLASH, ScannerToken.Preliminary.OPERATOR);
        preTokenMap.put(ScannerState.STAR, ScannerToken.Preliminary.OPERATOR);
        preTokenMap.put(ScannerState.FIELD, ScannerToken.Preliminary.FIELD);


        HashMap<String, ScannerToken.Final> map;

        // Final token map
        map = new HashMap<String, ScannerToken.Final>();
        map.put(CUSTOM_TOKEN, ScannerToken.Final.IDENTIFIER);
        map.put("Void", ScannerToken.Final.TP_VOID);
        map.put("Int", ScannerToken.Final.TP_INT);
        map.put("Bool", ScannerToken.Final.TP_BOOL);
        map.put("Char", ScannerToken.Final.TP_CHAR);
        map.put("False", ScannerToken.Final.BL_FALSE);
        map.put("True", ScannerToken.Final.BL_TRUE);
        map.put("if", ScannerToken.Final.CND_IF);
        map.put("else", ScannerToken.Final.CND_ELSE);
        map.put("while", ScannerToken.Final.CND_WHILE);
        map.put("return", ScannerToken.Final.RETURN);
        map.put("(", ScannerToken.Final.GET_HEAD);
        map.put(")", ScannerToken.Final.GET_TAIL);
        map.put("[", ScannerToken.Final.GET_FIRST);
        map.put("]", ScannerToken.Final.GET_SECOND);
        finalTokenMap.put(ScannerToken.Preliminary.IDENTIFIER, map);

        map = new HashMap<String, ScannerToken.Final>();
        map.put(CUSTOM_TOKEN, ScannerToken.Final.NUMERIC);
        finalTokenMap.put(ScannerToken.Preliminary.NUMERIC, map);

        map = new HashMap<String, ScannerToken.Final>();
        map.put(CUSTOM_TOKEN, ScannerToken.Final.CHARACTER);
        finalTokenMap.put(ScannerToken.Preliminary.CHARACTER, map);

        map = new HashMap<String, ScannerToken.Final>();
        map.put("+", ScannerToken.Final.OP_ADD);
        map.put("-", ScannerToken.Final.OP_SUBTRACT);
        map.put("*", ScannerToken.Final.OP_MULTIPLY);
        map.put("/", ScannerToken.Final.OP_DIVIDE);
        map.put("%", ScannerToken.Final.OP_MODULO);
        map.put("==", ScannerToken.Final.OP_EQUAL);
        map.put("!=", ScannerToken.Final.OP_NOT_EQUAL);
        map.put("<", ScannerToken.Final.OP_LESS_THAN);
        map.put(">", ScannerToken.Final.OP_GREATER_THAN);
        map.put("<=", ScannerToken.Final.OP_LESS_THAN_OR_EQUAL);
        map.put(">=", ScannerToken.Final.OP_GREATER_THAN_OR_EQUAL);
        map.put("&&", ScannerToken.Final.OP_AND);
        map.put("||", ScannerToken.Final.OP_OR);
        map.put("=", ScannerToken.Final.OP_ASSIGN);
        map.put("!", ScannerToken.Final.OP_NEGATE);
        map.put(":", ScannerToken.Final.OP_CONCATENATE);
        finalTokenMap.put(ScannerToken.Preliminary.OPERATOR, map);

        map = new HashMap<String, ScannerToken.Final>();
        map.put("(", ScannerToken.Final.BR_ROUND_START);
        map.put(")", ScannerToken.Final.BR_ROUND_END);
        map.put("[", ScannerToken.Final.BR_SQUARE_START);
        map.put("]", ScannerToken.Final.BR_SQUARE_END);
        map.put("{", ScannerToken.Final.BR_CURLY_START);
        map.put("}", ScannerToken.Final.BR_CURLY_END);
        finalTokenMap.put(ScannerToken.Preliminary.BRACKET, map);

        map = new HashMap<String, ScannerToken.Final>();
        map.put(",", ScannerToken.Final.PNC_COMA);
        map.put(";", ScannerToken.Final.PNC_SEMICOLON);
        finalTokenMap.put(ScannerToken.Preliminary.PUNCTUATION, map);

        map = new HashMap<String, ScannerToken.Final>();
        map.put(".", ScannerToken.Final.FIELD);
        finalTokenMap.put(ScannerToken.Preliminary.FIELD, map);
    }
}
