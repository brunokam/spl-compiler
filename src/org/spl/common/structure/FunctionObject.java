package org.spl.common.structure;

import org.spl.common.ASTNode;
import org.spl.common.Nonterminal;
import org.spl.common.type.BasicType;
import org.spl.common.type.Type;
import org.spl.typechecker.exception.EmptyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FunctionObject extends BindingObject {

    private ArrayList<VariableObject> m_argumentList;
    private ScopeObject m_scopeObject;
    private ASTNode m_declarationNode;
    private Type m_tempType;
    private boolean m_isPredefined;

    public FunctionObject(Type type, String identifier, ScopeObject parentScope, ASTNode declarationNode) {
        super(type, identifier);
        m_argumentList = new ArrayList<VariableObject>();
        m_scopeObject = new ScopeObject(parentScope, Nonterminal.FuncDecl);
        m_declarationNode = declarationNode;
        m_tempType = null;
        m_isPredefined = false;

        m_scopeObject.setFunctionObject(this);
    }

    // Constructor for predefined functions
    public FunctionObject(Type type, String identifier, ArrayList<VariableObject> arguments) {
        super(type, identifier);
        m_argumentList = new ArrayList<VariableObject>(arguments);
        m_scopeObject = null;
        m_declarationNode = new ASTNode(Nonterminal.FuncDecl);
        m_tempType = null;
        m_isPredefined = true;
    }

    public ArrayList<VariableObject> getArguments() {
        return m_argumentList;
    }

    public Type getTempType() {
        return m_tempType;
    }

    public ScopeObject getScopeObject() {
        return m_scopeObject;
    }

    public ASTNode getDeclarationNode() {
        return m_declarationNode;
    }

    public boolean isPredefined() {
        return m_isPredefined;
    }

    public void addArgument(VariableObject argument) {
        m_argumentList.add(argument);
    }

    public void setTempType(Type tempType) {
        m_tempType = tempType;
    }

    public boolean isPolymorphic() {
        for (VariableObject argument : m_argumentList) {
            if (argument.getType().isPolymorphic()) {
                return true;
            }
        }

        return false;
    }

    public boolean isVoid() {
        return m_type.unify(new BasicType("Void"));
    }

    public void initialiseScopeObject(ScopeObject parentScope) {
        m_scopeObject = new ScopeObject(parentScope, Nonterminal.FuncDecl);
        m_scopeObject.setFunctionObject(this);
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
}
