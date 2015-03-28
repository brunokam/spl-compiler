package org.spl.binder.exception;

import org.spl.common.ASTNode;

public class IncorrectTypeDeclarationException extends Exception {

    private int m_lineNumber;
    private int m_columnNumber;

    public IncorrectTypeDeclarationException(ASTNode node) {
        super("Error: incorrect type declaration at line " +
                node.getLineNumber() + ", column " + node.getColumnNumber());
        m_lineNumber = node.getLineNumber();
        m_columnNumber = node.getColumnNumber();
    }

    public int getLineNumber() {
        return m_lineNumber;
    }

    public int getColumnNumber() {
        return m_columnNumber;
    }
}
