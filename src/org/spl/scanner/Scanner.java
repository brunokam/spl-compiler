package org.spl.scanner;

import org.spl.common.TokenInfo;
import org.spl.common.Token;
import org.spl.scanner.exception.ScanningException;
import org.spl.scanner.exception.TokenizationException;

import java.util.LinkedList;

public class Scanner {

    private static class ScannerIterator {

        private String m_input; // Input string
        private int m_inputPos; // Position of the character in input which is currently processed
        private LinkedList<TokenInfo> m_tokenList; // List of tokens
        private String m_tokenString; // Current string which represents a token under construction

        public ScannerIterator(String in) {
            m_input = in;
            m_inputPos = 0;
            m_tokenList = new LinkedList<TokenInfo>();
            m_tokenString = "";
        }

        public boolean hasNext() {
            return m_inputPos < m_input.length();
        }

        public char next() {
            if (m_inputPos >= m_input.length()) {
                throw new IndexOutOfBoundsException();
            }

            return m_input.charAt(m_inputPos++);
        }

        public int length() {
            return m_tokenString.length();
        }

        public void pass(char c) {
            if ("\n\t ".indexOf(c) == -1) {
                m_tokenString += c;
            }
        }

        public void clear() {
            m_tokenString = "";
        }

        public void close(PreToken preToken) throws TokenizationException {
            Token finalToken;
            int lineNumber = getLineNumber();
            int columnNumber = getColumnNumber() - m_tokenString.length();

            if (ScannerTokenMaps.finalTokenMap.get(preToken).containsKey(m_tokenString)) {
                finalToken = ScannerTokenMaps.finalTokenMap.get(preToken).get(m_tokenString);
            } else if (ScannerTokenMaps.finalTokenMap.get(preToken).containsKey(ScannerTokenMaps.CUSTOM_TOKEN)) {
                finalToken = ScannerTokenMaps.finalTokenMap.get(preToken).get(ScannerTokenMaps.CUSTOM_TOKEN);
            } else {
                throw new TokenizationException(m_tokenString, lineNumber, columnNumber, m_tokenList);
            }

            m_tokenList.add(new TokenInfo(finalToken, m_tokenString, lineNumber, columnNumber));
            m_tokenString = "";
        }

        public LinkedList<TokenInfo> getTokenList() {
            return m_tokenList;
        }

        public int getLineNumber() {
            int lines = 1;
            int currentLine = -1;
            for (int i = 0; i < m_input.length(); ++i) {
                if (m_input.charAt(i) == '\n') {
                    if (currentLine == -1 && i >= m_inputPos - 1) {
                        currentLine = lines;
                    }
                    ++lines;
                }
            }

            return currentLine;
        }

        public int getColumnNumber() {
            int columnNumber = 0;
            for (int i = m_inputPos - 1; i >= 0 && m_input.charAt(i) != '\n'; --i) {
                ++columnNumber;
            }

            return columnNumber;
        }
    }

    public static LinkedList<TokenInfo> scan(String code)
            throws ScanningException, TokenizationException {
        ScannerIterator it = new ScannerIterator(code);
        Automaton automaton = new Automaton();

        State lastState, state = State.START;
        while (it.hasNext()) {
            char c = it.next();

            lastState = state;
            state = automaton.proceed(c);

            if (state == null) {
                it.close(ScannerTokenMaps.preTokenMap.get(lastState));
                throw new ScanningException(c, it.getLineNumber(), it.getColumnNumber(), it.getTokenList());
            }

            // If the transition starts and ends in different states and previous state is not the start one
            if (lastState != State.START && (state != lastState ||
                    (lastState == State.BRACKET && state == State.BRACKET))) { // Adjacent brackets hack
                // If the transition starts and ends in two accept states
                if (Automaton.acceptStateList.contains(lastState) &&
                        Automaton.acceptStateList.contains(state)) {
                    it.close(ScannerTokenMaps.preTokenMap.get(lastState));
                }

                it.pass(c);

                if (state == State.START) {
                    it.clear();
                }
            } else {
                it.pass(c);
            }
        }

        TokenListPostprocessor postprocessor = new TokenListPostprocessor();
        return postprocessor.run(it.getTokenList());
    }
}
