package org.spl.common.type;

import java.util.HashMap;

public class PolymorphicType extends Type {

    public PolymorphicType(String typeString) {
        super(typeString);
    }

    @Override
    public boolean isPolymorphic() {
        return true;
    }

    @Override
    public boolean unify(Type type) {
        return true;
    }

    @Override
    public Type replace(HashMap<String, Type> polymorphicMap) {
        if (polymorphicMap.containsKey(m_typeString)) {
            return polymorphicMap.get(m_typeString);
        }

        throw new RuntimeException();
    }
}
