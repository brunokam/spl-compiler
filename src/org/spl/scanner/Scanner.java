package org.spl.scanner;

import com.sun.tools.javac.util.Pair;
import org.spl.scanner.exception.ScanningException;
import org.spl.scanner.exception.TokenizationException;

import java.util.LinkedList;

public class Scanner {

    private static class ScannerIterator {

        private String m_input; // Input string
        private int m_inputPos; // Position of the character in input which is currently processed
        private LinkedList<Pair<ScannerToken.Final, String>> m_tokenList; // List of tokens
        private String m_tokenString; // Current string which represents a token under construction

        public ScannerIterator(String in) {
            m_input = in;
            m_inputPos = 0;
            m_tokenList = new LinkedList<Pair<ScannerToken.Final, String>>();
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

        public void close(ScannerToken.Preliminary preToken) throws TokenizationException {
            ScannerToken.Final finalToken;
            if (ScannerTokenMaps.finalTokenMap.get(preToken).containsKey(m_tokenString)) {
                finalToken = ScannerTokenMaps.finalTokenMap.get(preToken).get(m_tokenString);
            } else if (ScannerTokenMaps.finalTokenMap.get(preToken).containsKey(ScannerTokenMaps.CUSTOM_TOKEN)) {

                finalToken = ScannerTokenMaps.finalTokenMap.get(preToken).get(ScannerTokenMaps.CUSTOM_TOKEN);
            } else {
                throw new TokenizationException(m_tokenString, getLineNumber(),
                        getColumnNumber() - m_tokenString.length(), m_tokenList);
            }

            m_tokenList.add(new Pair<ScannerToken.Final, String>(finalToken, m_tokenString));
            m_tokenString = "";
        }

        public LinkedList<Pair<ScannerToken.Final, String>> getTokenList() {
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

    public static LinkedList<Pair<ScannerToken.Final, String>> scan(String code)
            throws ScanningException, TokenizationException {
        ScannerIterator it = new ScannerIterator(code);
        ScannerAutomaton automaton = new ScannerAutomaton();

        ScannerState lastState, state = ScannerState.START;
        while (it.hasNext()) {
            char c = it.next();

            lastState = state;
            state = automaton.proceed(c);

            if (state == null) {
                it.close(ScannerTokenMaps.preTokenMap.get(lastState));
                throw new ScanningException(c, it.getLineNumber(), it.getColumnNumber(), it.getTokenList());
            }

            // If the transition starts and ends in different states and previous state is not the start one
            if (lastState != ScannerState.START && state != lastState) {
                // If the transition starts and ends in two accept states
                if (ScannerAutomaton.acceptStateList.contains(lastState) &&
                        ScannerAutomaton.acceptStateList.contains(state)) {
                    it.close(ScannerTokenMaps.preTokenMap.get(lastState));
                }

                it.pass(c);

                if (state == ScannerState.START) {
                    it.clear();
                }
            } else {
                it.pass(c);
            }
        }

        return it.getTokenList();
    }
}
