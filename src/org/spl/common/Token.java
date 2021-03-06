package org.spl.common;

public enum Token implements Symbol {

    IDENTIFIER, NUMERIC, CHARACTER,
    TYPE_VOID, TYPE_POLYMORPHIC, TYPE_INT, TYPE_BOOL, TYPE_CHAR,
    ARRTYPE_POLYMORPHIC, ARRTYPE_INT, ARRTYPE_BOOL, ARRTYPE_CHAR,
    TUPLE_TYPE,
    BOOL_TRUE, BOOL_FALSE,
    CONDITIONAL_IF, CONDITIONAL_ELSE, CONDITIONAL_WHILE,
    RETURN,
    GETTER_HEAD, GETTER_TAIL, GETTER_FIRST, GETTER_SECOND,
    OPERATOR_ADD, OPERATOR_SUBTRACT, OPERATOR_MULTIPLY, OPERATOR_DIVIDE, OPERATOR_MODULO, OPERATOR_EQUAL,
    OPERATOR_NOT_EQUAL, OPERATOR_LESS_THAN, OPERATOR_GREATER_THAN, OPERATOR_LESS_THAN_OR_EQUAL,
    OPERATOR_GREATER_THAN_OR_EQUAL, OPERATOR_AND, OPERATOR_OR, OPERATOR_ASSIGN, OPERATOR_NEGATE, OPERATOR_CONCATENATE,
    BRA_ROUND_START, BRA_ROUND_END, BRA_SQUARE_START, BRA_SQUARE_END, EMPTY_ARRAY, BRA_CURLY_START, BRA_CURLY_END,
    PUNC_COMMA, PUNC_SEMICOLON,
    FIELD;

    @Override
    public String getType() {
        return "TOKEN";
    }
}
