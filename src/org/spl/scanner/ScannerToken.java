package org.spl.scanner;

public class ScannerToken {

    public enum Preliminary {
        IDENTIFIER, NUMERIC, CHARACTER, OPERATOR, BRACKET, PUNCTUATION, FIELD
    }

    // TP - type, BL - bool, CND - conditional, GET - getter, OP - operator, BR - bracket, PNC - punctuation
    public enum Final {
        IDENTIFIER, NUMERIC, CHARACTER,
        TP_VOID, TP_INT, TP_BOOL, TP_CHAR,
        BL_FALSE, BL_TRUE,
        CND_IF, CND_ELSE, CND_WHILE,
        RETURN,
        GET_HEAD, GET_TAIL, GET_FIRST, GET_SECOND,
        OP_ADD, OP_SUBTRACT, OP_MULTIPLY, OP_DIVIDE, OP_MODULO, OP_EQUAL, OP_NOT_EQUAL, OP_LESS_THAN, OP_GREATER_THAN,
        OP_LESS_THAN_OR_EQUAL, OP_GREATER_THAN_OR_EQUAL, OP_AND, OP_OR, OP_ASSIGN, OP_NEGATE, OP_CONCATENATE,
        BR_ROUND_START, BR_ROUND_END, BR_SQUARE_START, BR_SQUARE_END, BR_CURLY_START, BR_CURLY_END,
        PNC_COMA, PNC_SEMICOLON,
        FIELD
    }
}
