package org.spl.common.structure;

import org.spl.common.type.Type;

import java.util.ArrayList;

public class FunctionCall {

    private String m_identifier;
    private ArrayList<Type> m_argumentTypeList;

    public FunctionCall(String identifier, ArrayList<Type> argumentTypeList) {
        m_identifier = identifier;
        m_argumentTypeList = new ArrayList<Type>(argumentTypeList);
    }

    public String getIdentifier() {
        return m_identifier;
    }

    public ArrayList<Type> getArgumentTypeList() {
        return m_argumentTypeList;
    }

    public boolean containsPolymorphicTypes() {
        for (Type type : m_argumentTypeList) {
            if (type.isPolymorphic()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "{identifier=\"" + m_identifier +
                "\", argumentTypeList=" + m_argumentTypeList +
                "}";
    }
}
