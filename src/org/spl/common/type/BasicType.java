package org.spl.common.type;

import java.util.HashMap;

public class BasicType extends Type {

    public BasicType(String typeString) {
        super(typeString);
    }

    @Override
    public boolean isPolymorphic() {
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
    public Type replace(HashMap<String, Type> polymorphicMap) {
        return this;
    }
}
