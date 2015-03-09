package org.spl.scanner.exception;

import org.spl.common.TokenInfo;

import java.util.LinkedList;

public class ScanningException extends Exception {

    private char m_invalidChar;
    private int m_lineNumber;
    private int m_columnNumber;
    private LinkedList<TokenInfo> m_tokenList;

    public ScanningException(char invalidChar, int lineNumber, int columnNumber,
                             LinkedList<TokenInfo> tokenList) {
        super("Error: invalid character \"" + invalidChar + "\" after \"" + tokenList.getLast().getString() +
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

    public LinkedList<TokenInfo> getTokenList() {
        return m_tokenList;
    }
}
