package org.spl.scanner;

import java.util.ArrayList;
import java.util.HashMap;

public class Automaton {

    private final static HashMap<State, HashMap<SingleCharacter, State>> transitionMap = new HashMap<State, HashMap<SingleCharacter, State>>();
    public final static ArrayList<State> acceptStateList = new ArrayList<State>();

    static {
        // Transition map
        HashMap<SingleCharacter, State> map;

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        map.put(SingleCharacter.DIGIT, State.NUMERIC);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.PUNCTUATION, State.PUNCTUATION);
        map.put(SingleCharacter.STAR, State.OPERATOR);
        map.put(SingleCharacter.PLUS, State.OPERATOR);
        map.put(SingleCharacter.PERCENT, State.OPERATOR);
        map.put(SingleCharacter.COLON, State.OPERATOR);
        map.put(SingleCharacter.MINUS, State.MINUS);
        map.put(SingleCharacter.EXCLAMATION_MARK, State.EXCLAMATION_MARK);
        map.put(SingleCharacter.EQUAL_SIGN, State.ASSIGNMENT);
        map.put(SingleCharacter.SLASH, State.SLASH);
        map.put(SingleCharacter.LESS_THAN, State.LESS_THAN);
        map.put(SingleCharacter.GREATER_THAN, State.GREATER_THAN);
        map.put(SingleCharacter.AMPERSAND, State.AMPERSAND);
        map.put(SingleCharacter.BAR, State.BAR);
        map.put(SingleCharacter.APOSTROPHE, State.APOSTROPHE);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.START, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        map.put(SingleCharacter.DIGIT, State.IDENTIFIER);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.PUNCTUATION, State.PUNCTUATION);
        map.put(SingleCharacter.STAR, State.OPERATOR);
        map.put(SingleCharacter.PLUS, State.OPERATOR);
        map.put(SingleCharacter.PERCENT, State.OPERATOR);
        map.put(SingleCharacter.COLON, State.OPERATOR);
        map.put(SingleCharacter.MINUS, State.MINUS);
        map.put(SingleCharacter.EXCLAMATION_MARK, State.EXCLAMATION_MARK);
        map.put(SingleCharacter.EQUAL_SIGN, State.ASSIGNMENT);
        map.put(SingleCharacter.SLASH, State.SLASH);
        map.put(SingleCharacter.LESS_THAN, State.LESS_THAN);
        map.put(SingleCharacter.GREATER_THAN, State.GREATER_THAN);
        map.put(SingleCharacter.AMPERSAND, State.AMPERSAND);
        map.put(SingleCharacter.BAR, State.BAR);
        map.put(SingleCharacter.UNDERSCORE, State.IDENTIFIER);
        map.put(SingleCharacter.DOT, State.FIELD);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.IDENTIFIER, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.DIGIT, State.NUMERIC);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.PUNCTUATION, State.PUNCTUATION);
        map.put(SingleCharacter.STAR, State.OPERATOR);
        map.put(SingleCharacter.PLUS, State.OPERATOR);
        map.put(SingleCharacter.PERCENT, State.OPERATOR);
        map.put(SingleCharacter.COLON, State.OPERATOR);
        map.put(SingleCharacter.MINUS, State.MINUS);
        map.put(SingleCharacter.EXCLAMATION_MARK, State.EXCLAMATION_MARK);
        map.put(SingleCharacter.EQUAL_SIGN, State.ASSIGNMENT);
        map.put(SingleCharacter.SLASH, State.SLASH);
        map.put(SingleCharacter.LESS_THAN, State.LESS_THAN);
        map.put(SingleCharacter.GREATER_THAN, State.GREATER_THAN);
        map.put(SingleCharacter.AMPERSAND, State.AMPERSAND);
        map.put(SingleCharacter.BAR, State.BAR);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.NUMERIC, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.PUNCTUATION, State.PUNCTUATION);
        map.put(SingleCharacter.PLUS, State.OPERATOR);
        map.put(SingleCharacter.COLON, State.OPERATOR);
        map.put(SingleCharacter.MINUS, State.MINUS);
        map.put(SingleCharacter.EQUAL_SIGN, State.ASSIGNMENT);
        map.put(SingleCharacter.LESS_THAN, State.LESS_THAN);
        map.put(SingleCharacter.GREATER_THAN, State.GREATER_THAN);
        map.put(SingleCharacter.AMPERSAND, State.AMPERSAND);
        map.put(SingleCharacter.BAR, State.BAR);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.CHARACTER, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        map.put(SingleCharacter.DIGIT, State.NUMERIC);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.PUNCTUATION, State.PUNCTUATION);
        map.put(SingleCharacter.STAR, State.OPERATOR);
        map.put(SingleCharacter.PLUS, State.OPERATOR);
        map.put(SingleCharacter.PERCENT, State.OPERATOR);
        map.put(SingleCharacter.COLON, State.OPERATOR);
        map.put(SingleCharacter.MINUS, State.MINUS);
        map.put(SingleCharacter.EXCLAMATION_MARK, State.EXCLAMATION_MARK);
        map.put(SingleCharacter.EQUAL_SIGN, State.ASSIGNMENT);
        map.put(SingleCharacter.SLASH, State.SLASH);
        map.put(SingleCharacter.LESS_THAN, State.LESS_THAN);
        map.put(SingleCharacter.GREATER_THAN, State.GREATER_THAN);
        map.put(SingleCharacter.AMPERSAND, State.AMPERSAND);
        map.put(SingleCharacter.BAR, State.BAR);
        map.put(SingleCharacter.APOSTROPHE, State.APOSTROPHE);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.BRACKET, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        map.put(SingleCharacter.DIGIT, State.NUMERIC);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.PUNCTUATION, State.PUNCTUATION);
        map.put(SingleCharacter.MINUS, State.MINUS);
        map.put(SingleCharacter.EXCLAMATION_MARK, State.EXCLAMATION_MARK);
        map.put(SingleCharacter.SLASH, State.SLASH);
        map.put(SingleCharacter.APOSTROPHE, State.APOSTROPHE);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.PUNCTUATION, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        map.put(SingleCharacter.DIGIT, State.NUMERIC);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.PUNCTUATION, State.PUNCTUATION);
        map.put(SingleCharacter.SLASH, State.SLASH);
        map.put(SingleCharacter.APOSTROPHE, State.APOSTROPHE);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.OPERATOR, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        map.put(SingleCharacter.DIGIT, State.NUMERIC);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.MINUS, State.MINUS);
        map.put(SingleCharacter.EXCLAMATION_MARK, State.EXCLAMATION_MARK);
        map.put(SingleCharacter.EQUAL_SIGN, State.EQUAL);
        map.put(SingleCharacter.APOSTROPHE, State.APOSTROPHE);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.ASSIGNMENT, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        map.put(SingleCharacter.DIGIT, State.NUMERIC);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.MINUS, State.MINUS);
        map.put(SingleCharacter.EQUAL_SIGN, State.LESS_THAN_OR_EQUAL);
        map.put(SingleCharacter.APOSTROPHE, State.APOSTROPHE);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.LESS_THAN, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        map.put(SingleCharacter.DIGIT, State.NUMERIC);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.MINUS, State.MINUS);
        map.put(SingleCharacter.EQUAL_SIGN, State.GREATER_THAN_OR_EQUAL);
        map.put(SingleCharacter.APOSTROPHE, State.APOSTROPHE);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.GREATER_THAN, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        map.put(SingleCharacter.DIGIT, State.NUMERIC);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.MINUS, State.MINUS);
        map.put(SingleCharacter.EXCLAMATION_MARK, State.EXCLAMATION_MARK);
        map.put(SingleCharacter.EQUAL_SIGN, State.NOT_EQUAL);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.EXCLAMATION_MARK, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        map.put(SingleCharacter.DIGIT, State.NUMERIC);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.MINUS, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        map.put(SingleCharacter.DIGIT, State.NUMERIC);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.MINUS, State.MINUS);
        map.put(SingleCharacter.EXCLAMATION_MARK, State.EXCLAMATION_MARK);
        map.put(SingleCharacter.APOSTROPHE, State.APOSTROPHE);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.EQUAL, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        map.put(SingleCharacter.DIGIT, State.NUMERIC);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.MINUS, State.MINUS);
        map.put(SingleCharacter.EXCLAMATION_MARK, State.EXCLAMATION_MARK);
        map.put(SingleCharacter.APOSTROPHE, State.APOSTROPHE);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.LESS_THAN_OR_EQUAL, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        map.put(SingleCharacter.DIGIT, State.NUMERIC);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.MINUS, State.MINUS);
        map.put(SingleCharacter.EXCLAMATION_MARK, State.EXCLAMATION_MARK);
        map.put(SingleCharacter.APOSTROPHE, State.APOSTROPHE);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.GREATER_THAN_OR_EQUAL, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        map.put(SingleCharacter.DIGIT, State.NUMERIC);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.MINUS, State.MINUS);
        map.put(SingleCharacter.EXCLAMATION_MARK, State.EXCLAMATION_MARK);
        map.put(SingleCharacter.APOSTROPHE, State.APOSTROPHE);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.NOT_EQUAL, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        map.put(SingleCharacter.DIGIT, State.NUMERIC);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.MINUS, State.MINUS);
        map.put(SingleCharacter.EXCLAMATION_MARK, State.EXCLAMATION_MARK);
        map.put(SingleCharacter.APOSTROPHE, State.APOSTROPHE);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.AND, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        map.put(SingleCharacter.DIGIT, State.NUMERIC);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.MINUS, State.MINUS);
        map.put(SingleCharacter.EXCLAMATION_MARK, State.EXCLAMATION_MARK);
        map.put(SingleCharacter.APOSTROPHE, State.APOSTROPHE);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.OR, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        map.put(SingleCharacter.DIGIT, State.NUMERIC);
        map.put(SingleCharacter.BRACKET, State.BRACKET);
        map.put(SingleCharacter.STAR, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.SLASH, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.START);
        map.put(SingleCharacter.SPACE, State.START);
        transitionMap.put(State.SLASH, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.IDENTIFIER);
        transitionMap.put(State.FIELD, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.AMPERSAND, State.AND);
        transitionMap.put(State.AMPERSAND, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.BAR, State.OR);
        transitionMap.put(State.BAR, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.ONE_LETTER);
        map.put(SingleCharacter.DIGIT, State.ONE_LETTER);
        map.put(SingleCharacter.BRACKET, State.ONE_LETTER);
        map.put(SingleCharacter.PUNCTUATION, State.ONE_LETTER);
        map.put(SingleCharacter.STAR, State.ONE_LETTER);
        map.put(SingleCharacter.PLUS, State.ONE_LETTER);
        map.put(SingleCharacter.PERCENT, State.ONE_LETTER);
        map.put(SingleCharacter.COLON, State.ONE_LETTER);
        map.put(SingleCharacter.MINUS, State.ONE_LETTER);
        map.put(SingleCharacter.EXCLAMATION_MARK, State.ONE_LETTER);
        map.put(SingleCharacter.EQUAL_SIGN, State.ONE_LETTER);
        map.put(SingleCharacter.SLASH, State.ONE_LETTER);
        map.put(SingleCharacter.LESS_THAN, State.ONE_LETTER);
        map.put(SingleCharacter.GREATER_THAN, State.ONE_LETTER);
        map.put(SingleCharacter.AMPERSAND, State.ONE_LETTER);
        map.put(SingleCharacter.BAR, State.ONE_LETTER);
        map.put(SingleCharacter.UNDERSCORE, State.ONE_LETTER);
        map.put(SingleCharacter.DOT, State.ONE_LETTER);
        map.put(SingleCharacter.SPACE, State.ONE_LETTER);
        transitionMap.put(State.APOSTROPHE, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.APOSTROPHE, State.CHARACTER);
        transitionMap.put(State.ONE_LETTER, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.DIGIT, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.BRACKET, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.PUNCTUATION, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.STAR, State.STAR);
        map.put(SingleCharacter.PLUS, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.PERCENT, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.COLON, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.MINUS, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.EXCLAMATION_MARK, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.EQUAL_SIGN, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.SLASH, State.START);
        map.put(SingleCharacter.LESS_THAN, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.GREATER_THAN, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.AMPERSAND, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.BAR, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.UNDERSCORE, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.DOT, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.APOSTROPHE, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.NEW_LINE, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.TAB, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.SPACE, State.MULTI_LINE_COMMENT);
        transitionMap.put(State.STAR, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.DIGIT, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.BRACKET, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.PUNCTUATION, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.STAR, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.PLUS, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.PERCENT, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.COLON, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.MINUS, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.EXCLAMATION_MARK, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.EQUAL_SIGN, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.SLASH, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.LESS_THAN, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.GREATER_THAN, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.AMPERSAND, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.BAR, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.UNDERSCORE, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.DOT, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.APOSTROPHE, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.NEW_LINE, State.START);
        map.put(SingleCharacter.TAB, State.ONE_LINE_COMMENT);
        map.put(SingleCharacter.SPACE, State.ONE_LINE_COMMENT);
        transitionMap.put(State.ONE_LINE_COMMENT, map);

        map = new HashMap<SingleCharacter, State>();
        map.put(SingleCharacter.LETTER, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.DIGIT, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.BRACKET, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.PUNCTUATION, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.STAR, State.STAR);
        map.put(SingleCharacter.PLUS, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.PERCENT, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.COLON, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.MINUS, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.EXCLAMATION_MARK, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.EQUAL_SIGN, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.SLASH, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.LESS_THAN, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.GREATER_THAN, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.AMPERSAND, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.BAR, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.UNDERSCORE, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.DOT, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.APOSTROPHE, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.NEW_LINE, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.TAB, State.MULTI_LINE_COMMENT);
        map.put(SingleCharacter.SPACE, State.MULTI_LINE_COMMENT);
        transitionMap.put(State.MULTI_LINE_COMMENT, map);

        // Accept state list
        acceptStateList.add(State.START);
        acceptStateList.add(State.IDENTIFIER);
        acceptStateList.add(State.NUMERIC);
        acceptStateList.add(State.CHARACTER);
        acceptStateList.add(State.BRACKET);
        acceptStateList.add(State.PUNCTUATION);
        acceptStateList.add(State.OPERATOR);
        acceptStateList.add(State.ASSIGNMENT);
        acceptStateList.add(State.LESS_THAN);
        acceptStateList.add(State.GREATER_THAN);
        acceptStateList.add(State.EXCLAMATION_MARK);
        acceptStateList.add(State.MINUS);
        acceptStateList.add(State.EQUAL);
        acceptStateList.add(State.LESS_THAN_OR_EQUAL);
        acceptStateList.add(State.GREATER_THAN_OR_EQUAL);
        acceptStateList.add(State.NOT_EQUAL);
        acceptStateList.add(State.AND);
        acceptStateList.add(State.OR);
        acceptStateList.add(State.SLASH);
        acceptStateList.add(State.FIELD);
    }

    private State m_currentState;

    public Automaton() {
        m_currentState = State.START;
    }

    public State proceed(char c) {
        String SYMBOLS_BR = "()[]{}";
        String SYMBOLS_PNC = ";,";

        State state;
        if (java.lang.Character.isLetter(c)) {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.LETTER);
        } else if (java.lang.Character.isDigit(c)) {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.DIGIT);
        } else if (SYMBOLS_BR.indexOf(c) != -1) {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.BRACKET);
        } else if (SYMBOLS_PNC.indexOf(c) != -1) {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.PUNCTUATION);
        } else if (c == '+') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.PLUS);
        } else if (c == '*') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.STAR);
        } else if (c == '%') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.PERCENT);
        } else if (c == ':') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.COLON);
        } else if (c == '-') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.MINUS);
        } else if (c == '!') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.EXCLAMATION_MARK);
        } else if (c == '=') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.EQUAL_SIGN);
        } else if (c == '/') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.SLASH);
        } else if (c == '<') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.LESS_THAN);
        } else if (c == '>') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.GREATER_THAN);
        } else if (c == '&') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.AMPERSAND);
        } else if (c == '|') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.BAR);
        } else if (c == '_') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.UNDERSCORE);
        } else if (c == '.') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.DOT);
        } else if (c == '\'') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.APOSTROPHE);
        } else if (c == '\n') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.NEW_LINE);
        } else if (c == '\t') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.TAB);
        } else if (c == ' ') {
            state = Automaton.transitionMap.get(m_currentState).get(SingleCharacter.SPACE);
        } else {
            state = null;
        }

        m_currentState = state;
        return state;
    }
}
