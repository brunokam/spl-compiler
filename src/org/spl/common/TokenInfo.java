package org.spl.common;

public class TokenInfo {

    private Token m_token;
    private String m_string;
    private int m_lineNumber;
    private int m_columnNumber;

    public TokenInfo(Token token, String string) {
        m_token = token;
        m_string = string;
        m_lineNumber = -1;
        m_columnNumber = -1;
    }

    public TokenInfo(Token token, String string, int lineNumber, int columnNumber) {
        m_token = token;
        m_string = string;
        m_lineNumber = lineNumber;
        m_columnNumber = columnNumber;
    }

    public Token getToken() {
        return m_token;
    }

    public String getString() {
        return m_string;
    }

    public int getLineNumber() {
        return m_lineNumber;
    }

    public int getColumnNumber() {
        return m_columnNumber;
    }

    public void setLineNumber(int lineNumber) {
        m_lineNumber = lineNumber;
    }

    public void setColumnNumber(int columnNumber) {
        m_columnNumber = columnNumber;
    }
}
