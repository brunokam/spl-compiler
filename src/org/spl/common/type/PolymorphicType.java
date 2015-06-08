package org.spl.common.type;

import java.util.HashMap;

public class PolymorphicType extends Type {

    public PolymorphicType(String typeString) {
        super(typeString);
    }

    @Override
    public boolean isBasicType() {
        return false;
    }

    @Override
    public boolean isPolymorphicType() {
        return true;
    }

    @Override
    public boolean isTupleType() {
        return false;
    }

    @Override
    public boolean isListType() {
        return false;
    }

    @Override
    public boolean isEmptyListType() {
        return false;
    }

    @Override
    public boolean containsPolymorphicTypes() {
        return true;
    }

    @Override
    public boolean unify(Type type) {
        return true;
    }

    @Override
    public Integer getAddressSize() {
        return 1;
    }

    @Override
    public Integer getBodySize() {
        return 1;
    }

    @Override
    public Type replace(HashMap<String, Type> polymorphicMap) {
        if (polymorphicMap.containsKey(m_typeString)) {
            return polymorphicMap.get(m_typeString);
        }

        throw new RuntimeException();
    }

    @Override
    public String toString() {
        return m_typeString;
    }
}
