package org.spl.common.structure;

import org.spl.common.type.Type;

public class ReturnStatement extends StructureObject {

    private Expression m_expression;
    private Scope m_scope;

    public ReturnStatement(Expression expression, Scope scope) {
        super();
        m_expression = expression;
        m_scope = scope;
    }

    @Override
    public Type getType() {
        return m_expression.getType();
    }

    @Override
    public void generateCode(Context context) {
        Integer expressionSize = m_expression.getSize();
        Type expressionType = getType();

//        Integer adjustmentLength = -context.getLocalEnvironmentLength() - 1;
//        context.addInstruction(new String[]{ADJUST, adjustmentLength.toString()});
//        context.addInstruction(new String[]{RETURN});


        Integer size = m_expression.getSize();

        context.addInstruction(new String[]{LOAD_CONSTANT, size.toString()});
        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
        size += 2;

        m_expression.generateCodeForValue(context);

        if (expressionType.isBasicType() || expressionType.isTupleType()) {
            context.addInstruction(new String[]{STORE_MULTIPLE_ON_HEAP, size.toString()});
            context.addInstruction(new String[]{STORE_REGISTER, "RR"});
            context.addInstruction(new String[]{BRANCH, "ret" + m_scope.getId().toString()});
        } else if (expressionType.isListType()) {
            // TODO
        }
    }

    @Override
    public String toString() {
        return "RETURN";
    }
}
