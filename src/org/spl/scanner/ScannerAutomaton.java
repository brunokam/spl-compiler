package org.spl.scanner;

import java.util.ArrayList;
import java.util.HashMap;

public class ScannerAutomaton {

    private final static HashMap<ScannerState, HashMap<ScannerSymbol, ScannerState>> transitionMap = new HashMap<ScannerState, HashMap<ScannerSymbol, ScannerState>>();
    public final static ArrayList<ScannerState> acceptStateList = new ArrayList<ScannerState>();

    static {
        // Transition map
        HashMap<ScannerSymbol, ScannerState> map;

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DIGIT, ScannerState.NUMERIC);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.PUNCTUATION, ScannerState.PUNCTUATION);
        map.put(ScannerSymbol.STAR, ScannerState.OPERATOR);
        map.put(ScannerSymbol.PLUS, ScannerState.OPERATOR);
        map.put(ScannerSymbol.PERCENT, ScannerState.OPERATOR);
        map.put(ScannerSymbol.COLON, ScannerState.OPERATOR);
        map.put(ScannerSymbol.MINUS, ScannerState.MINUS);
        map.put(ScannerSymbol.EXCLAMATION_MARK, ScannerState.EXCLAMATION_MARK);
        map.put(ScannerSymbol.EQUAL_SIGN, ScannerState.ASSIGNMENT);
        map.put(ScannerSymbol.SLASH, ScannerState.SLASH);
        map.put(ScannerSymbol.LESS_THAN, ScannerState.LESS_THAN);
        map.put(ScannerSymbol.GREATER_THAN, ScannerState.GREATER_THAN);
        map.put(ScannerSymbol.AMPERSAND, ScannerState.AMPERSAND);
        map.put(ScannerSymbol.BAR, ScannerState.BAR);
        map.put(ScannerSymbol.APOSTROPHE, ScannerState.APOSTROPHE);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.START, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DIGIT, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.PUNCTUATION, ScannerState.PUNCTUATION);
        map.put(ScannerSymbol.STAR, ScannerState.OPERATOR);
        map.put(ScannerSymbol.PLUS, ScannerState.OPERATOR);
        map.put(ScannerSymbol.PERCENT, ScannerState.OPERATOR);
        map.put(ScannerSymbol.COLON, ScannerState.OPERATOR);
        map.put(ScannerSymbol.MINUS, ScannerState.MINUS);
        map.put(ScannerSymbol.EXCLAMATION_MARK, ScannerState.EXCLAMATION_MARK);
        map.put(ScannerSymbol.EQUAL_SIGN, ScannerState.ASSIGNMENT);
        map.put(ScannerSymbol.SLASH, ScannerState.SLASH);
        map.put(ScannerSymbol.LESS_THAN, ScannerState.LESS_THAN);
        map.put(ScannerSymbol.GREATER_THAN, ScannerState.GREATER_THAN);
        map.put(ScannerSymbol.AMPERSAND, ScannerState.AMPERSAND);
        map.put(ScannerSymbol.BAR, ScannerState.BAR);
        map.put(ScannerSymbol.UNDERSCORE, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DOT, ScannerState.FIELD);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.IDENTIFIER, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.DIGIT, ScannerState.NUMERIC);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.PUNCTUATION, ScannerState.PUNCTUATION);
        map.put(ScannerSymbol.STAR, ScannerState.OPERATOR);
        map.put(ScannerSymbol.PLUS, ScannerState.OPERATOR);
        map.put(ScannerSymbol.PERCENT, ScannerState.OPERATOR);
        map.put(ScannerSymbol.COLON, ScannerState.OPERATOR);
        map.put(ScannerSymbol.MINUS, ScannerState.MINUS);
        map.put(ScannerSymbol.EXCLAMATION_MARK, ScannerState.EXCLAMATION_MARK);
        map.put(ScannerSymbol.EQUAL_SIGN, ScannerState.ASSIGNMENT);
        map.put(ScannerSymbol.SLASH, ScannerState.SLASH);
        map.put(ScannerSymbol.LESS_THAN, ScannerState.LESS_THAN);
        map.put(ScannerSymbol.GREATER_THAN, ScannerState.GREATER_THAN);
        map.put(ScannerSymbol.AMPERSAND, ScannerState.AMPERSAND);
        map.put(ScannerSymbol.BAR, ScannerState.BAR);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.NUMERIC, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.PUNCTUATION, ScannerState.PUNCTUATION);
        map.put(ScannerSymbol.PLUS, ScannerState.OPERATOR);
        map.put(ScannerSymbol.COLON, ScannerState.OPERATOR);
        map.put(ScannerSymbol.MINUS, ScannerState.MINUS);
        map.put(ScannerSymbol.EQUAL_SIGN, ScannerState.ASSIGNMENT);
        map.put(ScannerSymbol.LESS_THAN, ScannerState.LESS_THAN);
        map.put(ScannerSymbol.GREATER_THAN, ScannerState.GREATER_THAN);
        map.put(ScannerSymbol.AMPERSAND, ScannerState.AMPERSAND);
        map.put(ScannerSymbol.BAR, ScannerState.BAR);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.CHARACTER, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DIGIT, ScannerState.NUMERIC);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.PUNCTUATION, ScannerState.PUNCTUATION);
        map.put(ScannerSymbol.STAR, ScannerState.OPERATOR);
        map.put(ScannerSymbol.PLUS, ScannerState.OPERATOR);
        map.put(ScannerSymbol.PERCENT, ScannerState.OPERATOR);
        map.put(ScannerSymbol.COLON, ScannerState.OPERATOR);
        map.put(ScannerSymbol.MINUS, ScannerState.MINUS);
        map.put(ScannerSymbol.EXCLAMATION_MARK, ScannerState.EXCLAMATION_MARK);
        map.put(ScannerSymbol.EQUAL_SIGN, ScannerState.ASSIGNMENT);
        map.put(ScannerSymbol.SLASH, ScannerState.SLASH);
        map.put(ScannerSymbol.LESS_THAN, ScannerState.LESS_THAN);
        map.put(ScannerSymbol.GREATER_THAN, ScannerState.GREATER_THAN);
        map.put(ScannerSymbol.AMPERSAND, ScannerState.AMPERSAND);
        map.put(ScannerSymbol.BAR, ScannerState.BAR);
        map.put(ScannerSymbol.APOSTROPHE, ScannerState.APOSTROPHE);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.BRACKET, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DIGIT, ScannerState.NUMERIC);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.PUNCTUATION, ScannerState.PUNCTUATION);
        map.put(ScannerSymbol.MINUS, ScannerState.MINUS);
        map.put(ScannerSymbol.EXCLAMATION_MARK, ScannerState.EXCLAMATION_MARK);
        map.put(ScannerSymbol.SLASH, ScannerState.SLASH);
        map.put(ScannerSymbol.APOSTROPHE, ScannerState.APOSTROPHE);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.PUNCTUATION, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DIGIT, ScannerState.NUMERIC);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.PUNCTUATION, ScannerState.PUNCTUATION);
        map.put(ScannerSymbol.SLASH, ScannerState.SLASH);
        map.put(ScannerSymbol.APOSTROPHE, ScannerState.APOSTROPHE);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.OPERATOR, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DIGIT, ScannerState.NUMERIC);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.MINUS, ScannerState.MINUS);
        map.put(ScannerSymbol.EXCLAMATION_MARK, ScannerState.EXCLAMATION_MARK);
        map.put(ScannerSymbol.EQUAL_SIGN, ScannerState.EQUAL);
        map.put(ScannerSymbol.APOSTROPHE, ScannerState.APOSTROPHE);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.ASSIGNMENT, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DIGIT, ScannerState.NUMERIC);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.MINUS, ScannerState.MINUS);
        map.put(ScannerSymbol.EQUAL_SIGN, ScannerState.LESS_THAN_OR_EQUAL);
        map.put(ScannerSymbol.APOSTROPHE, ScannerState.APOSTROPHE);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.LESS_THAN, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DIGIT, ScannerState.NUMERIC);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.MINUS, ScannerState.MINUS);
        map.put(ScannerSymbol.EQUAL_SIGN, ScannerState.GREATER_THAN_OR_EQUAL);
        map.put(ScannerSymbol.APOSTROPHE, ScannerState.APOSTROPHE);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.GREATER_THAN, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DIGIT, ScannerState.NUMERIC);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.MINUS, ScannerState.MINUS);
        map.put(ScannerSymbol.EXCLAMATION_MARK, ScannerState.EXCLAMATION_MARK);
        map.put(ScannerSymbol.EQUAL_SIGN, ScannerState.NOT_EQUAL);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.EXCLAMATION_MARK, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DIGIT, ScannerState.NUMERIC);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.MINUS, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DIGIT, ScannerState.NUMERIC);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.MINUS, ScannerState.MINUS);
        map.put(ScannerSymbol.EXCLAMATION_MARK, ScannerState.EXCLAMATION_MARK);
        map.put(ScannerSymbol.APOSTROPHE, ScannerState.APOSTROPHE);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.EQUAL, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DIGIT, ScannerState.NUMERIC);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.MINUS, ScannerState.MINUS);
        map.put(ScannerSymbol.EXCLAMATION_MARK, ScannerState.EXCLAMATION_MARK);
        map.put(ScannerSymbol.APOSTROPHE, ScannerState.APOSTROPHE);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.LESS_THAN_OR_EQUAL, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DIGIT, ScannerState.NUMERIC);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.MINUS, ScannerState.MINUS);
        map.put(ScannerSymbol.EXCLAMATION_MARK, ScannerState.EXCLAMATION_MARK);
        map.put(ScannerSymbol.APOSTROPHE, ScannerState.APOSTROPHE);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.GREATER_THAN_OR_EQUAL, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DIGIT, ScannerState.NUMERIC);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.MINUS, ScannerState.MINUS);
        map.put(ScannerSymbol.EXCLAMATION_MARK, ScannerState.EXCLAMATION_MARK);
        map.put(ScannerSymbol.APOSTROPHE, ScannerState.APOSTROPHE);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.NOT_EQUAL, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DIGIT, ScannerState.NUMERIC);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.MINUS, ScannerState.MINUS);
        map.put(ScannerSymbol.EXCLAMATION_MARK, ScannerState.EXCLAMATION_MARK);
        map.put(ScannerSymbol.APOSTROPHE, ScannerState.APOSTROPHE);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.AND, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DIGIT, ScannerState.NUMERIC);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.MINUS, ScannerState.MINUS);
        map.put(ScannerSymbol.EXCLAMATION_MARK, ScannerState.EXCLAMATION_MARK);
        map.put(ScannerSymbol.APOSTROPHE, ScannerState.APOSTROPHE);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.OR, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        map.put(ScannerSymbol.DIGIT, ScannerState.NUMERIC);
        map.put(ScannerSymbol.BRACKET, ScannerState.BRACKET);
        map.put(ScannerSymbol.STAR, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.SLASH, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.START);
        map.put(ScannerSymbol.SPACE, ScannerState.START);
        transitionMap.put(ScannerState.SLASH, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.IDENTIFIER);
        transitionMap.put(ScannerState.FIELD, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.AMPERSAND, ScannerState.AND);
        transitionMap.put(ScannerState.AMPERSAND, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.BAR, ScannerState.OR);
        transitionMap.put(ScannerState.BAR, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.DIGIT, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.BRACKET, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.PUNCTUATION, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.STAR, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.PLUS, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.PERCENT, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.COLON, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.MINUS, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.EXCLAMATION_MARK, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.EQUAL_SIGN, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.SLASH, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.LESS_THAN, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.GREATER_THAN, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.AMPERSAND, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.BAR, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.UNDERSCORE, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.DOT, ScannerState.ONE_LETTER);
        map.put(ScannerSymbol.SPACE, ScannerState.ONE_LETTER);
        transitionMap.put(ScannerState.APOSTROPHE, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.APOSTROPHE, ScannerState.CHARACTER);
        transitionMap.put(ScannerState.ONE_LETTER, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.DIGIT, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.BRACKET, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.PUNCTUATION, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.STAR, ScannerState.STAR);
        map.put(ScannerSymbol.PLUS, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.PERCENT, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.COLON, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.MINUS, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.EXCLAMATION_MARK, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.EQUAL_SIGN, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.SLASH, ScannerState.START);
        map.put(ScannerSymbol.LESS_THAN, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.GREATER_THAN, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.AMPERSAND, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.BAR, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.UNDERSCORE, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.DOT, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.APOSTROPHE, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.TAB, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.SPACE, ScannerState.MULTI_LINE_COMMENT);
        transitionMap.put(ScannerState.STAR, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.DIGIT, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.BRACKET, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.PUNCTUATION, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.STAR, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.PLUS, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.PERCENT, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.COLON, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.MINUS, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.EXCLAMATION_MARK, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.EQUAL_SIGN, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.SLASH, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.LESS_THAN, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.GREATER_THAN, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.AMPERSAND, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.BAR, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.UNDERSCORE, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.DOT, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.APOSTROPHE, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.START);
        map.put(ScannerSymbol.TAB, ScannerState.ONE_LINE_COMMENT);
        map.put(ScannerSymbol.SPACE, ScannerState.ONE_LINE_COMMENT);
        transitionMap.put(ScannerState.ONE_LINE_COMMENT, map);

        map = new HashMap<ScannerSymbol, ScannerState>();
        map.put(ScannerSymbol.LETTER, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.DIGIT, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.BRACKET, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.PUNCTUATION, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.STAR, ScannerState.STAR);
        map.put(ScannerSymbol.PLUS, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.PERCENT, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.COLON, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.MINUS, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.EXCLAMATION_MARK, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.EQUAL_SIGN, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.SLASH, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.LESS_THAN, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.GREATER_THAN, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.AMPERSAND, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.BAR, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.UNDERSCORE, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.DOT, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.APOSTROPHE, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.NEW_LINE, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.TAB, ScannerState.MULTI_LINE_COMMENT);
        map.put(ScannerSymbol.SPACE, ScannerState.MULTI_LINE_COMMENT);
        transitionMap.put(ScannerState.MULTI_LINE_COMMENT, map);

        // Accept state list
        acceptStateList.add(ScannerState.START);
        acceptStateList.add(ScannerState.IDENTIFIER);
        acceptStateList.add(ScannerState.NUMERIC);
        acceptStateList.add(ScannerState.CHARACTER);
        acceptStateList.add(ScannerState.BRACKET);
        acceptStateList.add(ScannerState.PUNCTUATION);
        acceptStateList.add(ScannerState.OPERATOR);
        acceptStateList.add(ScannerState.ASSIGNMENT);
        acceptStateList.add(ScannerState.LESS_THAN);
        acceptStateList.add(ScannerState.GREATER_THAN);
        acceptStateList.add(ScannerState.EXCLAMATION_MARK);
        acceptStateList.add(ScannerState.MINUS);
        acceptStateList.add(ScannerState.EQUAL);
        acceptStateList.add(ScannerState.LESS_THAN_OR_EQUAL);
        acceptStateList.add(ScannerState.GREATER_THAN_OR_EQUAL);
        acceptStateList.add(ScannerState.NOT_EQUAL);
        acceptStateList.add(ScannerState.AND);
        acceptStateList.add(ScannerState.OR);
        acceptStateList.add(ScannerState.SLASH);
        acceptStateList.add(ScannerState.FIELD);
    }

    private ScannerState m_currentState;

    public ScannerAutomaton() {
        m_currentState = ScannerState.START;
    }

    public ScannerState proceed(char c) {
        String SYMBOLS_BR = "()[]{}";
        String SYMBOLS_PNC = ";,";

        ScannerState state;
        if (Character.isLetter(c)) {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.LETTER);
        } else if (Character.isDigit(c)) {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.DIGIT);
        } else if (SYMBOLS_BR.indexOf(c) != -1) {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.BRACKET);
        } else if (SYMBOLS_PNC.indexOf(c) != -1) {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.PUNCTUATION);
        } else if (c == '+') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.PLUS);
        } else if (c == '*') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.STAR);
        } else if (c == '%') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.PERCENT);
        } else if (c == ':') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.COLON);
        } else if (c == '-') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.MINUS);
        } else if (c == '!') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.EXCLAMATION_MARK);
        } else if (c == '=') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.EQUAL_SIGN);
        } else if (c == '/') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.SLASH);
        } else if (c == '<') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.LESS_THAN);
        } else if (c == '>') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.GREATER_THAN);
        } else if (c == '&') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.AMPERSAND);
        } else if (c == '|') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.BAR);
        } else if (c == '_') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.UNDERSCORE);
        } else if (c == '.') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.DOT);
        } else if (c == '\'') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.APOSTROPHE);
        } else if (c == '\n') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.NEW_LINE);
        } else if (c == '\t') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.TAB);
        } else if (c == ' ') {
            state = ScannerAutomaton.transitionMap.get(m_currentState).get(ScannerSymbol.SPACE);
        } else {
            state = null;
        }

        m_currentState = state;
        return state;
    }
}
