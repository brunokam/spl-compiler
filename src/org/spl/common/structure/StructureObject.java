package org.spl.common.structure;

import org.spl.common.ASTNode;
import org.spl.common.type.Type;

public abstract class StructureObject {

    private final static Integer GARBAGE_COLLECTOR_BOUNDARY = 2096;

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
    protected final static String NE = "ne";
    protected final static String GT = "gt";
    protected final static String GE = "ge";
    protected final static String LT = "lt";
    protected final static String LE = "le";

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

    public abstract void generate(Context context);

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

        saveHeapPointer(context);

        if (declaration.isGlobal()) {
            context.addInstruction(new String[]{LOAD_REGISTER, "R5"});
            context.addInstruction(new String[]{LOAD_VIA_ADDRESS, addressPosition});
        } else {
            context.addInstruction(new String[]{LOAD_LOCAL, addressPosition});
        }

        context.addInstruction(new String[]{LOAD_FROM_HEAP, "0"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "1"});
        context.addInstruction(new String[]{ADD});

        if (declaration.isGlobal()) {
            context.addInstruction(new String[]{LOAD_REGISTER, "R5"});
            context.addInstruction(new String[]{LOAD_VIA_ADDRESS, addressPosition});
        } else {
            context.addInstruction(new String[]{LOAD_LOCAL, addressPosition});
        }

        context.addInstruction(new String[]{STORE_REGISTER, "HP"});
        context.addInstruction(new String[]{STORE_ON_HEAP});

        restoreHeapPointer(context);

        context.addInstruction(new String[]{ADJUST, "-1"});
    }

    protected void deleteReference(Context context, VariableDeclaration declaration) {
        String addressPosition = context.getAddressPosition(declaration).toString();

        saveHeapPointer(context);

        if (declaration.isGlobal()) {
            context.addInstruction(new String[]{LOAD_REGISTER, "R5"});
            context.addInstruction(new String[]{LOAD_VIA_ADDRESS, addressPosition});
        } else {
            context.addInstruction(new String[]{LOAD_LOCAL, addressPosition});
        }

        context.addInstruction(new String[]{LOAD_FROM_HEAP, "0"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "1"});
        context.addInstruction(new String[]{SUBTRACT});

        if (declaration.isGlobal()) {
            context.addInstruction(new String[]{LOAD_REGISTER, "R5"});
            context.addInstruction(new String[]{LOAD_VIA_ADDRESS, addressPosition});
        } else {
            context.addInstruction(new String[]{LOAD_LOCAL, addressPosition});
        }

        context.addInstruction(new String[]{STORE_REGISTER, "HP"});
        context.addInstruction(new String[]{STORE_ON_HEAP});

        restoreHeapPointer(context);

        context.addInstruction(new String[]{ADJUST, "-1"});
    }

    public void generateGarbageCollector(Context context) {
        context.addInstruction(new String[]{"collect_garbage:", NO_OPERATION});

        saveHeapPointer(context);

        // Stores initial previous heap pointer reference
        context.addInstruction(new String[]{LOAD_REGISTER, "HP"});
        context.addInstruction(new String[]{STORE_REGISTER, "R7"});

        // Stores initial heap pointer
        context.addInstruction(new String[]{LOAD_CONSTANT, "2000"});
        context.addInstruction(new String[]{STORE_REGISTER, "HP"});

        // While HP < R6
        context.addInstruction(new String[]{"gc_while:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_REGISTER, "HP"});
        context.addInstruction(new String[]{LOAD_REGISTER, "R6"});
        context.addInstruction(new String[]{LT});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "gc_end_while"});

        // If reference counter == 0
        context.addInstruction(new String[]{LOAD_REGISTER, "HP"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "0"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
        context.addInstruction(new String[]{LE});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "gc_else"});

        context.addInstruction(new String[]{LOAD_REGISTER, "R7"});
        context.addInstruction(new String[]{STORE_ON_HEAP});
        context.addInstruction(new String[]{STORE_REGISTER, "R7"});

        // Moves HP to the next block
        context.addInstruction(new String[]{LOAD_REGISTER, "HP"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "3"});
        context.addInstruction(new String[]{ADD});
        context.addInstruction(new String[]{STORE_REGISTER, "HP"});

        context.addInstruction(new String[]{BRANCH, "gc_end_if"});

        // Else
        context.addInstruction(new String[]{"gc_else:", NO_OPERATION});

        // Moves HP to the next block
        context.addInstruction(new String[]{LOAD_REGISTER, "HP"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "4"});
        context.addInstruction(new String[]{ADD});
        context.addInstruction(new String[]{STORE_REGISTER, "HP"});

        // End if
        context.addInstruction(new String[]{"gc_end_if:", NO_OPERATION});

        // Loops
        context.addInstruction(new String[]{BRANCH, "gc_while"});

        // End while
        context.addInstruction(new String[]{"gc_end_while:", NO_OPERATION});

//        restoreHeapPointer(context);
        // Sets heap pointer to point on the free space
        context.addInstruction(new String[]{LOAD_REGISTER, "R7"});
        context.addInstruction(new String[]{STORE_REGISTER, "HP"});

        context.addInstruction(new String[]{RETURN});
    }

    public void generateValueGetter(Context context) {
        context.addInstruction(new String[]{"get_value:", NO_OPERATION});

        context.addInstruction(new String[]{STORE_REGISTER, "RR"});

        // If x is Int
        context.addInstruction(new String[]{LOAD_FROM_STACK, "0"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "0"}); // Int type identifier
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "vg_else_if_bool"});

        context.addInstruction(new String[]{LOAD_FROM_HEAP, "2"});

        context.addInstruction(new String[]{BRANCH, "vg_end_if"});

        // Else if x is Bool
        context.addInstruction(new String[]{"vg_else_if_bool:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "0"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "1"}); // Bool type identifier
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "vg_else_if_char"});

        context.addInstruction(new String[]{LOAD_FROM_HEAP, "2"});

        context.addInstruction(new String[]{BRANCH, "vg_end_if"});

        // Else if x is Char
        context.addInstruction(new String[]{"vg_else_if_char:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "0"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "2"}); // Char type identifier
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "vg_else_if_tuple"});

        context.addInstruction(new String[]{LOAD_FROM_HEAP, "2"});

        context.addInstruction(new String[]{BRANCH, "vg_end_if"});

        // Else if x is tuple
        context.addInstruction(new String[]{"vg_else_if_tuple:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "0"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "3"});
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "vg_else_if_list"});

        context.addInstruction(new String[]{LOAD_MULTIPLE_FROM_HEAP, "-3", "2"});

        context.addInstruction(new String[]{BRANCH, "vg_end_if"});

        // Else if x is list
        context.addInstruction(new String[]{"vg_else_if_list:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "0"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "4"});
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "vg_end_if"});

        context.addInstruction(new String[]{LOAD_MULTIPLE_FROM_HEAP, "-3", "2"});

        // End if
        context.addInstruction(new String[]{"vg_end_if:", NO_OPERATION});

        context.addInstruction(new String[]{LOAD_REGISTER, "RR"});
        context.addInstruction(new String[]{RETURN});
    }

    public void generateVariableInitialiser(Context context) {
        context.addInstruction(new String[]{"initialise_variable:", NO_OPERATION});

        context.addInstruction(new String[]{STORE_REGISTER, "RR"});

        // Executes garbage collector if needed
        context.addInstruction(new String[]{LOAD_REGISTER, "HP"});
        context.addInstruction(new String[]{LOAD_CONSTANT, GARBAGE_COLLECTOR_BOUNDARY.toString()});
        context.addInstruction(new String[]{GE});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "vm_if"});

        context.addInstruction(new String[]{LOAD_REGISTER, "HP"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "0"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "vm_if"});

        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "collect_garbage"});

        // If value under HP == 0
        context.addInstruction(new String[]{"vm_if:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_REGISTER, "HP"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "0"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_TRUE, "vm_else"});

        context.addInstruction(new String[]{LOAD_REGISTER, "HP"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "0"});
        context.addInstruction(new String[]{STORE_REGISTER, "R7"});

        // Stores the block
        context.addInstruction(new String[]{STORE_MULTIPLE_ON_HEAP, "4"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "3"});
        context.addInstruction(new String[]{SUBTRACT});

        // Moves HP to the new position
        context.addInstruction(new String[]{LOAD_REGISTER, "R7"});
        context.addInstruction(new String[]{STORE_REGISTER, "HP"});

        context.addInstruction(new String[]{BRANCH, "vm_end_if"});

        // Else
        context.addInstruction(new String[]{"vm_else:", NO_OPERATION});

        // Stores the block
        context.addInstruction(new String[]{STORE_MULTIPLE_ON_HEAP, "4"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "3"});
        context.addInstruction(new String[]{SUBTRACT});

        // End if
        context.addInstruction(new String[]{"vm_end_if:", NO_OPERATION});

        context.addInstruction(new String[]{LOAD_REGISTER, "RR"});
        context.addInstruction(new String[]{RETURN});
    }

    public void generateReferenceIncrementor(Context context) {
        context.addInstruction(new String[]{"add_reference:", NO_OPERATION});

        saveHeapPointer(context);

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "0"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "1"});
        context.addInstruction(new String[]{ADD});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-2"});
        context.addInstruction(new String[]{STORE_REGISTER, "HP"});
        context.addInstruction(new String[]{STORE_ON_HEAP});
        context.addInstruction(new String[]{ADJUST, "-1"});

        restoreHeapPointer(context);

        context.addInstruction(new String[]{RETURN});
    }

    public void generateReferenceDecrementor(Context context) {
        context.addInstruction(new String[]{"delete_reference:", NO_OPERATION});

        saveHeapPointer(context);

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "0"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "1"});
        context.addInstruction(new String[]{SUBTRACT});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-2"});
        context.addInstruction(new String[]{STORE_REGISTER, "HP"});
        context.addInstruction(new String[]{STORE_ON_HEAP});
        context.addInstruction(new String[]{ADJUST, "-1"});

        restoreHeapPointer(context);

        context.addInstruction(new String[]{RETURN});
    }

    public void generateVariableUtiliser(Context context) {
        context.addInstruction(new String[]{"utilise_variable:", NO_OPERATION});

        // If x is basic Int
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "0"}); // Int type identifier
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "vu_else_if_bool"});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "utilise_basic_variable"});

        context.addInstruction(new String[]{BRANCH, "vu_end_if"});

        // Else if x is Bool
        context.addInstruction(new String[]{"vu_else_if_bool:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "1"}); // Bool type identifier
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "vu_else_if_char"});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "utilise_basic_variable"});

        context.addInstruction(new String[]{BRANCH, "vu_end_if"});

        // Else if x is Char
        context.addInstruction(new String[]{"vu_else_if_char:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "2"}); // Char type identifier
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "vu_else_if_tuple"});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "utilise_basic_variable"});

        context.addInstruction(new String[]{BRANCH, "vu_end_if"});

        // Else if x is tuple
        context.addInstruction(new String[]{"vu_else_if_tuple:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "3"});
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "vu_else_if_list"});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "utilise_tuple"});

        context.addInstruction(new String[]{BRANCH, "vu_end_if"});

        // Else if x is list
        context.addInstruction(new String[]{"vu_else_if_list:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "4"});
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "vu_end_if"});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "utilise_list"});

        context.addInstruction(new String[]{BRANCH, "vu_end_if"});

        // Else if x is list element
        context.addInstruction(new String[]{"vu_else_if_list_elem:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "5"});
        context.addInstruction(new String[]{EQ});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "utilise_list_elem"});

        // End if
        context.addInstruction(new String[]{"vu_end_if:", NO_OPERATION});

        context.addInstruction(new String[]{ADJUST, "-1"});

        context.addInstruction(new String[]{RETURN});
    }

    public void generateBasicVariableUtiliser(Context context) {
        context.addInstruction(new String[]{"utilise_basic_variable:", NO_OPERATION});

        // Deletes reference
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "delete_reference"});
//        context.addInstruction(new String[]{STORE_REGISTER, "HP"});
//        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
//        context.addInstruction(new String[]{STORE_ON_HEAP});

        context.addInstruction(new String[]{ADJUST, "-1"});

        context.addInstruction(new String[]{RETURN});
    }

    public void generateTupleUtiliser(Context context) {
        context.addInstruction(new String[]{"utilise_tuple:", NO_OPERATION});

        // Deletes reference
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "delete_reference"});
//        context.addInstruction(new String[]{STORE_REGISTER, "HP"});
//        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
//        context.addInstruction(new String[]{STORE_ON_HEAP});

        context.addInstruction(new String[]{LOAD_MULTIPLE_FROM_HEAP, "-3", "2"});

        // Utilises the first element
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "utilise_variable"});
        context.addInstruction(new String[]{ADJUST, "-1"});

        // Utilises the second element
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "utilise_variable"});
        context.addInstruction(new String[]{ADJUST, "-1"});

        context.addInstruction(new String[]{RETURN});
    }

    public void generateListUtiliser(Context context) {
        context.addInstruction(new String[]{"utilise_list:", NO_OPERATION});

        // Deletes reference
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "delete_reference"});
//        context.addInstruction(new String[]{STORE_REGISTER, "HP"});
//        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
//        context.addInstruction(new String[]{STORE_ON_HEAP});

        context.addInstruction(new String[]{LOAD_FROM_HEAP, "2"});

        // Utilises the head element
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "utilise_list_elem"});
        context.addInstruction(new String[]{ADJUST, "-1"});

        context.addInstruction(new String[]{RETURN});
    }

    public void generateListElementUtiliser(Context context) {
        context.addInstruction(new String[]{"utilise_list_elem:", NO_OPERATION});

        // Deletes reference
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "delete_reference"});
//        context.addInstruction(new String[]{STORE_REGISTER, "HP"});
//        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
//        context.addInstruction(new String[]{STORE_ON_HEAP});

        // If x != 0 (end of a list)
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "2"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
        context.addInstruction(new String[]{NE});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "leu_end_if"});

        // Utilises a value of the element
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "2"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "utilise_variable"});
        context.addInstruction(new String[]{ADJUST, "-1"});

        // Utilises the next element
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "3"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "utilise_list_elem"});
        context.addInstruction(new String[]{ADJUST, "-1"});

        // End if
        context.addInstruction(new String[]{"leu_end_if:", NO_OPERATION});

        context.addInstruction(new String[]{RETURN});
    }

    public void generateVariableReferenceIncrementor(Context context) {
        context.addInstruction(new String[]{"inc_variable:", NO_OPERATION});

        // If x is basic Int
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "0"}); // Int type identifier
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "vri_else_if_bool"});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "inc_basic_variable"});

        context.addInstruction(new String[]{BRANCH, "vri_end_if"});

        // Else if x is Bool
        context.addInstruction(new String[]{"vri_else_if_bool:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "1"}); // Bool type identifier
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "vri_else_if_char"});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "inc_basic_variable"});

        context.addInstruction(new String[]{BRANCH, "vri_end_if"});

        // Else if x is Char
        context.addInstruction(new String[]{"vri_else_if_char:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "2"}); // Char type identifier
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "vri_else_if_tuple"});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "inc_basic_variable"});

        context.addInstruction(new String[]{BRANCH, "vri_end_if"});

        // Else if x is tuple
        context.addInstruction(new String[]{"vri_else_if_tuple:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "3"});
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "vri_else_if_list"});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "inc_tuple"});

        context.addInstruction(new String[]{BRANCH, "vri_end_if"});

        // Else if x is list
        context.addInstruction(new String[]{"vri_else_if_list:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "4"});
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "vri_end_if"});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "inc_list"});

        context.addInstruction(new String[]{BRANCH, "vri_end_if"});

        // Else if x is list element
        context.addInstruction(new String[]{"vri_else_if_list_elem:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "5"});
        context.addInstruction(new String[]{EQ});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "inc_list_elem"});

        // End if
        context.addInstruction(new String[]{"vri_end_if:", NO_OPERATION});

        context.addInstruction(new String[]{ADJUST, "-1"});

        context.addInstruction(new String[]{RETURN});
    }

    public void generateBasicVariableReferenceIncrementor(Context context) {
        context.addInstruction(new String[]{"inc_basic_variable:", NO_OPERATION});

        // Deletes reference
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "add_reference"});
//        context.addInstruction(new String[]{STORE_REGISTER, "HP"});
//        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
//        context.addInstruction(new String[]{STORE_ON_HEAP});

        context.addInstruction(new String[]{ADJUST, "-1"});

        context.addInstruction(new String[]{RETURN});
    }

    public void generateTupleReferenceIncrementor(Context context) {
        context.addInstruction(new String[]{"inc_tuple:", NO_OPERATION});

        // Deletes reference
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "add_reference"});
//        context.addInstruction(new String[]{STORE_REGISTER, "HP"});
//        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
//        context.addInstruction(new String[]{STORE_ON_HEAP});

        context.addInstruction(new String[]{LOAD_MULTIPLE_FROM_HEAP, "-3", "2"});

        // Utilises the first element
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "inc_variable"});
        context.addInstruction(new String[]{ADJUST, "-1"});

        // Utilises the second element
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "inc_variable"});
        context.addInstruction(new String[]{ADJUST, "-1"});

        context.addInstruction(new String[]{RETURN});
    }

    public void generateListReferenceIncrementor(Context context) {
        context.addInstruction(new String[]{"inc_list:", NO_OPERATION});

        // Deletes reference
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "add_reference"});
//        context.addInstruction(new String[]{STORE_REGISTER, "HP"});
//        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
//        context.addInstruction(new String[]{STORE_ON_HEAP});

        context.addInstruction(new String[]{LOAD_FROM_HEAP, "2"});

        // Utilises the head element
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "inc_list_elem"});
        context.addInstruction(new String[]{ADJUST, "-1"});

        context.addInstruction(new String[]{RETURN});
    }

    public void generateListElementReferenceIncrementor(Context context) {
        context.addInstruction(new String[]{"inc_list_elem:", NO_OPERATION});

        // Deletes reference
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "add_reference"});
//        context.addInstruction(new String[]{STORE_REGISTER, "HP"});
//        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
//        context.addInstruction(new String[]{STORE_ON_HEAP});

        // If x != 0 (end of a list)
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "2"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
        context.addInstruction(new String[]{NE});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "leri_end_if"});

        // Utilises a value of the element
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "2"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "inc_variable"});
        context.addInstruction(new String[]{ADJUST, "-1"});

        // Utilises the next element
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "3"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "inc_list_elem"});
        context.addInstruction(new String[]{ADJUST, "-1"});

        // End if
        context.addInstruction(new String[]{"leri_end_if:", NO_OPERATION});

        context.addInstruction(new String[]{RETURN});
    }

    public void generatePrint(Context context) {
        context.addInstruction(new String[]{"print:", NO_OPERATION});

        // If x is basic Int
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "0"}); // Int type identifier
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "pr_else_if_bool"});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "get_value"});
        context.addInstruction(new String[]{TRAP, "0"});

        context.addInstruction(new String[]{BRANCH, "pr_end_if"});

        // Else if x is Bool
        context.addInstruction(new String[]{"pr_else_if_bool:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "1"}); // Bool type identifier
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "pr_else_if_char"});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "get_value"});
        context.addInstruction(new String[]{TRAP, "0"});

        context.addInstruction(new String[]{BRANCH, "pr_end_if"});

        // Else if x is Char
        context.addInstruction(new String[]{"pr_else_if_char:", NO_OPERATION});
        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{LOAD_FROM_HEAP, "1"});
        context.addInstruction(new String[]{LOAD_CONSTANT, "2"}); // Char type identifier
        context.addInstruction(new String[]{EQ});
        context.addInstruction(new String[]{BRANCH_ON_FALSE, "pr_end_if"});

        context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
        context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "get_value"});
        context.addInstruction(new String[]{TRAP, "1"});

        context.addInstruction(new String[]{BRANCH, "pr_end_if"});

        // End if
        context.addInstruction(new String[]{"pr_end_if:", NO_OPERATION});

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
