package org.spl.scanner;

import org.spl.common.Token;

import java.util.HashMap;

public class ScannerTokenMaps {

    public final static HashMap<State, PreToken> preTokenMap = new HashMap<State, PreToken>();
    public final static HashMap<PreToken, HashMap<String, Token>> finalTokenMap =
            new HashMap<PreToken, HashMap<String, Token>>();
    public final static String CUSTOM_TOKEN = "CUSTOM_TOKEN";

    static {
        // Preliminary token map
        preTokenMap.put(State.IDENTIFIER, PreToken.IDENTIFIER);
        preTokenMap.put(State.NUMERIC, PreToken.NUMERIC);
        preTokenMap.put(State.CHARACTER, PreToken.CHARACTER);
        preTokenMap.put(State.BRACKET, PreToken.BRACKET);
        preTokenMap.put(State.PUNCTUATION, PreToken.PUNCTUATION);
        preTokenMap.put(State.OPERATOR, PreToken.OPERATOR);
        preTokenMap.put(State.ASSIGNMENT, PreToken.OPERATOR);
        preTokenMap.put(State.LESS_THAN, PreToken.OPERATOR);
        preTokenMap.put(State.GREATER_THAN, PreToken.OPERATOR);
        preTokenMap.put(State.EXCLAMATION_MARK, PreToken.OPERATOR);
        preTokenMap.put(State.MINUS, PreToken.OPERATOR);
        preTokenMap.put(State.EQUAL, PreToken.OPERATOR);
        preTokenMap.put(State.LESS_THAN_OR_EQUAL, PreToken.OPERATOR);
        preTokenMap.put(State.GREATER_THAN_OR_EQUAL, PreToken.OPERATOR);
        preTokenMap.put(State.NOT_EQUAL, PreToken.OPERATOR);
        preTokenMap.put(State.AND, PreToken.OPERATOR);
        preTokenMap.put(State.OR, PreToken.OPERATOR);
        preTokenMap.put(State.SLASH, PreToken.OPERATOR);
        preTokenMap.put(State.STAR, PreToken.OPERATOR);
        preTokenMap.put(State.FIELD, PreToken.FIELD);


        HashMap<String, Token> map;

        // Final token map
        map = new HashMap<String, Token>();
        map.put(CUSTOM_TOKEN, Token.IDENTIFIER);
        map.put("Void", Token.TYPE_VOID);
        map.put("Int", Token.TYPE_INT);
        map.put("Bool", Token.TYPE_BOOL);
        map.put("Char", Token.TYPE_CHAR);
        map.put("False", Token.BOOL_FALSE);
        map.put("True", Token.BOOL_TRUE);
        map.put("if", Token.CONDITIONAL_IF);
        map.put("else", Token.CONDITIONAL_ELSE);
        map.put("while", Token.CONDITIONAL_WHILE);
        map.put("return", Token.RETURN);
        map.put("hd", Token.GET_HEAD);
        map.put("tl", Token.GET_TAIL);
        map.put("fst", Token.GET_FIRST);
        map.put("snd", Token.GET_SECOND);
        finalTokenMap.put(PreToken.IDENTIFIER, map);

        map = new HashMap<String, Token>();
        map.put(CUSTOM_TOKEN, Token.NUMERIC);
        finalTokenMap.put(PreToken.NUMERIC, map);

        map = new HashMap<String, Token>();
        map.put(CUSTOM_TOKEN, Token.CHARACTER);
        finalTokenMap.put(PreToken.CHARACTER, map);

        map = new HashMap<String, Token>();
        map.put("+", Token.OPERATOR_ADD);
        map.put("-", Token.OPERATOR_SUBTRACT);
        map.put("*", Token.OPERATOR_MULTIPLY);
        map.put("/", Token.OPERATOR_DIVIDE);
        map.put("%", Token.OPERATOR_MODULO);
        map.put("==", Token.OPERATOR_EQUAL);
        map.put("!=", Token.OPERATOR_NOT_EQUAL);
        map.put("<", Token.OPERATOR_LESS_THAN);
        map.put(">", Token.OPERATOR_GREATER_THAN);
        map.put("<=", Token.OPERATOR_LESS_THAN_OR_EQUAL);
        map.put(">=", Token.OPERATOR_GREATER_THAN_OR_EQUAL);
        map.put("&&", Token.OPERATOR_AND);
        map.put("||", Token.OPERATOR_OR);
        map.put("=", Token.OPERATOR_ASSIGN);
        map.put("!", Token.OPERATOR_NEGATE);
        map.put(":", Token.OPERATOR_CONCATENATE);
        finalTokenMap.put(PreToken.OPERATOR, map);

        map = new HashMap<String, Token>();
        map.put("(", Token.BRA_ROUND_START);
        map.put(")", Token.BRA_ROUND_END);
        map.put("[", Token.BRA_SQUARE_START);
        map.put("]", Token.BRA_SQUARE_END);
        map.put("{", Token.BRA_CURLY_START);
        map.put("}", Token.BRA_CURLY_END);
        finalTokenMap.put(PreToken.BRACKET, map);

        map = new HashMap<String, Token>();
        map.put(",", Token.PUNC_COMMA);
        map.put(";", Token.PUNC_SEMICOLON);
        finalTokenMap.put(PreToken.PUNCTUATION, map);

        map = new HashMap<String, Token>();
        map.put(".", Token.FIELD);
        finalTokenMap.put(PreToken.FIELD, map);
    }
}
