package org.spl.common.structure;

import org.spl.common.type.BasicType;
import org.spl.common.type.Type;

public class ReturnStatement extends StructureObject {

    private Expression m_expression;
    private Scope m_scope;
    private FunctionDeclaration m_functionDeclaration;

    public ReturnStatement(Expression expression, Scope scope, FunctionDeclaration functionDeclaration) {
        super();
        m_expression = expression;
        m_scope = scope;
        m_functionDeclaration = functionDeclaration;
    }

    @Override
    public Type getType() {
        return m_expression.getType();
    }

    @Override
    public void generate(Context context) {
        Type type = getType();
        Integer size = m_expression.getSize();

        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});

        if (m_expression.isReference()) {
            m_expression.generate(context);

            context.addInstruction(new String[]{LOAD_MULTIPLE_FROM_HEAP, "-3", "3"});
        } else {
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

            m_expression.generateValue(context);

            size += 2;
        }

        if (size == 3) {
            // Adds empty space in the block
            context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
        }

        // Stores the value on the heap
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "initialise_variable"});

        context.addInstruction(new String[]{STORE_REGISTER, "RR"});
        context.addInstruction(new String[]{BRANCH, "return_from_" + m_functionDeclaration.getIdentifier()});
    }

    @Override
    public String toString() {
        return "RETURN";
    }
}
