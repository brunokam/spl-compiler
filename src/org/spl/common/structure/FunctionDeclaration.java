package org.spl.common.structure;

import org.spl.common.ASTNode;
import org.spl.common.FunctionCall;
import org.spl.common.Nonterminal;
import org.spl.common.type.BasicType;
import org.spl.common.type.Type;
import org.spl.typechecker.exception.EmptyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FunctionDeclaration extends StructureObject {

    private ArrayList<VariableDeclaration> m_argumentList;
    private Scope m_scope;
    private ASTNode m_declarationNode;
    private Type m_tempType;
    private boolean m_isPredefined;

    public FunctionDeclaration(Type type, String identifier, Scope parentScope, ASTNode declarationNode) {
        super(type, identifier);
        m_argumentList = new ArrayList<VariableDeclaration>();
        m_scope = new Scope(parentScope, Nonterminal.FuncDecl);
        m_declarationNode = declarationNode;
        m_tempType = null;
        m_isPredefined = false;

        m_scope.setFunctionObject(this);
    }

    // Constructor for predefined functions
    public FunctionDeclaration(Type type, String identifier, ArrayList<VariableDeclaration> arguments) {
        super(type, identifier);
        m_argumentList = new ArrayList<VariableDeclaration>(arguments);
        m_scope = null;
        m_declarationNode = new ASTNode(Nonterminal.FuncDecl);
        m_tempType = null;
        m_isPredefined = true;
    }

    public ArrayList<VariableDeclaration> getArguments() {
        return m_argumentList;
    }

    public Type getTempType() {
        return m_tempType;
    }

    public Scope getScopeObject() {
        return m_scope;
    }

    public ASTNode getDeclarationNode() {
        return m_declarationNode;
    }

    public boolean isPredefined() {
        return m_isPredefined;
    }

    public void addArgument(VariableDeclaration argument) {
        m_argumentList.add(argument);
    }

    public void setTempType(Type tempType) {
        m_tempType = tempType;
    }

    public boolean isPolymorphic() {
        for (VariableDeclaration argument : m_argumentList) {
            if (argument.getType().containsPolymorphicTypes()) {
                return true;
            }
        }

        return false;
    }

    public boolean isVoid() {
        return m_type.unify(new BasicType("Void"));
    }

    public void initialiseScopeObject(Scope parentScope) {
        m_scope = new Scope(parentScope, Nonterminal.FuncDecl);
        m_scope.setFunctionObject(this);
    }

    public boolean match(FunctionCall functionCall) {
        String identifier = functionCall.getIdentifier();
        ArrayList<Type> argumentTypeList = functionCall.getArgumentTypeList();

        if (!m_identifier.equals(identifier) || m_argumentList.size() != argumentTypeList.size()) {
            return false;
        }

        for (int i = 0; i < m_argumentList.size(); ++i) {
            if (!m_argumentList.get(i).getType().unify(argumentTypeList.get(i))) {
                return false;
            }
        }

        return true;
    }

    public boolean match(FunctionCall functionCall, HashMap<String, Type> polymorphicMap) throws EmptyException {
        String identifier = functionCall.getIdentifier();
        ArrayList<Type> argumentTypeList = functionCall.getArgumentTypeList();

        if (!m_identifier.equals(identifier) || m_argumentList.size() != argumentTypeList.size()) {
            return false;
        }

        for (int i = 0; i < m_argumentList.size(); ++i) {
            Type type1 = m_argumentList.get(i).getType().replace(getArgumentTypeMap(functionCall));
            Type type2 = argumentTypeList.get(i);
            if (!type1.unify(type2)) {
                return false;
            }
        }

        return true;
    }

    public HashMap<String, Type> getArgumentTypeMap(FunctionCall functionCall) throws EmptyException {
        HashMap<String, Type> result = new HashMap<String, Type>();
        ArrayList<Type> callArgumentTypeList = functionCall.getArgumentTypeList();

        if (m_argumentList.size() != callArgumentTypeList.size()) {
            throw new EmptyException();
        }

        for (int i = 0; i < m_argumentList.size(); ++i) {
            HashMap<String, Type> map = Type.buildPolymorphicMap(m_argumentList.get(i).getType(), callArgumentTypeList.get(i));
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                HashMap.Entry entry = (HashMap.Entry) it.next();
//                System.out.println(m_argumentList.get(i).getType() + " " + callArgumentTypeList.get(i));
                String string = (String) entry.getKey();
                Type type = (Type) entry.getValue();
//                System.out.println(string + " " + type);

                if (!result.containsKey(string)) {
                    result.put((String) entry.getKey(), (Type) entry.getValue());
                } else if (!result.get(string).unify(type)) {
                    throw new EmptyException();
                }
            }
        }

        return result;
    }

    private void generateReturnSubroutine(Context context) {
        context.addInstruction(new String[]{"return_from_" + m_identifier + ":"});

        saveHeapPointer(context);

        Integer additionalAdjustment = 0;

        for (StructureObject structureObject : m_scope.getStructures()) {
            if (structureObject instanceof VariableDeclaration) {
                VariableDeclaration declaration = (VariableDeclaration) structureObject;
                Type type = declaration.getType();
                String addressPosition = context.getAddressPosition(declaration).toString();

                context.addInstruction(new String[]{LOAD_LOCAL, addressPosition});

                if (type.isBasicType()) {
                    context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "utilise_basic_variable"});
                } else if (type.isTupleType()) {
                    context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "utilise_tuple"});
                } else if (type.isListType()) {
                    context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "utilise_list"});
                }

                ++additionalAdjustment;
            }
        }

        restoreHeapPointer(context);

        generateAdjustmentCode(context, additionalAdjustment);

        context.addInstruction(new String[]{STORE_REGISTER, "MP"});
        context.addInstruction(new String[]{RETURN});
    }

    private void generateAdjustmentCode(Context context, Integer additionalAdjustment) {
        Integer adjustment = -(additionalAdjustment + context.getLocalEnvironmentLength());

        if (adjustment < 0) {
            context.addInstruction(new String[]{ADJUST, adjustment.toString()});
        }
    }

    @Override
    public void generate(Context context) {
        context.addInstruction(new String[]{"f_" + m_identifier + ":"});
        context.addInstruction(new String[]{LOAD_REGISTER, "MP"});
        context.addInstruction(new String[]{LOAD_STACK_ADDRESS, "0"});
        context.addInstruction(new String[]{STORE_REGISTER, "MP"});

        for (VariableDeclaration argument : m_argumentList) {
            context.addArgument(argument);
        }

        for (StructureObject structureObject : m_scope.getStructures()) {
            structureObject.generate(context);
        }

        if (!m_identifier.equals("main")) {
            if (isVoid()) {
                context.addInstruction(new String[]{BRANCH, "return_from_" + m_identifier});
            }

            generateReturnSubroutine(context);
        } else {
            generateAdjustmentCode(context, 0);
            context.addInstruction(new String[]{STORE_REGISTER, "MP"});
        }

        context.clearLocal();
    }

    @Override
    public String toString() {
        return "FUNCTION " + m_identifier;
    }
}
