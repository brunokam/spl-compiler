package org.spl.common.type;

import java.util.HashMap;
import java.util.Iterator;

public abstract class Type {

    protected String m_typeString;

    public Type(String typeString) {
        m_typeString = typeString;
    }

    public abstract boolean isBasicType();

    public abstract boolean isPolymorphicType();

    public abstract boolean isTupleType();

    public abstract boolean isListType();

    public abstract boolean isEmptyListType();

    public abstract boolean containsPolymorphicTypes();

    public abstract boolean unify(Type type);

    public abstract Integer getAddressSize();

    public abstract Integer getBodySize();

    public abstract Type replace(HashMap<String, Type> polymorphicMap);

    public Integer getOverallSize() {
        return getBodySize() + 2;
    }

    private static HashMap<String, Type> _buildPolymorphicMap(final Type type1, final Type type2) {
        if (type1 instanceof PolymorphicType) {
            final PolymorphicType polymorphicType = (PolymorphicType) type1;

            if (type2 instanceof PolymorphicType) {
//                System.out.println(type1 + " " + type2);
                throw new RuntimeException();
//                return new HashMap<String, Type>() {{
//                    put(polymorphicType.toString(), type2);
//                }};
            } else {
                return new HashMap<String, Type>() {{
                    put(polymorphicType.toString(), type2);
                }};
            }
        } else if (type2 instanceof PolymorphicType) {
            final PolymorphicType polymorphicType = (PolymorphicType) type2;

            return new HashMap<String, Type>() {{
                put(polymorphicType.toString(), type1);
            }};
        } else {
            if (type1 instanceof BasicType) {
                return new HashMap<String, Type>();
            } else if ((type1 instanceof ListType && type2 instanceof EmptyListType) ||
                    (type1 instanceof EmptyListType && type2 instanceof ListType)) {
                return new HashMap<String, Type>();
            } else if (type1 instanceof ListType) {
                ListType listType1 = (ListType) type1;
                ListType listType2 = (ListType) type2;
                return _buildPolymorphicMap(listType1.getInnerType(), listType2.getInnerType());
            } else if (type1 instanceof TupleType) {
                TupleType tupleType1 = (TupleType) type1;
                TupleType tupleType2 = (TupleType) type2;

                HashMap<String, Type> result = _buildPolymorphicMap(tupleType1.getInnerTypeLeft(), tupleType2.getInnerTypeLeft());
                Iterator it = _buildPolymorphicMap(tupleType1.getInnerTypeRight(), tupleType2.getInnerTypeRight()).entrySet().iterator();
                while (it.hasNext()) {
                    HashMap.Entry entry = (HashMap.Entry) it.next();
                    String string = (String) entry.getKey();
                    Type type = (Type) entry.getValue();
                    if (!result.containsKey(string) || result.get(string).unify(type)) {
                        result.put(string, type);
                    } else {
                        throw new RuntimeException();
                    }
                }

                return result;
            }
        }

        throw new RuntimeException();
    }

    public static HashMap<String, Type> buildPolymorphicMap(Type type1, Type type2) {
        if (type1.unify(type2)) {
            return _buildPolymorphicMap(type1, type2);
        } else {
            throw new RuntimeException();
        }
    }
}
