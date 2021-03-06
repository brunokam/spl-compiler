package org.spl.common.type;

import java.util.HashMap;

public class BasicType extends Type {

    public BasicType(String typeString) {
        super(typeString);
    }

    @Override
    public boolean isBasicType() {
        return true;
    }

    @Override
    public boolean isPolymorphicType() {
        return false;
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
        return false;
    }

    @Override
    public boolean unify(Type type) {
        if (type instanceof BasicType && m_typeString.equals(type.toString())) {
            return true;
        } else if (type instanceof PolymorphicType) {
            return true;
        }

        return false;
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
        return this;
    }

    @Override
    public String toString() {
        return m_typeString;
    }
}
