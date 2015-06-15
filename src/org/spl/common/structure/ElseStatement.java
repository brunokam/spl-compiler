package org.spl.common.structure;

import org.spl.common.Nonterminal;

public class ElseStatement extends StructureObject {

    private Scope m_scope;

    public ElseStatement(Scope parentScope) {
        super();
        m_scope = new Scope(parentScope, Nonterminal.ElseStmt);
    }

    public Scope getScope() {
        return m_scope;
    }

    @Override
    public void generate(Context context) {
        for (StructureObject structureObject : m_scope.getStructures()) {
            structureObject.generate(context);
        }
    }

    @Override
    public String toString() {
        return "ELSE";
    }
}
