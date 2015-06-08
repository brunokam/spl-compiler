package org.spl.common.type;

import java.util.HashMap;

public class EmptyListType extends ListType {

    public EmptyListType() {
        super(new PolymorphicType("t"));
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
        return false;
    }

    @Override
    public boolean isEmptyListType() {
        return true;
    }

    @Override
    public boolean containsPolymorphicTypes() {
        return false;
    }

    @Override
    public boolean unify(Type type) {
        if (type instanceof ListType) {
            return true;
        } else if (type instanceof PolymorphicType) {
            return true;
        }

        return false;
    }

    @Override
    public Integer getAddressSize() {
        return 2;
    }

    @Override
    public Integer getBodySize() {
        return 2;
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
