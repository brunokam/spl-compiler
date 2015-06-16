package org.spl.common.structure;

import org.spl.common.type.BasicType;
import org.spl.common.type.Type;

public class VariableDeclaration extends StructureObject {

    private Assignment m_assignment;
    private boolean m_isGlobal;

    public VariableDeclaration(Type type, String identifier) {
        super(type, identifier);
        m_isGlobal = false;
    }

    public VariableDeclaration(Type type, String identifier, boolean isGlobal) {
        super(type, identifier);
        m_isGlobal = isGlobal;
    }

    public Assignment getDeclarationAssignment() {
        return m_assignment;
    }

    public void setDeclarationAssignment(Assignment assignment) {
        m_assignment = assignment;
    }

    public boolean isGlobal() {
        return m_isGlobal;
    }

    public Integer getSize() {
        return m_type.getBodySize();
    }

    @Override
    public void generate(Context context) {
        Expression expression = m_assignment.getExpression();
        Type type = expression.getType();
        Integer size = expression.getSize();

        if (expression.isReference()) {
            expression.generate(context);

            context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "add_reference"});
            context.addInstruction(new String[]{ADJUST, "-1"});
        } else {
            context.addInstruction(new String[]{LOAD_CONSTANT, "1"});

            if (type.isBasicType()) {
                if (type.unify(new BasicType("Int"))) {
                    context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
                } else if (type.unify(new BasicType("Bool"))) {
                    context.addInstruction(new String[]{LOAD_CONSTANT, "1"});
                } else if (type.unify(new BasicType("Char"))) {
                    context.addInstruction(new String[]{LOAD_CONSTANT, "2"});
                }
            } else if (type.isTupleType()) {
                context.addInstruction(new String[]{LOAD_CONSTANT, "3"});
            } else if (type.isListType()) {
                context.addInstruction(new String[]{LOAD_CONSTANT, "4"});
            }

            size += 2;
        }

        expression.generate(context);

        if (!expression.isReference()) {
            if (size == 3) {
                // Adds empty space in the block
                context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
            }

            // Stores the value on the heap
            context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "initialise_variable"});
        }

        context.addInstruction(new String[]{NO_OPERATION});
        context.addInstruction(new String[]{ANNOTE, "SP", "0", "0", "red", "\"var " + m_identifier + "\""});

        if (m_isGlobal) {
            context.addGlobal(this);
        } else {
            context.addLocal(this);
        }
    }

    @Override
    public String toString() {
        return "VARIABLE_DECL " + m_identifier;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof VariableDeclaration)) {
            return false;
        }

        VariableDeclaration variableDeclaration = (VariableDeclaration) object;
        if (!variableDeclaration.getIdentifier().equals(m_identifier) || !variableDeclaration.getType().unify(m_type)) {
            return false;
        }

        return true;
    }
}
