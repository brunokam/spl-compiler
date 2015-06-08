package org.spl.common.structure;

import org.spl.common.Nonterminal;

public class WhileStatement extends StructureObject {

    private Expression m_conditionalExpression;
    private Scope m_scope;

    public WhileStatement(Expression conditionalExpression, Scope parentScope) {
        super();
        m_conditionalExpression = conditionalExpression;
        m_scope = new Scope(parentScope, Nonterminal.WhileStmt);
    }

    public Scope getScope() {
        return m_scope;
    }

    @Override
    public void generateCode(Context context) {
        Integer statementCounter = context.getStatementCounter();

        context.addInstruction(new String[]{"while" + statementCounter + ":", NO_OPERATION});
        m_conditionalExpression.generateCode(context);
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "end_while" + statementCounter});

        for (StructureObject structureObject : m_scope.getStructures()) {
            structureObject.generateCode(context);
        }

        context.addInstruction(new String[]{BRANCH, "while" + statementCounter});
        context.addInstruction(new String[]{"end_while" + statementCounter + ":", NO_OPERATION});
    }

    @Override
    public String toString() {
        return "WHILE";
    }
}
