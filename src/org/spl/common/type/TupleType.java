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
    public boolean isPolymorphic() {
        return m_innerTypeLeft.isPolymorphic() || m_innerTypeRight.isPolymorphic();
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
    public Type replace(HashMap<String, Type> polymorphicMap) {
        return new TupleType(m_innerTypeLeft.replace(polymorphicMap), m_innerTypeRight.replace(polymorphicMap));
    }
}
