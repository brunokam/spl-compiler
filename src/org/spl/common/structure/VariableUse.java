package org.spl.common.structure;

import org.spl.common.type.Type;

import java.util.ArrayList;

public class VariableUse extends Expression {

    private VariableDeclaration m_declaration;

    public VariableUse(VariableDeclaration declaration) {
        super();
        m_declaration = declaration;
    }

    public VariableUse(Expression expression, VariableDeclaration declaration) {
        super();
        m_node = expression.m_node;
        m_token = expression.m_token;
        m_expressions = new ArrayList<Expression>(expression.m_expressions);
        m_forcedAdjustment = expression.m_forcedAdjustment;
        m_declaration = declaration;
    }

    @Override
    public boolean isReference() {
        return true;
    }

    @Override
    public Type getType() {
        return m_declaration.getType();
    }

    @Override
    public Integer getSize() {
        return m_declaration.getType().getBodySize();
    }

    public VariableDeclaration getDeclaration() {
        return m_declaration;
    }

    @Override
    public void generate(Context context) {
        String addressPosition = context.getAddressPosition(m_declaration).toString();

        if (context.containsLocal(m_declaration) || context.containsArgument(m_declaration)) {
            context.addInstruction(new String[]{LOAD_LOCAL, addressPosition});
        } else if (context.containsGlobal(m_declaration)) {
            context.addInstruction(new String[]{LOAD_REGISTER, "R5"});
            context.addInstruction(new String[]{LOAD_VIA_ADDRESS, addressPosition});
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public String toString() {
        return "VARIABLE_USE " + m_declaration.getIdentifier();
    }
}
