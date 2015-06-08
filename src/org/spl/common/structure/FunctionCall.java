package org.spl.common.structure;

import org.spl.common.type.Type;

import java.util.ArrayList;

public class FunctionCall extends Expression {

    private FunctionDeclaration m_functionDeclaration;
    private ArrayList<Expression> m_arguments;

    public FunctionCall(FunctionDeclaration functionDeclaration) {
        super();
        m_functionDeclaration = functionDeclaration;
        m_arguments = new ArrayList<Expression>();
    }

    @Override
    public boolean isReference() {
        return false;
    }

    public FunctionDeclaration getFunctionDeclaration() {
        return m_functionDeclaration;
    }

    public ArrayList<Expression> getArguments() {
        return m_arguments;
    }

    public void addArgument(Expression expression) {
        m_arguments.add(0, expression);
    }

    @Override
    public Integer getSize() {
        if (m_functionDeclaration.getIdentifier().equals("new")) {
            return m_arguments.get(0).getSize();
        } else {
            return m_functionDeclaration.getType().getBodySize();
        }
    }

    @Override
    public void generateCode(Context context) {
        // TODO: Refactor
        if (m_functionDeclaration.getIdentifier().equals("print")) {
            Expression argument = m_arguments.get(0);

            argument.generateCodeForValue(context);
            argument.generatePrintCode(context);

            return;
        } else if (m_functionDeclaration.getIdentifier().equals("new")) {
            Expression argument = m_arguments.get(0);

            argument.generateCodeForValue(context);

            return;
        }

        Type returnType = m_functionDeclaration.getType();

        for (Expression argument : m_arguments) {
            Type argumentType = argument.getType();
            Integer size = argument.getSize();

            if (argument.isReference()) {
                Variable variable = (Variable) argument;
                VariableDeclaration declaration = variable.getDeclaration();
                addReference(context, declaration);
            } else {
                context.addInstruction(new String[]{LOAD_CONSTANT, size.toString()});
                context.addInstruction(new String[]{LOAD_CONSTANT, "1"});
                size += 2;
            }

            argument.generateCode(context);

            if ((argumentType.isBasicType() || argumentType.isTupleType()) && !argument.isReference()) {
                context.addInstruction(new String[]{STORE_MULTIPLE_ON_HEAP, size.toString()});
            }

            context.addInstruction(new String[]{ANNOTE, "SP", "0", "0", "red", "\"func arg\""});
        }

        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, m_functionDeclaration.getIdentifier()});

        Integer adjustmentLength = 0;
        for (Expression argument : m_arguments) {
            adjustmentLength -= argument.getSize();
        }

//        context.addInstruction(new String[]{LOAD_LOCAL, "0"});
//        context.addInstruction(new String[]{STORE_REGISTER, "MP"});
        if (returnType.isBasicType() || returnType.isTupleType()) {
            context.addInstruction(new String[]{ADJUST, adjustmentLength.toString()});
            context.addInstruction(new String[]{LOAD_REGISTER, "RR"});
            context.addInstruction(new String[]{LOAD_MULTIPLE_FROM_HEAP, "0", returnType.getBodySize().toString()});
//            context.addInstruction(new String[]{STORE_MULTIPLE_ON_HEAP, size.toString()});
//            context.addInstruction(new String[]{STORE_REGISTER, "RR"});
//            context.addInstruction(new String[]{STORE_REGISTER, "MP"});
//            context.addInstruction(new String[]{RETURN});
        } else if (returnType.isListType()) {
            // TODO
        }
    }

    @Override
    public String toString() {
        return "FUNCTION_CALL " + m_functionDeclaration.getIdentifier();
    }
}
