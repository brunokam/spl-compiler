package org.spl.typechecker.exception;

import org.spl.common.ASTNode;

public class IncorrectTypeException extends Exception {

    private String m_identifier;
    private int m_lineNumber;
    private int m_columnNumber;

    public IncorrectTypeException(ASTNode node) {
        super("Error: incorrect type applied to \"" + node.getString() + "\" at line " +
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
