package org.spl.common.structure;

import org.spl.common.type.Type;

public class Variable extends Expression {

    private VariableDeclaration m_declaration;

    public Variable(VariableDeclaration declaration) {
        super();
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
    public void generateCode(Context context) {
        Type type = m_declaration.getType();
        String variableSize = type.getBodySize().toString();
        String addressPosition = context.getAddressPosition(m_declaration).toString();
        String addressSize = type.getAddressSize().toString();

        if (context.containsLocal(m_declaration) || context.containsArgument(m_declaration)) {
            context.addInstruction(new String[]{LOAD_MULTIPLE_LOCAL, addressPosition, addressSize});
        } else if (context.containsGlobal(m_declaration)) {
            context.addInstruction(new String[]{LOAD_REGISTER, "R5"});
            context.addInstruction(new String[]{LOAD_MULTIPLE_VIA_ADDRESS, addressPosition, addressSize});
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public String toString() {
        return "VARIABLE " + m_declaration.getIdentifier();
    }
}
