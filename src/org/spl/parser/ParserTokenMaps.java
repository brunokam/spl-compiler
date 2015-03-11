package org.spl.parser;

import org.spl.common.Token;

import java.util.ArrayList;

public class ParserTokenMaps {

    public final static ArrayList<Token> ASTTokenList = new ArrayList<Token>();

    static {
        // AST token list
        ASTTokenList.add(Token.IDENTIFIER);
        ASTTokenList.add(Token.NUMERIC);
        ASTTokenList.add(Token.CHARACTER);
        ASTTokenList.add(Token.TYPE_VOID);
        ASTTokenList.add(Token.TYPE_INT);
        ASTTokenList.add(Token.TYPE_BOOL);
        ASTTokenList.add(Token.TYPE_CHAR);
        ASTTokenList.add(Token.BOOL_FALSE);
        ASTTokenList.add(Token.BOOL_TRUE);
        ASTTokenList.add(Token.CONDITIONAL_IF);
        ASTTokenList.add(Token.CONDITIONAL_ELSE);
        ASTTokenList.add(Token.CONDITIONAL_WHILE);
        ASTTokenList.add(Token.RETURN);
        ASTTokenList.add(Token.GET_HEAD);
        ASTTokenList.add(Token.GET_TAIL);
        ASTTokenList.add(Token.GET_FIRST);
        ASTTokenList.add(Token.GET_SECOND);
        ASTTokenList.add(Token.OPERATOR_ADD);
        ASTTokenList.add(Token.OPERATOR_SUBTRACT);
        ASTTokenList.add(Token.OPERATOR_MULTIPLY);
        ASTTokenList.add(Token.OPERATOR_DIVIDE);
        ASTTokenList.add(Token.OPERATOR_MODULO);
        ASTTokenList.add(Token.OPERATOR_EQUAL);
        ASTTokenList.add(Token.OPERATOR_NOT_EQUAL);
        ASTTokenList.add(Token.OPERATOR_LESS_THAN);
        ASTTokenList.add(Token.OPERATOR_GREATER_THAN);
        ASTTokenList.add(Token.OPERATOR_LESS_THAN_OR_EQUAL);
        ASTTokenList.add(Token.OPERATOR_GREATER_THAN_OR_EQUAL);
        ASTTokenList.add(Token.OPERATOR_AND);
        ASTTokenList.add(Token.OPERATOR_OR);
        ASTTokenList.add(Token.OPERATOR_ASSIGN);
        ASTTokenList.add(Token.OPERATOR_NEGATE);
        ASTTokenList.add(Token.OPERATOR_CONCATENATE);
    }
}
