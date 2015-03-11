package org.spl.scanner;

import org.spl.common.Symbol;

public class ASTNodeObject {

    private Symbol m_symbol;

    public ASTNodeObject(Symbol symbol) {
        m_symbol = symbol;
    }

    public Symbol getSymbol() {
        return m_symbol;
    }

    @Override
    public String toString() {
        return m_symbol.toString();
    }
}
