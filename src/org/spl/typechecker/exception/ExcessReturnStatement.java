package org.spl.typechecker.exception;

import org.spl.common.ASTNode;

public class ExcessReturnStatement extends Exception {

    private String m_identifier;
    private int m_lineNumber;
    private int m_columnNumber;

    public ExcessReturnStatement(String functionIdentifier, ASTNode node) {
        super("Error: return statement in Void function \"" + functionIdentifier + "\" at line " +
                node.getLineNumber() + ", column " + node.getColumnNumber());
        m_identifier = node.getString();
        m_lineNumber = node.getLineNumber();
        m_columnNumber = node.getColumnNumber();
    }

    public String getIdentifier() {
        return m_identifier;
    }

    public int getLineNumber() {
        return m_lineNumber;
    }

    public int getColumnNumber() {
        return m_columnNumber;
    }
}
