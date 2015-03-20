package org.spl.parser;

import com.sun.tools.javac.util.Pair;
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
    public final static ArrayList<Symbol> ASTTempNonterminalList = new ArrayList<Symbol>();
    public final static ArrayList<Symbol> ASTPossibleNonterminalList = new ArrayList<Symbol>();
    public final static ArrayList<Symbol> ASTBodySymbolList = new ArrayList<Symbol>();
    public final static ArrayList<Symbol> ASTExpSymbolList = new ArrayList<Symbol>();
    public final static ArrayList<Symbol> ASTOperator2ContainerSymbolList = new ArrayList<Symbol>();
    public final static HashMap<Token, Token> ASTArrayTypeTokenMap = new HashMap<Token, Token>();
    public final static HashMap<Symbol, Pair<ArrayList<Symbol>, Pair<String, String>>> ASTBracketMap = new HashMap<Symbol, Pair<ArrayList<Symbol>, Pair<String, String>>>();
    public final static HashMap<Symbol, String> ASTComaMap = new HashMap<Symbol, String>();
    public final static HashMap<Symbol, String> ASTSemicolonMap = new HashMap<Symbol, String>();
    public final static HashMap<Symbol, ArrayList<Symbol>> ASTSpaceMap = new HashMap<Symbol, ArrayList<Symbol>>();
    public final static HashMap<Pair<Symbol, Symbol>, String> ASTNewLineMap = new HashMap<Pair<Symbol, Symbol>, String>();
    public final static HashMap<Symbol, ArrayList<Symbol>> ASTTabMap = new HashMap<Symbol, ArrayList<Symbol>>();

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

        // AST space map
        ArrayList<Symbol> list;
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.FuncDecl);
        list.add(Nonterminal.MoreDecl);
        ASTSpaceMap.put(Token.TYPE_VOID, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.VarDecl);
        list.add(Nonterminal.Stmt);
        list.add(Nonterminal.E1);
        list.add(Nonterminal.E2);
        list.add(Nonterminal.E3);
        list.add(Nonterminal.E4);
        list.add(Nonterminal.E5);
        ASTSpaceMap.put(Token.IDENTIFIER, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.VarDecl);
        list.add(Nonterminal.E1);
        list.add(Nonterminal.E2);
        list.add(Nonterminal.E3);
        list.add(Nonterminal.E4);
        list.add(Nonterminal.E5);
        ASTSpaceMap.put(Token.NUMERIC, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.VarDecl);
        list.add(Nonterminal.E1);
        list.add(Nonterminal.E2);
        list.add(Nonterminal.E3);
        list.add(Nonterminal.E4);
        list.add(Nonterminal.E5);
        ASTSpaceMap.put(Token.CHARACTER, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Stmt);
        ASTSpaceMap.put(Token.CONDITIONAL_IF, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Stmt);
        ASTSpaceMap.put(Token.CONDITIONAL_WHILE, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Else);
        ASTSpaceMap.put(Token.CONDITIONAL_ELSE, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op21);
        ASTSpaceMap.put(Token.OPERATOR_AND, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op21);
        ASTSpaceMap.put(Token.OPERATOR_OR, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op22);
        ASTSpaceMap.put(Token.OPERATOR_EQUAL, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op22);
        ASTSpaceMap.put(Token.OPERATOR_NOT_EQUAL, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op22);
        ASTSpaceMap.put(Token.OPERATOR_LESS_THAN, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op22);
        ASTSpaceMap.put(Token.OPERATOR_GREATER_THAN, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op22);
        ASTSpaceMap.put(Token.OPERATOR_LESS_THAN_OR_EQUAL, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op22);
        ASTSpaceMap.put(Token.OPERATOR_GREATER_THAN_OR_EQUAL, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op23);
        ASTSpaceMap.put(Token.OPERATOR_MODULO, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op24);
        ASTSpaceMap.put(Token.OPERATOR_ADD, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op24);
        ASTSpaceMap.put(Token.OPERATOR_SUBTRACT, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op25);
        ASTSpaceMap.put(Token.OPERATOR_MULTIPLY, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op25);
        ASTSpaceMap.put(Token.OPERATOR_DIVIDE, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.VarDecl);
        list.add(Nonterminal.VarDeclOrStmt);
        list.add(Nonterminal.RestOfStmt);
        ASTSpaceMap.put(Token.OPERATOR_ASSIGN, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Stmt);
        ASTSpaceMap.put(Token.RETURN, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.FuncDecl);
        list.add(Nonterminal.DeclArgs);
        list.add(Nonterminal.DeclSingleArg);
        ASTSpaceMap.put(Nonterminal.Type, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.VarDecl);
        ASTSpaceMap.put(Nonterminal.TypeWithoutId, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.FuncDecl);
        list.add(Nonterminal.MoreDecl);
        ASTSpaceMap.put(Nonterminal.DeclArgs, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.DeclArgs);
        ASTSpaceMap.put(Nonterminal.DeclSingleArg, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Stmt);
        ASTSpaceMap.put(Nonterminal.Exp, list);

        // AST new line map
        ASTNewLineMap.put(new Pair<Symbol, Symbol>(Nonterminal.FuncDecl, Nonterminal.SPL), "\n\n");
        ASTNewLineMap.put(new Pair<Symbol, Symbol>(Nonterminal.FuncDecl, Nonterminal.MoreDecl), "\n\n");
        ASTNewLineMap.put(new Pair<Symbol, Symbol>(Nonterminal.Body, Nonterminal.FuncDecl), "\n\n");
        ASTNewLineMap.put(new Pair<Symbol, Symbol>(Nonterminal.VarDecl, Nonterminal.Body), "\n");
        ASTNewLineMap.put(new Pair<Symbol, Symbol>(Nonterminal.Stmt, Nonterminal.Body), "\n\n");
        ASTNewLineMap.put(new Pair<Symbol, Symbol>(Nonterminal.Stmt, Nonterminal.Stmt), "\n");
        ASTNewLineMap.put(new Pair<Symbol, Symbol>(Nonterminal.Stmt, Nonterminal.Else), "\n");
//        ASTNewLineMap.put(Nonterminal.RestOfStmt, "\n");
//        ASTNewLineMap.put(Nonterminal.Ret, "\n");

        // AST tab map
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Body);
        ASTTabMap.put(Nonterminal.VarDecl, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Body);
        list.add(Nonterminal.Stmt);
        list.add(Nonterminal.Else);
        ASTTabMap.put(Nonterminal.Stmt, list);

        // AST bracket map
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.FuncDecl);
        ASTBracketMap.put(Nonterminal.DeclArgs, new Pair<ArrayList<Symbol>, Pair<String, String>>(list, new Pair<String, String>("(", ")")));
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.FuncDecl);
        ASTBracketMap.put(Nonterminal.Body, new Pair<ArrayList<Symbol>, Pair<String, String>>(list, new Pair<String, String>("{\n", "}")));
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Stmt);
        list.add(Nonterminal.Else);
        ASTBracketMap.put(Nonterminal.Stmt, new Pair<ArrayList<Symbol>, Pair<String, String>>(list, new Pair<String, String>("{\n", "}")));
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Stmt);
        ASTBracketMap.put(Nonterminal.CallArgs, new Pair<ArrayList<Symbol>, Pair<String, String>>(list, new Pair<String, String>("(", ")")));
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Stmt);
        ASTBracketMap.put(Nonterminal.Exp, new Pair<ArrayList<Symbol>, Pair<String, String>>(list, new Pair<String, String>("(", ")")));
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.E1);
        list.add(Nonterminal.E2);
        list.add(Nonterminal.E3);
        list.add(Nonterminal.E4);
        list.add(Nonterminal.E5);
        list.add(Nonterminal.Exp);
        ASTBracketMap.put(Nonterminal.ExpFieldOrFunc, new Pair<ArrayList<Symbol>, Pair<String, String>>(list, new Pair<String, String>("(", ")")));
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.FuncDecl);
        ASTBracketMap.put(Nonterminal.DeclArgs, new Pair<ArrayList<Symbol>, Pair<String, String>>(list, new Pair<String, String>("(", ")")));

        // AST coma map
        ASTComaMap.put(Nonterminal.DeclSingleArg, ",");

        // AST semicolon map
        ASTSemicolonMap.put(Nonterminal.VarDecl, ";");
        ASTSemicolonMap.put(Nonterminal.RestOfStmt, ";");
        ASTSemicolonMap.put(Nonterminal.Ret, ";");


//        ArrayList<String> list2 = new ArrayList<String>();
//        list2.add(" ");
//        afterMap.put(Nonterminal.Type, list2);
    }
}
