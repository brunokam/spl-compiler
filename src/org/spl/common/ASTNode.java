package org.spl.common;

import org.spl.common.type.Type;
import org.spl.common.structure.Scope;

import javax.swing.tree.DefaultMutableTreeNode;

public class ASTNode extends DefaultMutableTreeNode {

    private Symbol m_symbol;
    private Token m_token;
    private String m_string;
    private Type m_type;
    private Type m_tempType;
    private Scope m_scope;
    private int m_lineNumber;
    private int m_columnNumber;

    public ASTNode(Object userObject) {
        super(userObject);
    }

    public ASTNode(Symbol symbol) {
        m_symbol = symbol;
        m_token = null;
        m_string = null;
        m_type = null;
        m_tempType = null;
        m_scope = null;
    }

    public ASTNode(Symbol symbol, String string) {
        m_symbol = symbol;
        m_token = null;
        m_string = string;
        m_type = null;
        m_tempType = null;
        m_scope = null;
    }

    public ASTNode(Symbol symbol, Token token, String string, int lineNumber, int columnNumber) {
        m_symbol = symbol;
        m_token = token;
        m_string = string;
        m_type = null;
        m_tempType = null;
        m_scope = null;
        m_lineNumber = lineNumber;
        m_columnNumber = columnNumber;
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

    public Type getType() {
        return m_type;
    }

    public Type getTempType() {
        return m_tempType;
    }

    public Scope getScopeObject() {
        return m_scope;
    }

    public int getLineNumber() {
        return m_lineNumber;
    }

    public int getColumnNumber() {
        return m_columnNumber;
    }

    public ASTNode setSymbol(Symbol symbol) {
        m_symbol = symbol;
        return this;
    }

    public ASTNode setToken(Token token) {
        m_token = token;
        return this;
    }

    public ASTNode setString(String string) {
        m_string = string;
        return this;
    }

    public ASTNode setType(Type type) {
        m_type = type;
        return this;
    }

    public ASTNode setTempType(Type tempType) {
        m_tempType = tempType;
        return this;
    }

    public ASTNode setScopeObject(Scope scope) {
        m_scope = scope;
        return this;
    }

    public ASTNode setLineNumber(int lineNumber) {
        m_lineNumber = lineNumber;
        return this;
    }

    public ASTNode setColumnNumber(int columnNumber) {
        m_columnNumber = columnNumber;
        return this;
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
