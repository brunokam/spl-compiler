package org.spl.common.type;

import java.util.HashMap;

public class ArrayType extends Type {

    private Type m_innerType;

    public ArrayType(Type innerType) {
        super("[" + innerType.toString() + "]");
        m_innerType = innerType;
    }

    public Type getInnerType() {
        return m_innerType;
    }

    @Override
    public boolean isPolymorphic() {
        return m_innerType.isPolymorphic();
    }

    @Override
    public boolean unify(Type type) {
        if (type instanceof ArrayType) {
            ArrayType arrayType = (ArrayType) type;
            if (m_innerType.unify(arrayType.getInnerType())) {
                return true;
            }
        } else if (type instanceof EmptyArrayType) {
            return true;
        } else if (type instanceof PolymorphicType) {
            return true;
        }

        return false;
    }

    @Override
    public Type replace(HashMap<String, Type> polymorphicMap) {
        return new ArrayType(m_innerType.replace(polymorphicMap));
    }
}
