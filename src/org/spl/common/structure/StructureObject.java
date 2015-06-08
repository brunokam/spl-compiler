package org.spl.common.structure;

import org.spl.common.ASTNode;
import org.spl.common.type.Type;

public abstract class StructureObject {

    protected final static String NO_OPERATION = "nop";
    protected final static String ANNOTE = "annote";
    protected final static String LOAD_VIA_ADDRESS = "lda";
    protected final static String LOAD_MULTIPLE_VIA_ADDRESS = "ldma";
    protected final static String LOAD_CONSTANT = "ldc";
    protected final static String LOAD_LOCAL = "ldl";
    protected final static String LOAD_MULTIPLE_LOCAL = "ldml";
    protected final static String LOAD_FROM_STACK = "lds";
    protected final static String LOAD_MULTIPLE_FROM_STACK = "ldms";
    protected final static String LOAD_STACK_ADDRESS = "ldsa";
    protected final static String LOAD_REGISTER = "ldr";
    protected final static String LOAD_FROM_HEAP = "ldh";
    protected final static String LOAD_MULTIPLE_FROM_HEAP = "ldmh";
    protected final static String BRANCH = "bra";
    protected final static String BRANCH_ON_TRUE = "brt";
    protected final static String BRANCH_ON_FALSE = "brf";
    protected final static String BRANCH_TO_SUBROUTINE = "bsr";
    protected final static String STORE_VIA_ADDRESS = "sta";
    protected final static String STORE_MULTIPLE_VIA_ADDRESS = "stma";
    protected final static String STORE_LOCAL = "stl";
    protected final static String STORE_MULTIPLE_LOCAL = "stml";
    protected final static String STORE_ON_STACK = "sts";
    protected final static String STORE_MULTIPLE_ON_STACK = "stms";
    protected final static String STORE_REGISTER = "str";
    protected final static String STORE_ON_HEAP = "sth";
    protected final static String STORE_MULTIPLE_ON_HEAP = "stmh";
    protected final static String RETURN = "ret";
    protected final static String ADJUST = "ajs";
    protected final static String TRAP = "trap";
    protected final static String HALT = "halt";
    protected final static String ADD = "add";
    protected final static String SUBTRACT = "sub";
    protected final static String SWAP = "swp";
    protected final static String EQ = "eq";
    protected final static String GT = "gt";

    protected Type m_type;
    protected String m_identifier;
    protected ASTNode m_node;

    public StructureObject() {
        m_type = null;
        m_identifier = null;
        m_node = null;
    }

    public StructureObject(Type type, String identifier) {
        m_type = type;
        m_identifier = identifier;
    }

    public Type getType() {
        return m_type;
    }

    public String getIdentifier() {
        return m_identifier;
    }

    public ASTNode getNode() {
        return m_node;
    }

    public void setNode(ASTNode object) {
        m_node = object;
    }

    public abstract void generateCode(Context context);

    protected void saveHeapPointer(Context context) {
        context.addInstruction(new String[]{LOAD_REGISTER, "HP"});
        context.addInstruction(new String[]{STORE_REGISTER, "R6"});
    }

    protected void restoreHeapPointer(Context context) {
        context.addInstruction(new String[]{LOAD_REGISTER, "R6"});
        context.addInstruction(new String[]{STORE_REGISTER, "HP"});
    }

    protected void addReference(Context context, VariableDeclaration declaration) {
        String addressPosition = context.getAddressPosition(declaration).toString();
        String offset = declaration.getType().getBodySize().toString();

        saveHeapPointer(context);

        if (declaration.isGlobal()) {
            context.addInstruction(new String[]{LOAD_REGISTER, "R5"});
            context.addInstruction(new String[]{LOAD_VIA_ADDRESS, addressPosition});
        } else {
            context.addInstruction(new String[]{LOAD_LOCAL, addressPosition});
        }

        context.addInstruction(new String[]{LOAD_FROM_HEAP, "-" + offset});
        context.addInstruction(new String[]{LOAD_CONSTANT, "1"});
        context.addInstruction(new String[]{ADD});

        if (declaration.isGlobal()) {
            context.addInstruction(new String[]{LOAD_REGISTER, "R5"});
            context.addInstruction(new String[]{LOAD_VIA_ADDRESS, addressPosition});
        } else {
            context.addInstruction(new String[]{LOAD_LOCAL, addressPosition});
        }

        context.addInstruction(new String[]{LOAD_CONSTANT, offset});
        context.addInstruction(new String[]{SUBTRACT});
        context.addInstruction(new String[]{STORE_REGISTER, "HP"});
        context.addInstruction(new String[]{STORE_ON_HEAP});

        restoreHeapPointer(context);

        context.addInstruction(new String[]{ADJUST, "-1"});
    }

    protected void deleteReference(Context context, VariableDeclaration declaration) {
        String addressPosition = context.getAddressPosition(declaration).toString();
        String offset = declaration.getType().getBodySize().toString();

        saveHeapPointer(context);

        if (declaration.isGlobal()) {
            context.addInstruction(new String[]{LOAD_REGISTER, "R5"});
            context.addInstruction(new String[]{LOAD_VIA_ADDRESS, addressPosition});
        } else {
            context.addInstruction(new String[]{LOAD_LOCAL, addressPosition});
        }

        context.addInstruction(new String[]{LOAD_FROM_HEAP, "-" + offset});
        context.addInstruction(new String[]{LOAD_CONSTANT, "1"});
        context.addInstruction(new String[]{SUBTRACT});

        if (declaration.isGlobal()) {
            context.addInstruction(new String[]{LOAD_REGISTER, "R5"});
            context.addInstruction(new String[]{LOAD_VIA_ADDRESS, addressPosition});
        } else {
            context.addInstruction(new String[]{LOAD_LOCAL, addressPosition});
        }

        context.addInstruction(new String[]{LOAD_CONSTANT, offset});
        context.addInstruction(new String[]{SUBTRACT});
        context.addInstruction(new String[]{STORE_REGISTER, "HP"});
        context.addInstruction(new String[]{STORE_ON_HEAP});

        restoreHeapPointer(context);

        context.addInstruction(new String[]{ADJUST, "-1"});
    }

    public void generateListCollector(Context context, VariableDeclaration declaration) {
        String identifier = declaration.getIdentifier();

        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
        context.addInstruction(new String[]{STORE_REGISTER, "R7"});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "0"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "-1"});
        context.addInstruction(new String[]{"stack_loop_" + identifier + ":", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_REGISTER, "R7"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "1"});
        context.addInstruction(new String[]{ADD});
        context.addInstruction(new String[]{STORE_REGISTER, "R7"});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "0"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "0"});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "0"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "stack_loop_" + identifier});
        context.addInstruction(new String[]{"end_stack_loop_" + identifier + ":", NO_OPERATION});
        context.addInstruction(new String[]{ADJUST, "-1"});
        context.addInstruction(new String[]{STORE_ON_HEAP});
        context.addInstruction(new String[]{STORE_ON_HEAP});
        context.addInstruction(new String[]{"heap_loop_" + identifier + ":", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_REGISTER, "R7"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
        context.addInstruction(new String[]{GT});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "heap_loop_" + identifier});
        context.addInstruction(new String[]{LOAD_REGISTER, "R7"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "1"});
        context.addInstruction(new String[]{SUBTRACT});
        context.addInstruction(new String[]{STORE_REGISTER, "R7"});
        context.addInstruction(new String[]{"end_heap_loop_" + identifier + ":", NO_OPERATION});
    }

    public void generateGarbageCollectorFunction(Context context) {
        context.addInstruction(new String[]{"garbage_collector:", NO_OPERATION});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_TRUE, "garbage_collector_return"});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});

        context.addInstruction(new String[]{TRAP, "0"});

        context.addInstruction(new String[]{STORE_ON_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH, "garbage_collector"});

        context.addInstruction(new String[]{"garbage_collector_return:", NO_OPERATION});
        context.addInstruction(new String[]{RETURN});
    }

    @Override
    public String toString() {
        return "{type=\"" + m_type +
                "\", identifier=\"" + m_identifier + "\"}";
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other == this) {
            return true;
        }

        if (!(other instanceof StructureObject)) {
            return false;
        }

        StructureObject structureObject = (StructureObject) other;
        if (m_identifier == null || structureObject.getIdentifier() == null) {
//            System.out.println(this.getClass().toString() + " " + bindingObject.getIdentifier());
            return false;
        } else {
            return m_identifier.equals(structureObject.getIdentifier());
        }
    }
}
