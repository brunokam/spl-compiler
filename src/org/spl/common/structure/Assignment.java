package org.spl.common.structure;

import org.spl.common.type.BasicType;
import org.spl.common.type.Type;

public class Assignment extends StructureObject {

    private VariableDeclaration m_variableDeclaration;
    private Expression m_expression;

    public Assignment(VariableDeclaration variableDeclaration, Expression expression) {
        super();
        m_variableDeclaration = variableDeclaration;
        m_expression = expression;
    }

    public VariableDeclaration getVariable() {
        return m_variableDeclaration;
    }

    public Expression getExpression() {
        return m_expression;
    }

    @Override
    public void generate(Context context) {
        String addressPosition = context.getAddressPosition(m_variableDeclaration).toString();
        Type type = m_variableDeclaration.getType();
        String addressSize = type.getAddressSize().toString();
        String bodySize = type.getBodySize().toString();

        if (m_expression.isReference()) {
            m_expression.generate(context);

            if (!m_variableDeclaration.equals(((VariableUse) m_expression).getDeclaration())) {
                if (context.containsLocal(m_variableDeclaration) || context.containsArgument(m_variableDeclaration)) {
                    context.addInstruction(new String[]{LOAD_LOCAL, addressPosition});
                } else if (context.containsGlobal(m_variableDeclaration)) {
                    context.addInstruction(new String[]{LOAD_REGISTER, "R5"});
                    context.addInstruction(new String[]{LOAD_VIA_ADDRESS, addressPosition});
                } else {
                    throw new RuntimeException();
                }

                context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "delete_reference"});
                context.addInstruction(new String[]{ADJUST, "-1"});
            }

            context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "add_reference"});
        } else {
            if (context.containsLocal(m_variableDeclaration) || context.containsArgument(m_variableDeclaration)) {
                context.addInstruction(new String[]{LOAD_LOCAL, addressPosition});
            } else if (context.containsGlobal(m_variableDeclaration)) {
                context.addInstruction(new String[]{LOAD_REGISTER, "R5"});
                context.addInstruction(new String[]{LOAD_VIA_ADDRESS, addressPosition});
            } else {
                throw new RuntimeException();
            }

            context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "delete_reference"});
            context.addInstruction(new String[]{ADJUST, "-1"});

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

            m_expression.generate(context);

            if (m_expression.getSize() == 1) {
                // Adds empty space in the block
                context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
            }

            // Stores the value on the heap
            context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "initialise_variable"});
        }

        if (context.containsLocal(m_variableDeclaration) || context.containsArgument(m_variableDeclaration)) {
            context.addInstruction(new String[]{STORE_LOCAL, addressPosition});
        } else if (context.containsGlobal(m_variableDeclaration)) {
            context.addInstruction(new String[]{LOAD_REGISTER, "R5"});
            context.addInstruction(new String[]{STORE_VIA_ADDRESS, addressPosition});
        }
    }

    @Override
    public String toString() {
        return "ASSIGNMENT " + m_variableDeclaration.getIdentifier();
    }
}
