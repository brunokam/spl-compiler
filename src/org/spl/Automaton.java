package org.spl;

public class Automaton {

    private State currentState;

    public Automaton() {
        currentState = State.START;
    }

    public State proceed(char c) {
        String SYMBOLS_BR = "()[]{}";
        String SYMBOLS_PNC = ";,";

        State state;
        if (Character.isLetter(c)) {
            state = Static.transitionMap.get(currentState).get(Symbol.LETTER);
        } else if (Character.isDigit(c)) {
            state = Static.transitionMap.get(currentState).get(Symbol.DIGIT);
        } else if (SYMBOLS_BR.indexOf(c) != -1) {
            state = Static.transitionMap.get(currentState).get(Symbol.BRACKET);
        } else if (SYMBOLS_PNC.indexOf(c) != -1) {
            state = Static.transitionMap.get(currentState).get(Symbol.PUNCTUATION);
        } else if (c == '+') {
            state = Static.transitionMap.get(currentState).get(Symbol.PLUS);
        } else if (c == '*') {
            state = Static.transitionMap.get(currentState).get(Symbol.STAR);
        } else if (c == '%') {
            state = Static.transitionMap.get(currentState).get(Symbol.PERCENT);
        } else if (c == ':') {
            state = Static.transitionMap.get(currentState).get(Symbol.COLON);
        } else if (c == '-') {
            state = Static.transitionMap.get(currentState).get(Symbol.MINUS);
        } else if (c == '!') {
            state = Static.transitionMap.get(currentState).get(Symbol.EXCLAMATION_MARK);
        } else if (c == '=') {
            state = Static.transitionMap.get(currentState).get(Symbol.EQUAL_SIGN);
        } else if (c == '/') {
            state = Static.transitionMap.get(currentState).get(Symbol.SLASH);
        } else if (c == '<') {
            state = Static.transitionMap.get(currentState).get(Symbol.LESS_THAN);
        } else if (c == '>') {
            state = Static.transitionMap.get(currentState).get(Symbol.GREATER_THAN);
        } else if (c == '&') {
            state = Static.transitionMap.get(currentState).get(Symbol.AMPERSAND);
        } else if (c == '|') {
            state = Static.transitionMap.get(currentState).get(Symbol.BAR);
        } else if (c == '_') {
            state = Static.transitionMap.get(currentState).get(Symbol.UNDERSCORE);
        } else if (c == '.') {
            state = Static.transitionMap.get(currentState).get(Symbol.DOT);
        } else if (c == '\n') {
            state = Static.transitionMap.get(currentState).get(Symbol.NEW_LINE);
        } else if (c == '\t') {
            state = Static.transitionMap.get(currentState).get(Symbol.TAB);
        } else if (c == ' ') {
            state = Static.transitionMap.get(currentState).get(Symbol.SPACE);
        } else {
            state = null;
        }

        if (state == null) {
            throw new RuntimeException();
        }

        currentState = state;
        return state;
    }
}
