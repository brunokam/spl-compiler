package org.spl.common;

import org.spl.common.type.TupleType;
import org.spl.common.structure.StructureObject;
import org.spl.common.structure.FunctionDeclaration;
import org.spl.common.structure.VariableDeclaration;
import org.spl.common.type.ListType;
import org.spl.common.type.BasicType;
import org.spl.common.type.PolymorphicType;

import java.util.ArrayList;

public class PredefinedFunctions {

    private final static BasicType TYPE_VOID = new BasicType("Void");
    private final static BasicType TYPE_INT = new BasicType("Int");
    private final static BasicType TYPE_BOOL = new BasicType("Bool");
    private final static BasicType TYPE_CHAR = new BasicType("Char");
    private final static PolymorphicType TYPE_POLYMORPHIC = new PolymorphicType("s");
    private final static ListType TYPE_ARRAY_POLYMORPHIC = new ListType(TYPE_POLYMORPHIC);

    private final static String OPERATOR_NEGATE = "!";
    private final static String OPERATOR_SUBTRACT1 = "-";

    private final static String OPERATOR_ASSIGN = "=";
    private final static String OPERATOR_ADD = "+";
    private final static String OPERATOR_SUBTRACT2 = "-";
    private final static String OPERATOR_MULTIPLY = "*";
    private final static String OPERATOR_DIVIDE = "/";
    private final static String OPERATOR_MODULO = "%";
    private final static String OPERATOR_CONCATENATE = ":";
    private final static String OPERATOR_EQUAL = "==";
    private final static String OPERATOR_NOT_EQUAL = "!=";
    private final static String OPERATOR_LESS_THAN = "<";
    private final static String OPERATOR_GREATER_THAN = ">";
    private final static String OPERATOR_LESS_THAN_OR_EQUAL = "<=";
    private final static String OPERATOR_GREATER_THAN_OR_EQUAL = ">=";
    private final static String OPERATOR_AND = "&&";
    private final static String OPERATOR_OR = "||";

    private final static String FUNC_IS_EMPTY = "isEmpty";
    private final static String FUNC_PRINT = "print";
    private final static String FUNC_NEW = "new";

    private final static String GETTER_HEAD = "hd";
    private final static String GETTER_TAIL = "tl";
    private final static String GETTER_FIRST = "fst";
    private final static String GETTER_SECOND = "snd";

    // Unary operator list
    private final static ArrayList<StructureObject> m_unaryOperatorList = new ArrayList<StructureObject>() {{
        // Unary operator !
        add(new FunctionDeclaration(TYPE_BOOL, OPERATOR_NEGATE, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_BOOL, "arg1"));
        }}));

        // Unary operator -
        add(new FunctionDeclaration(TYPE_INT, OPERATOR_SUBTRACT1, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_INT, "arg1"));
        }}));
        add(new FunctionDeclaration(TYPE_CHAR, OPERATOR_SUBTRACT1, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_CHAR, "arg1"));
        }}));
    }};

    // Binary operator list
    private final static ArrayList<StructureObject> m_binaryOperatorList = new ArrayList<StructureObject>() {{
        // Binary operator =
        add(new FunctionDeclaration(TYPE_BOOL, OPERATOR_ASSIGN, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_POLYMORPHIC, "arg1"));
            add(new VariableDeclaration(TYPE_POLYMORPHIC, "arg2"));
        }}));
//        add(new FunctionObject(TYPE_BOOL, OPERATOR_ASSIGN, new ArrayList<VariableObject>() {{
//            add(new VariableObject(TYPE_CHAR, "arg1"));
//            add(new VariableObject(TYPE_CHAR, "arg2"));
//        }}));

        // Binary operator +
        add(new FunctionDeclaration(TYPE_INT, OPERATOR_ADD, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_INT, "arg1"));
            add(new VariableDeclaration(TYPE_INT, "arg2"));
        }}));
        add(new FunctionDeclaration(TYPE_CHAR, OPERATOR_ADD, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_CHAR, "arg1"));
            add(new VariableDeclaration(TYPE_CHAR, "arg2"));
        }}));

        // Binary operator -
        add(new FunctionDeclaration(TYPE_INT, OPERATOR_SUBTRACT2, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_INT, "arg1"));
            add(new VariableDeclaration(TYPE_INT, "arg2"));
        }}));
        add(new FunctionDeclaration(TYPE_CHAR, OPERATOR_SUBTRACT2, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_CHAR, "arg1"));
            add(new VariableDeclaration(TYPE_CHAR, "arg2"));
        }}));

        // Binary operator *
        add(new FunctionDeclaration(TYPE_INT, OPERATOR_MULTIPLY, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_INT, "arg1"));
            add(new VariableDeclaration(TYPE_INT, "arg2"));
        }}));

        // Binary operator /
        add(new FunctionDeclaration(TYPE_INT, OPERATOR_DIVIDE, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_INT, "arg1"));
            add(new VariableDeclaration(TYPE_INT, "arg2"));
        }}));

        // Binary operator %
        add(new FunctionDeclaration(TYPE_INT, OPERATOR_MODULO, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_INT, "arg1"));
            add(new VariableDeclaration(TYPE_INT, "arg2"));
        }}));

        // Binary operator :
        add(new FunctionDeclaration(TYPE_ARRAY_POLYMORPHIC, OPERATOR_CONCATENATE, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_POLYMORPHIC, "arg1"));
            add(new VariableDeclaration(TYPE_ARRAY_POLYMORPHIC, "arg2"));
        }}));

        // Binary operator ==
        add(new FunctionDeclaration(TYPE_BOOL, OPERATOR_EQUAL, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_POLYMORPHIC, "arg1"));
            add(new VariableDeclaration(TYPE_POLYMORPHIC, "arg2"));
        }}));
//        add(new FunctionObject(TYPE_BOOL, OPERATOR_EQUAL, new ArrayList<VariableObject>() {{
//            add(new VariableObject(TYPE_CHAR, "arg1"));
//            add(new VariableObject(TYPE_CHAR, "arg2"));
//        }}));
//        add(new FunctionObject(TYPE_BOOL, OPERATOR_EQUAL, new ArrayList<VariableObject>() {{
//            add(new VariableObject(TYPE_BOOL, "arg1"));
//            add(new VariableObject(TYPE_BOOL, "arg2"));
//        }}));

        // Binary operator !=
        add(new FunctionDeclaration(TYPE_BOOL, OPERATOR_NOT_EQUAL, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_POLYMORPHIC, "arg1"));
            add(new VariableDeclaration(TYPE_POLYMORPHIC, "arg2"));
        }}));
//        add(new FunctionObject(TYPE_BOOL, OPERATOR_NOT_EQUAL, new ArrayList<VariableObject>() {{
//            add(new VariableObject(TYPE_CHAR, "arg1"));
//            add(new VariableObject(TYPE_CHAR, "arg2"));
//        }}));
//        add(new FunctionObject(TYPE_BOOL, OPERATOR_NOT_EQUAL, new ArrayList<VariableObject>() {{
//            add(new VariableObject(TYPE_BOOL, "arg1"));
//            add(new VariableObject(TYPE_BOOL, "arg2"));
//        }}));

        // Binary operator <
        add(new FunctionDeclaration(TYPE_BOOL, OPERATOR_LESS_THAN, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_POLYMORPHIC, "arg1"));
            add(new VariableDeclaration(TYPE_POLYMORPHIC, "arg2"));
        }}));
//        add(new FunctionObject(TYPE_BOOL, OPERATOR_LESS_THAN, new ArrayList<VariableObject>() {{
//            add(new VariableObject(TYPE_CHAR, "arg1"));
//            add(new VariableObject(TYPE_CHAR, "arg2"));
//        }}));

        // Binary operator >
        add(new FunctionDeclaration(TYPE_BOOL, OPERATOR_GREATER_THAN, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_POLYMORPHIC, "arg1"));
            add(new VariableDeclaration(TYPE_POLYMORPHIC, "arg2"));
        }}));
//        add(new FunctionObject(TYPE_BOOL, OPERATOR_GREATER_THAN, new ArrayList<VariableObject>() {{
//            add(new VariableObject(TYPE_CHAR, "arg1"));
//            add(new VariableObject(TYPE_CHAR, "arg2"));
//        }}));

        // Binary operator <=
        add(new FunctionDeclaration(TYPE_BOOL, OPERATOR_LESS_THAN_OR_EQUAL, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_POLYMORPHIC, "arg1"));
            add(new VariableDeclaration(TYPE_POLYMORPHIC, "arg2"));
        }}));
//        add(new FunctionObject(TYPE_BOOL, OPERATOR_LESS_THAN_OR_EQUAL, new ArrayList<VariableObject>() {{
//            add(new VariableObject(TYPE_CHAR, "arg1"));
//            add(new VariableObject(TYPE_CHAR, "arg2"));
//        }}));

        // Binary operator >=
        add(new FunctionDeclaration(TYPE_BOOL, OPERATOR_GREATER_THAN_OR_EQUAL, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_POLYMORPHIC, "arg1"));
            add(new VariableDeclaration(TYPE_POLYMORPHIC, "arg2"));
        }}));
//        add(new FunctionObject(TYPE_BOOL, OPERATOR_GREATER_THAN_OR_EQUAL, new ArrayList<VariableObject>() {{
//            add(new VariableObject(TYPE_CHAR, "arg1"));
//            add(new VariableObject(TYPE_CHAR, "arg2"));
//        }}));

        // Binary operator &&
        add(new FunctionDeclaration(TYPE_BOOL, OPERATOR_AND, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_BOOL, "arg1"));
            add(new VariableDeclaration(TYPE_BOOL, "arg2"));
        }}));

        // Binary operator ||
        add(new FunctionDeclaration(TYPE_BOOL, OPERATOR_OR, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_BOOL, "arg1"));
            add(new VariableDeclaration(TYPE_BOOL, "arg2"));
        }}));
    }};

    // Function list
    private final static ArrayList<StructureObject> m_functionList = new ArrayList<StructureObject>() {{
        // Function isEmpty
        add(new FunctionDeclaration(TYPE_BOOL, FUNC_IS_EMPTY, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_ARRAY_POLYMORPHIC, "arg1"));
        }}));

        // Function print
        add(new FunctionDeclaration(TYPE_VOID, FUNC_PRINT, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_POLYMORPHIC, "arg1"));
        }}));

        // Function new
        add(new FunctionDeclaration(TYPE_POLYMORPHIC, FUNC_NEW, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_POLYMORPHIC, "arg1"));
        }}));
    }};

    // Getter list
    private final static ArrayList<StructureObject> m_getterList = new ArrayList<StructureObject>() {{
        // Function hd
        add(new FunctionDeclaration(TYPE_POLYMORPHIC, GETTER_HEAD, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_ARRAY_POLYMORPHIC, "arg1"));
        }}));

        // Function tl
        add(new FunctionDeclaration(TYPE_ARRAY_POLYMORPHIC, GETTER_TAIL, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(TYPE_ARRAY_POLYMORPHIC, "arg1"));
        }}));

        // Function fst
        add(new FunctionDeclaration(new PolymorphicType("t1"), GETTER_FIRST, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(new TupleType(new PolymorphicType("t1"), new PolymorphicType("t2")), "arg1"));
        }}));

        // Function snd
        add(new FunctionDeclaration(new PolymorphicType("t2"), GETTER_SECOND, new ArrayList<VariableDeclaration>() {{
            add(new VariableDeclaration(new TupleType(new PolymorphicType("t1"), new PolymorphicType("t2")), "arg1"));
        }}));
    }};

    public static ArrayList<StructureObject> all() {
        ArrayList<StructureObject> result = new ArrayList<StructureObject>();
        result.addAll(m_unaryOperatorList);
        result.addAll(m_binaryOperatorList);
        result.addAll(m_functionList);
        result.addAll(m_getterList);

        return result;
    }
}
