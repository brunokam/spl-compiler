package org.spl.typechecker;

import javafx.util.Pair;
import org.spl.common.Token;
import org.spl.common.type.BasicType;
import org.spl.common.type.EmptyArrayType;
import org.spl.common.type.Type;

import java.util.ArrayList;
import java.util.HashMap;

public class TypeCheckerMaps {

    private final static String INT = "Int";
    private final static String BOOL = "Bool";
    private final static String CHAR = "Char";
    private final static String EMPTY_ARRAY = "[]";
    private final static String ARRTYPE_INT = "[Int]";
    private final static String ARRTYPE_BOOL = "[Bool]";
    private final static String ARRTYPE_CHAR = "[Char]";

    public final static ArrayList<String> arrayTypeList = new ArrayList<String>();
    public final static ArrayList<Token> literalList = new ArrayList<Token>();
    public final static HashMap<Token, Type> basicTypeMap = new HashMap<Token, Type>();
    public final static HashMap<Token, ArrayList<String>> unaryOpParamTypeMap = new HashMap<Token, ArrayList<String>>();
    public final static HashMap<Token, HashMap<String, String>> unaryOpReturnTypeMap = new HashMap<Token, HashMap<String, String>>();
    public final static HashMap<Token, ArrayList<Pair<String, String>>> binaryOpParamTypeMap = new HashMap<Token, ArrayList<Pair<String, String>>>();
    public final static HashMap<Token, HashMap<String, String>> binaryOpReturnTypeMap = new HashMap<Token, HashMap<String, String>>();
    public final static HashMap<Token, ArrayList<String>> getterParamTypeMap = new HashMap<Token, ArrayList<String>>();
    public final static HashMap<Token, HashMap<String, String>> getterReturnTypeMap = new HashMap<Token, HashMap<String, String>>();

    static {
        arrayTypeList.add(ARRTYPE_INT);
        arrayTypeList.add(ARRTYPE_BOOL);
        arrayTypeList.add(ARRTYPE_CHAR);

        literalList.add(Token.NUMERIC);
        literalList.add(Token.CHARACTER);
        literalList.add(Token.BOOL_TRUE);
        literalList.add(Token.BOOL_FALSE);
        literalList.add(Token.EMPTY_ARRAY);

        basicTypeMap.put(Token.NUMERIC, new BasicType(INT));
        basicTypeMap.put(Token.CHARACTER, new BasicType(CHAR));
        basicTypeMap.put(Token.BOOL_TRUE, new BasicType(BOOL));
        basicTypeMap.put(Token.BOOL_FALSE, new BasicType(BOOL));
        basicTypeMap.put(Token.EMPTY_ARRAY, new EmptyArrayType());

        unaryOpParamTypeMap.put(Token.OPERATOR_NEGATE, new ArrayList<String>() {{
            add(BOOL);
        }});
        unaryOpParamTypeMap.put(Token.OPERATOR_SUBTRACT, new ArrayList<String>() {{
            add(INT);
        }});

        unaryOpReturnTypeMap.put(Token.OPERATOR_NEGATE, new HashMap<String, String>() {{
            put(BOOL, BOOL);
        }});
        unaryOpReturnTypeMap.put(Token.OPERATOR_SUBTRACT, new HashMap<String, String>() {{
            put(INT, INT);
        }});

        binaryOpParamTypeMap.put(Token.OPERATOR_ASSIGN, new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>(INT, INT));
            add(new Pair<String, String>(BOOL, BOOL));
            add(new Pair<String, String>(CHAR, CHAR));
            add(new Pair<String, String>(ARRTYPE_INT, ARRTYPE_INT));
            add(new Pair<String, String>(ARRTYPE_BOOL, ARRTYPE_BOOL));
            add(new Pair<String, String>(ARRTYPE_CHAR, ARRTYPE_CHAR));
            add(new Pair<String, String>(ARRTYPE_INT, EMPTY_ARRAY));
            add(new Pair<String, String>(ARRTYPE_BOOL, EMPTY_ARRAY));
            add(new Pair<String, String>(ARRTYPE_CHAR, EMPTY_ARRAY));
        }});
        binaryOpParamTypeMap.put(Token.OPERATOR_ADD, new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>(INT, INT));
            add(new Pair<String, String>(CHAR, CHAR));
        }});
        binaryOpParamTypeMap.put(Token.OPERATOR_SUBTRACT, new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>(INT, INT));
            add(new Pair<String, String>(CHAR, CHAR));
        }});
        binaryOpParamTypeMap.put(Token.OPERATOR_MULTIPLY, new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>(INT, INT));
        }});
        binaryOpParamTypeMap.put(Token.OPERATOR_DIVIDE, new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>(INT, INT));
        }});
        binaryOpParamTypeMap.put(Token.OPERATOR_MODULO, new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>(INT, INT));
        }});
        binaryOpParamTypeMap.put(Token.OPERATOR_EQUAL, new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>(INT, INT));
            add(new Pair<String, String>(BOOL, BOOL));
            add(new Pair<String, String>(CHAR, CHAR));
        }});
        binaryOpParamTypeMap.put(Token.OPERATOR_NOT_EQUAL, new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>(INT, INT));
            add(new Pair<String, String>(BOOL, BOOL));
            add(new Pair<String, String>(CHAR, CHAR));
        }});
        binaryOpParamTypeMap.put(Token.OPERATOR_LESS_THAN, new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>(INT, INT));
            add(new Pair<String, String>(CHAR, CHAR));
        }});
        binaryOpParamTypeMap.put(Token.OPERATOR_GREATER_THAN, new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>(INT, INT));
            add(new Pair<String, String>(CHAR, CHAR));
        }});
        binaryOpParamTypeMap.put(Token.OPERATOR_LESS_THAN_OR_EQUAL, new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>(INT, INT));
            add(new Pair<String, String>(CHAR, CHAR));
        }});
        binaryOpParamTypeMap.put(Token.OPERATOR_GREATER_THAN_OR_EQUAL, new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>(INT, INT));
            add(new Pair<String, String>(CHAR, CHAR));
        }});
        binaryOpParamTypeMap.put(Token.OPERATOR_AND, new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>(BOOL, BOOL));
        }});
        binaryOpParamTypeMap.put(Token.OPERATOR_OR, new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>(BOOL, BOOL));
        }});
        binaryOpParamTypeMap.put(Token.OPERATOR_CONCATENATE, new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>(INT, ARRTYPE_INT));
            add(new Pair<String, String>(BOOL, ARRTYPE_BOOL));
            add(new Pair<String, String>(CHAR, ARRTYPE_CHAR));
            add(new Pair<String, String>(INT, EMPTY_ARRAY));
            add(new Pair<String, String>(BOOL, EMPTY_ARRAY));
            add(new Pair<String, String>(CHAR, EMPTY_ARRAY));
        }});

        binaryOpReturnTypeMap.put(Token.OPERATOR_ASSIGN, new HashMap<String, String>() {{
            put(INT, BOOL);
            put(BOOL, BOOL);
            put(CHAR, BOOL);
            put(ARRTYPE_INT, BOOL);
            put(ARRTYPE_BOOL, BOOL);
            put(ARRTYPE_CHAR, BOOL);
        }});
        binaryOpReturnTypeMap.put(Token.OPERATOR_ADD, new HashMap<String, String>() {{
            put(INT, INT);
            put(CHAR, CHAR);
        }});
        binaryOpReturnTypeMap.put(Token.OPERATOR_SUBTRACT, new HashMap<String, String>() {{
            put(INT, INT);
            put(CHAR, CHAR);
        }});
        binaryOpReturnTypeMap.put(Token.OPERATOR_MULTIPLY, new HashMap<String, String>() {{
            put(INT, INT);
        }});
        binaryOpReturnTypeMap.put(Token.OPERATOR_DIVIDE, new HashMap<String, String>() {{
            put(INT, INT);
        }});
        binaryOpReturnTypeMap.put(Token.OPERATOR_MODULO, new HashMap<String, String>() {{
            put(INT, INT);
        }});
        binaryOpReturnTypeMap.put(Token.OPERATOR_EQUAL, new HashMap<String, String>() {{
            put(INT, BOOL);
            put(BOOL, BOOL);
            put(CHAR, BOOL);
        }});
        binaryOpReturnTypeMap.put(Token.OPERATOR_NOT_EQUAL, new HashMap<String, String>() {{
            put(INT, BOOL);
            put(BOOL, BOOL);
            put(CHAR, BOOL);
        }});
        binaryOpReturnTypeMap.put(Token.OPERATOR_LESS_THAN, new HashMap<String, String>() {{
            put(INT, BOOL);
            put(CHAR, BOOL);
        }});
        binaryOpReturnTypeMap.put(Token.OPERATOR_GREATER_THAN, new HashMap<String, String>() {{
            put(INT, BOOL);
            put(CHAR, BOOL);
        }});
        binaryOpReturnTypeMap.put(Token.OPERATOR_LESS_THAN_OR_EQUAL, new HashMap<String, String>() {{
            put(INT, BOOL);
            put(CHAR, BOOL);
        }});
        binaryOpReturnTypeMap.put(Token.OPERATOR_GREATER_THAN_OR_EQUAL, new HashMap<String, String>() {{
            put(INT, BOOL);
            put(CHAR, BOOL);
        }});
        binaryOpReturnTypeMap.put(Token.OPERATOR_AND, new HashMap<String, String>() {{
            put(BOOL, BOOL);
        }});
        binaryOpReturnTypeMap.put(Token.OPERATOR_OR, new HashMap<String, String>() {{
            put(BOOL, BOOL);
        }});
        binaryOpReturnTypeMap.put(Token.OPERATOR_CONCATENATE, new HashMap<String, String>() {{
            put(INT, ARRTYPE_INT);
            put(BOOL, ARRTYPE_BOOL);
            put(CHAR, ARRTYPE_CHAR);
        }});

        getterParamTypeMap.put(Token.GETTER_HEAD, new ArrayList<String>() {{
            add(ARRTYPE_INT);
            add(ARRTYPE_BOOL);
            add(ARRTYPE_CHAR);
        }});
        getterParamTypeMap.put(Token.GETTER_TAIL, new ArrayList<String>() {{
            add(ARRTYPE_INT);
            add(ARRTYPE_BOOL);
            add(ARRTYPE_CHAR);
        }});

        getterReturnTypeMap.put(Token.GETTER_HEAD, new HashMap<String, String>() {{
            put(ARRTYPE_INT, INT);
            put(ARRTYPE_BOOL, BOOL);
            put(ARRTYPE_CHAR, CHAR);
        }});
        getterReturnTypeMap.put(Token.GETTER_TAIL, new HashMap<String, String>() {{
            put(ARRTYPE_INT, ARRTYPE_INT);
            put(ARRTYPE_BOOL, ARRTYPE_BOOL);
            put(ARRTYPE_CHAR, ARRTYPE_CHAR);
        }});
    }
}
