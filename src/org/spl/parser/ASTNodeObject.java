package org.spl.parser;

import org.spl.common.Symbol;

public class ASTNodeObject {

    private Symbol m_symbol;
    private String m_string;

    public ASTNodeObject(Symbol symbol) {
        m_symbol = symbol;
        m_string = null;
    }

    public ASTNodeObject(Symbol symbol, String string) {
        m_symbol = symbol;
        m_string = string;
    }

    public Symbol getSymbol() {
        return m_symbol;
    }

    public String getString() {
        return m_string;
    }

    @Override
    public String toString() {
        return m_symbol.toString();
    }
}
