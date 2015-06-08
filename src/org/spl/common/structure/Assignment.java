package org.spl.common.structure;

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
    public void generateCode(Context context) {
        String addressPosition = context.getAddressPosition(m_variableDeclaration).toString();
        Type type = m_variableDeclaration.getType();
        String addressSize = type.getAddressSize().toString();
        String bodySize = type.getBodySize().toString();

        m_expression.generateCode(context);

        if (context.containsLocal(m_variableDeclaration) || context.containsArgument(m_variableDeclaration)) {
            // If the expression is reference (=> the expression is a variable)
            if (m_expression.isReference()) {
                Variable referenceVariable = (Variable) m_expression;
                VariableDeclaration referenceDeclaration = referenceVariable.getDeclaration();

//                deleteReference(context, m_variableDeclaration);
//                addReference(context, referenceDeclaration);

                context.addInstruction(new String[]{STORE_MULTIPLE_LOCAL, addressPosition, addressSize});
            } else {
                saveHeapPointer(context);

                if (type.isBasicType()) {
                    context.addInstruction(new String[]{LOAD_LOCAL, addressPosition});
                    context.addInstruction(new String[]{STORE_REGISTER, "HP"});
                    context.addInstruction(new String[]{STORE_ON_HEAP});
                } else if (type.isTupleType()) {
                    context.addInstruction(new String[]{LOAD_LOCAL, addressPosition});
                    context.addInstruction(new String[]{STORE_REGISTER, "HP"});
                    context.addInstruction(new String[]{STORE_MULTIPLE_ON_HEAP, bodySize});
                } else if (type.isListType()) {
                    // TODO
                    context.addInstruction(new String[]{STORE_MULTIPLE_LOCAL, addressPosition, addressSize});
                }

                restoreHeapPointer(context);
                context.addInstruction(new String[]{ADJUST, "-1"});
            }
        } else if (context.containsGlobal(m_variableDeclaration)) {
            context.addInstruction(new String[]{LOAD_REGISTER, "R5"});

            // If the expression is reference (=> the expression is a variable)
            if (m_expression.isReference()) {
                Variable referenceVariable = (Variable) m_expression;
                VariableDeclaration referenceDeclaration = referenceVariable.getDeclaration();

//                deleteReference(context, m_variableDeclaration);
//                addReference(context, referenceDeclaration);

                context.addInstruction(new String[]{STORE_MULTIPLE_VIA_ADDRESS, addressPosition, addressSize});
            } else {
                saveHeapPointer(context);

                if (type.isBasicType()) {
                    context.addInstruction(new String[]{LOAD_VIA_ADDRESS, addressPosition});
                    context.addInstruction(new String[]{STORE_REGISTER, "HP"});
                    context.addInstruction(new String[]{STORE_ON_HEAP});
                } else if (type.isTupleType()) {
                    context.addInstruction(new String[]{LOAD_VIA_ADDRESS, addressPosition});
                    context.addInstruction(new String[]{STORE_REGISTER, "HP"});
                    context.addInstruction(new String[]{STORE_MULTIPLE_ON_HEAP, bodySize});
                } else if (type.isListType()) {
                    // TODO
                }

                restoreHeapPointer(context);
            }
        }
    }

    @Override
    public String toString() {
        return "ASSIGNMENT " + m_variableDeclaration.getIdentifier();
    }
}
