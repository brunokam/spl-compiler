package org.spl.common.structure;

import org.spl.common.ASTNode;
import org.spl.common.type.EmptyListType;
import org.spl.common.type.Type;

public class EmptyList extends Expression {

    public EmptyList(ASTNode valueNode) {
        super();
    }

    public Type getType() {
        return new EmptyListType();
    }

    @Override
    public Integer getSize() {
        return 2;
    }

    @Override
    public void generate(Context context) {
        context.addInstruction(new String[]{LOAD_CONSTANT, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "5"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});

        // Stores the value on the heap
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "initialise_variable"});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "0"});
    }

    @Override
    public String toString() {
        return "EMPTY_ARRAY";
    }
}
