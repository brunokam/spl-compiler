package org.spl.codegenerator;

import org.spl.common.structure.*;

import java.util.*;

public class CodeGenerator {

    private final static String LOAD_REGISTER = "ldr";
    private final static String BRANCH = "bra";
    private final static String STORE_REGISTER = "str";
    private final static String HALT = "halt";

    private ArrayList<String[]> process(Scope scope) {
        Context context = new Context();
        context.addInstruction(new String[]{LOAD_REGISTER, "MP"});
        context.addInstruction(new String[]{STORE_REGISTER, "R5"});

        for (StructureObject structureObject : scope.getStructures()) {
            if (structureObject instanceof VariableDeclaration) {
                structureObject.generateCode(context);
            }
        }

        context.addInstruction(new String[]{BRANCH, "main"});

        for (StructureObject structureObject : scope.getStructures()) {
            if (structureObject instanceof FunctionDeclaration) {
                structureObject.generateCode(context);
            }
        }

        context.addInstruction(new String[]{HALT});
        scope.generateGarbageCollectorFunction(context);

        return context.getInstructions();
    }

    public ArrayList<String[]> run(Scope globalScope) {
        ArrayList<String[]> instructionList = new ArrayList<String[]>();
        instructionList.addAll(process(globalScope));

        return instructionList;
    }
}
