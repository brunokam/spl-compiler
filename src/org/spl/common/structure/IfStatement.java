package org.spl.common.structure;

import org.spl.common.Nonterminal;

public class IfStatement extends StructureObject {

    private Expression m_conditionalExpression;
    private Scope m_scope;
    private ElseStatement m_elseStatement;

    public IfStatement(Expression conditionalExpression, Scope parentScope) {
        super();
        m_conditionalExpression = conditionalExpression;
        m_scope = new Scope(parentScope, Nonterminal.IfStmt);
        m_elseStatement = null;
    }

    public Scope getScope() {
        return m_scope;
    }

    public void setElseStatement(ElseStatement elseStatement) {
        m_elseStatement = elseStatement;
    }

    boolean hasElseStatement() {
        return m_elseStatement != null;
    }

    @Override
    public void generateCode(Context context) {
        Integer statementCounter = context.getStatementCounter();

        m_conditionalExpression.generateCode(context);
        context.addInstruction(new String[]{BRANCH_ON_FALSE, hasElseStatement() ? "else" + statementCounter : "end_if" + statementCounter});

        for (StructureObject structureObject : m_scope.getStructures()) {
            structureObject.generateCode(context);
        }

        context.addInstruction(new String[]{BRANCH, "end_if" + statementCounter});

        if (hasElseStatement()) {
            context.addInstruction(new String[]{"else" + statementCounter + ":", NO_OPERATION});
            m_elseStatement.generateCode(context);
        }

        context.addInstruction(new String[]{"end_if" + statementCounter + ":", NO_OPERATION});
    }

    @Override
    public String toString() {
        return "IF";
    }
}
