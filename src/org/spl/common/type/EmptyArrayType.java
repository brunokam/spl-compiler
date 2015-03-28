package org.spl.common.type;

import java.util.HashMap;

public class EmptyArrayType extends ArrayType {

    public EmptyArrayType() {
        super(new PolymorphicType("t"));
    }

    @Override
    public boolean isPolymorphic() {
        return false;
    }

    @Override
    public boolean unify(Type type) {
        if (type instanceof ArrayType) {
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

    @Override
    public String toString() {
        return "[]";
    }
}
