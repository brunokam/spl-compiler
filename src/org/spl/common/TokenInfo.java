package org.spl.common;

public class TokenInfo {

    private Token m_token;
    private String m_string;

    public TokenInfo(Token token, String string) {
        m_token = token;
        m_string = string;
    }

    public Token getToken() {
        return m_token;
    }

    public String getString() {
        return m_string;
    }
}
