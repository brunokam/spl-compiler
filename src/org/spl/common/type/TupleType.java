package org.spl.common.type;

import java.util.HashMap;

public class TupleType extends Type {

    private Type m_innerTypeLeft;
    private Type m_innerTypeRight;

    public TupleType(Type innerTypeLeft, Type innerTypeRight) {
        super("(" + innerTypeLeft + ", " + innerTypeRight + ")");
        m_innerTypeLeft = innerTypeLeft;
        m_innerTypeRight = innerTypeRight;
    }

    public Type getInnerTypeLeft() {
        return m_innerTypeLeft;
    }

    public Type getInnerTypeRight() {
        return m_innerTypeRight;
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
        return true;
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
        return m_innerTypeLeft.containsPolymorphicTypes() || m_innerTypeRight.containsPolymorphicTypes();
    }

    @Override
    public boolean unify(Type type) {
        if (type instanceof TupleType) {
            TupleType tupleType = (TupleType) type;
            if (m_innerTypeLeft.unify(tupleType.getInnerTypeLeft()) && m_innerTypeRight.unify(tupleType.getInnerTypeRight())) {
                return true;
            }
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
//        return m_innerTypeLeft.getBodySize() + m_innerTypeRight.getBodySize();
        return 2;
    }

    @Override
    public Type replace(HashMap<String, Type> polymorphicMap) {
        return new TupleType(m_innerTypeLeft.replace(polymorphicMap), m_innerTypeRight.replace(polymorphicMap));
    }

    @Override
    public String toString() {
        return "(" + m_innerTypeLeft.toString() + ", " + m_innerTypeRight + ")";
    }
}
