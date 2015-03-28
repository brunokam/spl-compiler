package org.spl.binder.exception;

import org.spl.common.ASTNode;

public class UndeclaredUseException extends Exception {

    private String m_identifier;
    private int m_lineNumber;
    private int m_columnNumber;

    public UndeclaredUseException(String identifier, ASTNode node) {
        super("Error: use of undeclared \"" + identifier + "\" at line " +
                node.getLineNumber() + ", column " + node.getColumnNumber());
        m_identifier = identifier;
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
