package org.spl.parser;

import org.spl.common.Symbol;
import org.spl.common.Token;

public class ASTNodeObject {

    private Symbol m_symbol;
    private Token m_token;
    private String m_string;

    public ASTNodeObject(Symbol symbol) {
        m_symbol = symbol;
        m_token = null;
        m_string = null;
    }

    public ASTNodeObject(Symbol symbol, String string) {
        m_symbol = symbol;
        m_token = null;
        m_string = string;
    }

    public ASTNodeObject(Symbol symbol, Token token, String string) {
        m_symbol = symbol;
        m_token = token;
        m_string = string;
    }

    public Symbol getSymbol() {
        return m_symbol;
    }

    public Token getToken() {
        return m_token;
    }

    public String getString() {
        return m_string;
    }

    public void setSymbol(Symbol symbol) {
        m_symbol = symbol;
    }

    public void setToken(Token token) {
        m_token = token;
    }

    public void setString(String string) {
        m_string = string;
    }

    @Override
    public String toString() {
        if (m_token == null) {
            return m_symbol.toString();
        } else {
            if (m_token == Token.IDENTIFIER || m_token == Token.NUMERIC || m_token == Token.CHARACTER) {
                return m_token.toString() + "_" + m_string;
            } else if (m_token == Token.TYPE_POLYMORPHIC) {
                return "TYPE_" + m_string;
            } else if (m_token == Token.ARRTYPE_POLYMORPHIC) {
                return "ARRTYPE_" + m_string.substring(1, m_string.length() - 1);
            } else {
                return m_token.toString();
            }
        }
    }
}
