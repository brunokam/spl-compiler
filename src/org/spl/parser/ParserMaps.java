package org.spl.parser;

import javafx.util.Pair;
import org.spl.common.Nonterminal;
import org.spl.common.Symbol;
import org.spl.common.Token;

import java.util.ArrayList;
import java.util.HashMap;

public class ParserMaps {

    public final static ArrayList<Token> ASTAllowedTokenList = new ArrayList<Token>();
    public final static ArrayList<Token> ASTCollapsingTokenList = new ArrayList<Token>();
    public final static ArrayList<Token> ASTOperator1TokenList = new ArrayList<Token>();
    public final static ArrayList<Token> ASTOperator2TokenList = new ArrayList<Token>();
    public final static ArrayList<Token> ASTLogOperatorTokenList = new ArrayList<Token>();
    public final static ArrayList<Token> ASTCmpOperatorTokenList = new ArrayList<Token>();
    public final static ArrayList<Token> ASTMatOperatorTokenList = new ArrayList<Token>();
    public final static ArrayList<Token> ASTFieldTokenList = new ArrayList<Token>();
    public final static ArrayList<Symbol> ASTTempNonterminalList = new ArrayList<Symbol>();
    public final static ArrayList<Symbol> ASTPossibleNonterminalList = new ArrayList<Symbol>();
    public final static ArrayList<Symbol> ASTBodySymbolList = new ArrayList<Symbol>();
    public final static ArrayList<Symbol> ASTExpSymbolList = new ArrayList<Symbol>();
    public final static ArrayList<Symbol> ASTOperator2ContainerSymbolList = new ArrayList<Symbol>();
    public final static HashMap<Token, Token> ASTArrayTypeTokenMap = new HashMap<Token, Token>();

    public final static HashMap<Symbol, String> ASTNewLineSymbolMap = new HashMap<Symbol, String>();
    public final static HashMap<Symbol, Pair<String, String>> ASTBracketSymbolMap = new HashMap<Symbol, Pair<String, String>>();
    public final static ArrayList<Symbol> ASTSpaceBeforeSymbolList = new ArrayList<Symbol>();
    public final static ArrayList<Symbol> ASTSpaceAfterSymbolList = new ArrayList<Symbol>();
    public final static ArrayList<Symbol> ASTCommaAfterSymbolList = new ArrayList<Symbol>();
    public final static ArrayList<Symbol> ASTSemicolonAfterSymbolList = new ArrayList<Symbol>();

//    public final static HashMap<Symbol, ArrayList<String>> beforeMap = new HashMap<Symbol, ArrayList<String>>();
//    public final static HashMap<Symbol, ArrayList<String>> afterMap = new HashMap<Symbol, ArrayList<String>>();

    static {
        // AST token list
        ASTAllowedTokenList.add(Token.IDENTIFIER);
        ASTAllowedTokenList.add(Token.NUMERIC);
        ASTAllowedTokenList.add(Token.CHARACTER);
        ASTAllowedTokenList.add(Token.TYPE_VOID);
        ASTAllowedTokenList.add(Token.TYPE_INT);
        ASTAllowedTokenList.add(Token.TYPE_BOOL);
        ASTAllowedTokenList.add(Token.TYPE_CHAR);
        ASTAllowedTokenList.add(Token.BOOL_FALSE);
        ASTAllowedTokenList.add(Token.BOOL_TRUE);
        ASTAllowedTokenList.add(Token.CONDITIONAL_IF);
        ASTAllowedTokenList.add(Token.CONDITIONAL_ELSE);
        ASTAllowedTokenList.add(Token.CONDITIONAL_WHILE);
        ASTAllowedTokenList.add(Token.RETURN);
        ASTAllowedTokenList.add(Token.GET_HEAD);
        ASTAllowedTokenList.add(Token.GET_TAIL);
        ASTAllowedTokenList.add(Token.GET_FIRST);
        ASTAllowedTokenList.add(Token.GET_SECOND);
        ASTAllowedTokenList.add(Token.OPERATOR_ADD);
        ASTAllowedTokenList.add(Token.OPERATOR_SUBTRACT);
        ASTAllowedTokenList.add(Token.OPERATOR_MULTIPLY);
        ASTAllowedTokenList.add(Token.OPERATOR_DIVIDE);
        ASTAllowedTokenList.add(Token.OPERATOR_MODULO);
        ASTAllowedTokenList.add(Token.OPERATOR_EQUAL);
        ASTAllowedTokenList.add(Token.OPERATOR_NOT_EQUAL);
        ASTAllowedTokenList.add(Token.OPERATOR_LESS_THAN);
        ASTAllowedTokenList.add(Token.OPERATOR_GREATER_THAN);
        ASTAllowedTokenList.add(Token.OPERATOR_LESS_THAN_OR_EQUAL);
        ASTAllowedTokenList.add(Token.OPERATOR_GREATER_THAN_OR_EQUAL);
        ASTAllowedTokenList.add(Token.OPERATOR_AND);
        ASTAllowedTokenList.add(Token.OPERATOR_OR);
        ASTAllowedTokenList.add(Token.OPERATOR_ASSIGN);
        ASTAllowedTokenList.add(Token.OPERATOR_NEGATE);
        ASTAllowedTokenList.add(Token.OPERATOR_CONCATENATE);
        ASTAllowedTokenList.add(Token.BRA_SQUARE_START);
        ASTAllowedTokenList.add(Token.BRA_SQUARE_END);
        ASTAllowedTokenList.add(Token.EMPTY_ARRAY);

        // AST operator token list
        ASTOperator1TokenList.add(Token.OPERATOR_NEGATE);
        ASTOperator1TokenList.add(Token.OPERATOR_SUBTRACT);

        // AST operator token list
        ASTOperator2TokenList.add(Token.OPERATOR_ADD);
        ASTOperator2TokenList.add(Token.OPERATOR_SUBTRACT);
        ASTOperator2TokenList.add(Token.OPERATOR_MULTIPLY);
        ASTOperator2TokenList.add(Token.OPERATOR_DIVIDE);
        ASTOperator2TokenList.add(Token.OPERATOR_MODULO);
        ASTOperator2TokenList.add(Token.OPERATOR_CONCATENATE);
        ASTOperator2TokenList.add(Token.OPERATOR_EQUAL);
        ASTOperator2TokenList.add(Token.OPERATOR_NOT_EQUAL);
        ASTOperator2TokenList.add(Token.OPERATOR_GREATER_THAN);
        ASTOperator2TokenList.add(Token.OPERATOR_LESS_THAN);
        ASTOperator2TokenList.add(Token.OPERATOR_GREATER_THAN_OR_EQUAL);
        ASTOperator2TokenList.add(Token.OPERATOR_LESS_THAN_OR_EQUAL);
        ASTOperator2TokenList.add(Token.OPERATOR_AND);
        ASTOperator2TokenList.add(Token.OPERATOR_OR);

        // AST logic operator token list
        ASTLogOperatorTokenList.add(Token.OPERATOR_AND);
        ASTLogOperatorTokenList.add(Token.OPERATOR_OR);

        // AST comparison operator token list
        ASTCmpOperatorTokenList.add(Token.OPERATOR_EQUAL);
        ASTCmpOperatorTokenList.add(Token.OPERATOR_NOT_EQUAL);
        ASTCmpOperatorTokenList.add(Token.OPERATOR_GREATER_THAN);
        ASTCmpOperatorTokenList.add(Token.OPERATOR_LESS_THAN);
        ASTCmpOperatorTokenList.add(Token.OPERATOR_GREATER_THAN_OR_EQUAL);
        ASTCmpOperatorTokenList.add(Token.OPERATOR_LESS_THAN_OR_EQUAL);

        // AST math operator token list
        ASTMatOperatorTokenList.add(Token.OPERATOR_ADD);
        ASTMatOperatorTokenList.add(Token.OPERATOR_SUBTRACT);
        ASTMatOperatorTokenList.add(Token.OPERATOR_MULTIPLY);
        ASTMatOperatorTokenList.add(Token.OPERATOR_DIVIDE);
        ASTMatOperatorTokenList.add(Token.OPERATOR_MODULO);

        // AST temporary nonterminal list
        ASTTempNonterminalList.add(Nonterminal.MoreDecl);
        ASTTempNonterminalList.add(Nonterminal.DeclRest);
        ASTTempNonterminalList.add(Nonterminal.FuncRest);
        ASTTempNonterminalList.add(Nonterminal.MoreVarDecl);
        ASTTempNonterminalList.add(Nonterminal.MoreStmt);
        ASTTempNonterminalList.add(Nonterminal.MoreField);
        ASTTempNonterminalList.add(Nonterminal.BasicType);
        ASTTempNonterminalList.add(Nonterminal.MoreFArgs);
        ASTTempNonterminalList.add(Nonterminal.FieldRest);
        ASTTempNonterminalList.add(Nonterminal.MoreActArgs);
        ASTTempNonterminalList.add(Nonterminal.MoreStmt);
        ASTTempNonterminalList.add(Nonterminal.RestOfStmt);
        ASTTempNonterminalList.add(Nonterminal.VarDeclOrStmt);
        ASTTempNonterminalList.add(Nonterminal.ExpFieldOrFunc);
        ASTTempNonterminalList.add(Nonterminal.FuncCallRest);

        // AST possibly nonterminal list
        ASTPossibleNonterminalList.add(Nonterminal.E1);
        ASTPossibleNonterminalList.add(Nonterminal.E2);
        ASTPossibleNonterminalList.add(Nonterminal.E3);
        ASTPossibleNonterminalList.add(Nonterminal.E4);
        ASTPossibleNonterminalList.add(Nonterminal.E5);

        // AST collapsing token list
        ASTCollapsingTokenList.add(Token.CONDITIONAL_IF);
        ASTCollapsingTokenList.add(Token.CONDITIONAL_ELSE);
        ASTCollapsingTokenList.add(Token.CONDITIONAL_WHILE);
        ASTCollapsingTokenList.add(Token.RETURN);
        ASTCollapsingTokenList.add(Token.TYPE_INT);
        ASTCollapsingTokenList.add(Token.TYPE_BOOL);
        ASTCollapsingTokenList.add(Token.TYPE_CHAR);

        // AST body symbol list
        ASTBodySymbolList.add(Nonterminal.FuncDecl);
        ASTBodySymbolList.add(Token.CONDITIONAL_IF);
        ASTBodySymbolList.add(Token.CONDITIONAL_ELSE);
        ASTBodySymbolList.add(Token.CONDITIONAL_WHILE);

        // AST Exp symbol list
        ASTExpSymbolList.add(Nonterminal.E1);
        ASTExpSymbolList.add(Nonterminal.E2);
        ASTExpSymbolList.add(Nonterminal.E3);
        ASTExpSymbolList.add(Nonterminal.E4);
        ASTExpSymbolList.add(Nonterminal.E5);

        // AST operator container symbol list
        ASTOperator2ContainerSymbolList.add(Nonterminal.Op21);
        ASTOperator2ContainerSymbolList.add(Nonterminal.Op22);
        ASTOperator2ContainerSymbolList.add(Nonterminal.Op23);
        ASTOperator2ContainerSymbolList.add(Nonterminal.Op24);
        ASTOperator2ContainerSymbolList.add(Nonterminal.Op25);
        ASTOperator2ContainerSymbolList.add(Nonterminal.MoreOp21);
        ASTOperator2ContainerSymbolList.add(Nonterminal.MoreOp22);
        ASTOperator2ContainerSymbolList.add(Nonterminal.MoreOp23);
        ASTOperator2ContainerSymbolList.add(Nonterminal.MoreOp24);
        ASTOperator2ContainerSymbolList.add(Nonterminal.MoreOp25);

        // AST array type token map
        ASTArrayTypeTokenMap.put(Token.IDENTIFIER, Token.ARRTYPE_POLYMORPHIC);
        ASTArrayTypeTokenMap.put(Token.TYPE_INT, Token.ARRTYPE_INT);
        ASTArrayTypeTokenMap.put(Token.TYPE_BOOL, Token.ARRTYPE_BOOL);
        ASTArrayTypeTokenMap.put(Token.TYPE_CHAR, Token.ARRTYPE_CHAR);

        // AST field token list
        ASTFieldTokenList.add(Token.GET_FIRST);
        ASTFieldTokenList.add(Token.GET_SECOND);
        ASTFieldTokenList.add(Token.GET_HEAD);
        ASTFieldTokenList.add(Token.GET_TAIL);



        // AST new line symbol map
        ASTNewLineSymbolMap.put(Nonterminal.FuncDecl, "\n\n");
        ASTNewLineSymbolMap.put(Nonterminal.VarDecl, "\n");
        ASTNewLineSymbolMap.put(Nonterminal.FuncCall, "\n");
        ASTNewLineSymbolMap.put(Nonterminal.Stmt, "\n");
        ASTNewLineSymbolMap.put(Nonterminal.ConditionalStmt, "\n");

        // AST bracket symbol map
        ASTBracketSymbolMap.put(Nonterminal.DeclArgs, new Pair<String, String>("(", ")"));
        ASTBracketSymbolMap.put(Nonterminal.CallArgs, new Pair<String, String>("(", ")"));
        ASTBracketSymbolMap.put(Nonterminal.ConditionalExp, new Pair<String, String>("(", ")"));
        ASTBracketSymbolMap.put(Nonterminal.TupleType, new Pair<String, String>("(", ")"));
        ASTBracketSymbolMap.put(Nonterminal.Body, new Pair<String, String>(" {\n", "}"));

        // AST space after symbol list
        ASTSpaceBeforeSymbolList.add(Nonterminal.Op2);
        ASTSpaceBeforeSymbolList.add(Nonterminal.ElseStmt);

        // AST space after symbol list
        ASTSpaceAfterSymbolList.add(Nonterminal.Type);
        ASTSpaceAfterSymbolList.add(Nonterminal.ArrayType);
        ASTSpaceAfterSymbolList.add(Nonterminal.TupleType);
        ASTSpaceAfterSymbolList.add(Token.TYPE_VOID);
        ASTSpaceAfterSymbolList.add(Nonterminal.DeclSingleArg);
        ASTSpaceAfterSymbolList.add(Nonterminal.CallSingleArg);
        ASTSpaceAfterSymbolList.add(Nonterminal.Op2);
        ASTSpaceAfterSymbolList.add(Token.CONDITIONAL_IF);
        ASTSpaceAfterSymbolList.add(Token.CONDITIONAL_WHILE);
        ASTSpaceAfterSymbolList.add(Token.RETURN);

        // AST comma after symbol list
        ASTCommaAfterSymbolList.add(Nonterminal.DeclSingleArg);
        ASTCommaAfterSymbolList.add(Nonterminal.CallSingleArg);

        // AST semicolon after symbol list
        ASTSemicolonAfterSymbolList.add(Nonterminal.VarDecl);
        ASTSemicolonAfterSymbolList.add(Nonterminal.FuncCall);
        ASTSemicolonAfterSymbolList.add(Nonterminal.Stmt);
        ASTSemicolonAfterSymbolList.add(Nonterminal.ReturnStmt);


//        ArrayList<String> list2 = new ArrayList<String>();
//        list2.add(" ");
//        afterMap.put(Nonterminal.Type, list2);
    }
}
