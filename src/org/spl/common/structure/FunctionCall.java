package org.spl.common.structure;

import org.spl.common.ASTNode;
import org.spl.common.type.BasicType;
import org.spl.common.type.Type;

import java.util.ArrayList;

public class FunctionCall extends Expression {

    private FunctionDeclaration m_functionDeclaration;
    private ArrayList<Expression> m_arguments;

    public FunctionCall(FunctionDeclaration functionDeclaration, ASTNode node) {
        super(node);
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

    private void generatePrintCall(Context context) {
        Expression argument = m_arguments.get(0);
        Type type = argument.getType();

        argument.generate(context);

        if (!argument.isReference()) {
            if (type.unify(new BasicType("Int"))) {
                context.addInstruction(new String[]{TRAP, "0"});
            } else if (type.unify(new BasicType("Bool"))) {
                context.addInstruction(new String[]{TRAP, "0"});
            } else if (type.unify(new BasicType("Char"))) {
                context.addInstruction(new String[]{TRAP, "1"});
            }
        } else {
            context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "print"});
        }
    }

    private void generateNewCall(Context context) {
        Expression argument = m_arguments.get(0);

        argument.generateValue(context);
    }

    @Override
    public void generate(Context context) {
        String functionIdentifier = m_functionDeclaration.getIdentifier();

        if (functionIdentifier.equals("print")) {
            generatePrintCall(context);
            return;
        } else if (functionIdentifier.equals("new")) {
            generateNewCall(context);
            return;
        }

        Type returnType = m_functionDeclaration.getType();

        for (Expression argument : m_arguments) {
            Type type = argument.getType();
            Integer size = argument.getSize();

            if (!argument.isReference()) {
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

            argument.generate(context);

            if (!argument.isReference()) {
                if (size == 3) {
                    // Adds empty space in the block
                    context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
                }

                // Stores the block
                context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "initialise_variable"});
            }

            context.addInstruction(new String[]{ANNOTE, "SP", "0", "0", "red", "\"func arg\""});
        }

        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "f_" + m_functionDeclaration.getIdentifier()});

        Integer adjustmentLength = -m_arguments.size();

        if (adjustmentLength < 0) {
            context.addInstruction(new String[]{ADJUST, adjustmentLength.toString()});
        }

        if (!m_functionDeclaration.isVoid()) {
            context.addInstruction(new String[]{LOAD_REGISTER, "RR"});

            if (returnType.isBasicType()) {
                context.addInstruction(new String[]{LOAD_FROM_HEAP, "2"});
            } else if (returnType.isTupleType()) {
                context.addInstruction(new String[]{LOAD_MULTIPLE_FROM_HEAP, "-3", "2"});
            } else if (returnType.isListType()) {
                context.addInstruction(new String[]{LOAD_MULTIPLE_FROM_HEAP, "-3", "2"});
            } else if (returnType.isPolymorphicType()) {
                context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "get_value"});
            }
        }
    }

    @Override
    public String toString() {
        return "FUNCTION_CALL " + m_functionDeclaration.getIdentifier();
    }
}
