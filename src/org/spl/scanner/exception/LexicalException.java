package org.spl.scanner.exception;

import com.sun.tools.javac.util.Pair;
import org.spl.scanner.ScannerToken;

import java.util.LinkedList;

public class LexicalException extends Exception {

    private char m_invalidChar;
    private int m_lineNumber;
    private int m_columnNumber;
    private LinkedList<Pair<ScannerToken, String>> m_tokenList;

    public LexicalException(char invalidChar, int lineNumber, int columnNumber, LinkedList<Pair<ScannerToken, String>> tokenList) {
        super("Error: invalid character \"" + invalidChar + "\" after \"" + tokenList.getLast().snd +
                "\" at line " + lineNumber + ", column " + columnNumber);
        m_invalidChar = invalidChar;
        m_lineNumber = lineNumber;
        m_columnNumber = columnNumber;
        m_tokenList = tokenList;
    }

    public char getInvalidChar() {
        return m_invalidChar;
    }

    public int getLineNumber() {
        return m_lineNumber;
    }

    public int getColumnNumber() {
        return m_columnNumber;
    }

    public LinkedList<Pair<ScannerToken, String>> getTokenList() {
        return m_tokenList;
    }
}
