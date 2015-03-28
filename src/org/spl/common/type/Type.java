package org.spl.common.type;

import java.util.HashMap;
import java.util.Iterator;

public abstract class Type {

    protected String m_typeString;

    public Type(String typeString) {
        m_typeString = typeString;
    }

    public abstract boolean isPolymorphic();

    public abstract boolean unify(Type type);

    public abstract Type replace(HashMap<String, Type> polymorphicMap);

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
            } else if ((type1 instanceof ArrayType && type2 instanceof EmptyArrayType) ||
                    (type1 instanceof EmptyArrayType && type2 instanceof ArrayType)) {
                return new HashMap<String, Type>();
            } else if (type1 instanceof ArrayType) {
                ArrayType arrayType1 = (ArrayType) type1;
                ArrayType arrayType2 = (ArrayType) type2;
                return _buildPolymorphicMap(arrayType1.getInnerType(), arrayType2.getInnerType());
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

    @Override
    public String toString() {
        return m_typeString;
    }
}
