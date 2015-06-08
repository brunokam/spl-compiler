package org.spl.common.type;

import java.util.HashMap;

public class ListType extends Type {

    private Type m_innerType;

    public ListType(Type innerType) {
        super("[" + innerType.toString() + "]");
        m_innerType = innerType;
    }

    public Type getInnerType() {
        return m_innerType;
    }

    @Override
    public boolean isBasicType() {
        return false;
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
        return true;
    }

    @Override
    public boolean isEmptyListType() {
        return false;
    }

    @Override
    public boolean containsPolymorphicTypes() {
        return m_innerType.containsPolymorphicTypes();
    }

    @Override
    public boolean unify(Type type) {
        if (type instanceof ListType) {
            ListType listType = (ListType) type;
            if (m_innerType.unify(listType.getInnerType())) {
                return true;
            }
        } else if (type instanceof EmptyListType) {
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
        return 2;
    }

    @Override
    public Type replace(HashMap<String, Type> polymorphicMap) {
        return new ListType(m_innerType.replace(polymorphicMap));
    }

    @Override
    public String toString() {
        return "[" + m_innerType.toString() + "]";
    }
}
