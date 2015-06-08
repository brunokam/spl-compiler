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
    public void generateCode(Context context) {
        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
        context.addInstruction(new String[]{STORE_ON_HEAP});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "0"});
    }

    @Override
    public String toString() {
        return "EMPTY_ARRAY";
    }
}
