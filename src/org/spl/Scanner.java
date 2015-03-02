package org.spl;

import com.sun.tools.javac.util.Pair;

import java.util.LinkedList;

public class Scanner {

    private static class ScannerIterator {

        private String input; // Input string
        private int inputPos; // Position of the character in input which is currently processed
        private LinkedList<Pair<Token, String>> tokenList; // List of tokenList
        private String preToken; // Current string which represents a token under construction

        public ScannerIterator(String in) {
            input = in;
            inputPos = 0;
            tokenList = new LinkedList<Pair<Token, String>>();
            preToken = "";
        }

        public boolean hasNext() {
            return inputPos < input.length();
        }

        public char next() {
            if (inputPos >= input.length()) {
                throw new IndexOutOfBoundsException();
            }

            return input.charAt(inputPos++);
        }

        public int length() {
            return preToken.length();
        }

        public void pass(char c) {
            if ("\n\t ".indexOf(c) == -1) {
                preToken += c;
            }
        }

        public void clear() {
            preToken = "";
        }

        public void close(Token token) {
            tokenList.add(new Pair<Token, String>(token, preToken));
            preToken = "";
        }

        public LinkedList<Pair<Token, String>> getTokenList() {
            return tokenList;
        }
    }

    public static LinkedList<Pair<Token, String>> scan(String code) {
        ScannerIterator it = new ScannerIterator(code);
        Automaton automaton = new Automaton();

        State lastState, state = State.START;
        while (it.hasNext()) {
            char c = it.next();

            lastState = state;
            state = automaton.proceed(c);

            // If the transition starts and ends in different states and previous state is not the start one
            if (lastState != State.START && state != lastState) {
                // If the transition starts and ends in two accept states
                if (Static.acceptStateList.contains(lastState) && Static.acceptStateList.contains(state)) {
                    it.close(Static.tokenMap.get(lastState));
                    it.pass(c);
                } else {
                    it.clear();
                }
            } else {
                it.pass(c);
            }
        }

        return it.getTokenList();
    }
}
