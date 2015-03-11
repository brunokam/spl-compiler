package org.spl.parser.exception;

import org.spl.common.TokenInfo;

import java.util.LinkedList;

public class ParsingException extends Exception {

    private String m_tokenString;
    private int m_lineNumber;
    private int m_columnNumber;
    private LinkedList<TokenInfo> m_tokenList;

    public ParsingException(String tokenString, int lineNumber, int columnNumber, LinkedList<TokenInfo> tokenList) {
        super("Error: invalid string \"" + tokenString + "\" at line " + lineNumber + ", column " + columnNumber);
        m_tokenString = tokenString;
        m_lineNumber = lineNumber;
        m_columnNumber = columnNumber;
        m_tokenList = tokenList;
    }

    public String getInvalidString() {
        return m_tokenString;
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
