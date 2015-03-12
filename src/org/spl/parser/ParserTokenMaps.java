package org.spl.parser;

import com.sun.tools.javac.util.Pair;
import org.spl.common.Nonterminal;
import org.spl.common.Symbol;
import org.spl.common.Token;

import java.util.ArrayList;
import java.util.HashMap;

public class ParserTokenMaps {

    public final static ArrayList<Token> ASTTokenList = new ArrayList<Token>();
    public final static ArrayList<Symbol> ASTTempNonterminalList = new ArrayList<Symbol>();
    public final static ArrayList<Symbol> ASTPossibleNonterminalList = new ArrayList<Symbol>();
    public final static ArrayList<Symbol> ASTStaticNonterminalList = new ArrayList<Symbol>();
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

        // AST temporary nonterminal list
        ASTTempNonterminalList.add(Nonterminal.MoreVarDecl);
        ASTTempNonterminalList.add(Nonterminal.DeclRest);
        ASTTempNonterminalList.add(Nonterminal.FuncRest);
        ASTTempNonterminalList.add(Nonterminal.BasicType);
        ASTTempNonterminalList.add(Nonterminal.MoreFArgs);
        ASTTempNonterminalList.add(Nonterminal.MoreField);
        ASTTempNonterminalList.add(Nonterminal.MoreActArgs);
        ASTTempNonterminalList.add(Nonterminal.MoreStmt);
        ASTTempNonterminalList.add(Nonterminal.RestOfStmt);

        // AST possibly nonterminal list
        ASTPossibleNonterminalList.add(Nonterminal.Stmt);
        ASTPossibleNonterminalList.add(Nonterminal.Exp);
        ASTPossibleNonterminalList.add(Nonterminal.Field);
        ASTPossibleNonterminalList.add(Nonterminal.E1);
        ASTPossibleNonterminalList.add(Nonterminal.E2);
        ASTPossibleNonterminalList.add(Nonterminal.E3);
        ASTPossibleNonterminalList.add(Nonterminal.E4);
        ASTPossibleNonterminalList.add(Nonterminal.E5);

        // AST static nonterminal list
//        ASTStaticNonterminalList.add(Nonterminal.Decl);
//        ASTStaticNonterminalList.add(Nonterminal.Stmt);
//        ASTStaticNonterminalList.add(Nonterminal.ActArgs);

        // AST space map
        ArrayList<Symbol> list;
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Decl);
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
        list.add(Nonterminal.Decl);
        list.add(Nonterminal.FArgs);
        list.add(Nonterminal.FSingleArg);
        ASTSpaceMap.put(Nonterminal.Type, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.VarDecl);
        ASTSpaceMap.put(Nonterminal.TypeWithoutId, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Decl);
        list.add(Nonterminal.MoreDecl);
        ASTSpaceMap.put(Nonterminal.FArgs, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.FArgs);
        ASTSpaceMap.put(Nonterminal.FSingleArg, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Stmt);
        ASTSpaceMap.put(Nonterminal.Exp, list);

        // AST new line map
        ASTNewLineMap.put(new Pair<Symbol, Symbol>(Nonterminal.Decl, Nonterminal.SPL), "\n\n");
        ASTNewLineMap.put(new Pair<Symbol, Symbol>(Nonterminal.Decl, Nonterminal.MoreDecl), "\n\n");
        ASTNewLineMap.put(new Pair<Symbol, Symbol>(Nonterminal.FuncBody, Nonterminal.Decl), "\n\n");
        ASTNewLineMap.put(new Pair<Symbol, Symbol>(Nonterminal.VarDecl, Nonterminal.FuncBody), "\n");
        ASTNewLineMap.put(new Pair<Symbol, Symbol>(Nonterminal.Stmt, Nonterminal.FuncBody), "\n\n");
        ASTNewLineMap.put(new Pair<Symbol, Symbol>(Nonterminal.Stmt, Nonterminal.Stmt), "\n");
        ASTNewLineMap.put(new Pair<Symbol, Symbol>(Nonterminal.Stmt, Nonterminal.Else), "\n");
//        ASTNewLineMap.put(Nonterminal.RestOfStmt, "\n");
//        ASTNewLineMap.put(Nonterminal.Ret, "\n");

        // AST tab map
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.FuncBody);
        ASTTabMap.put(Nonterminal.VarDecl, list);
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.FuncBody);
        list.add(Nonterminal.Stmt);
        list.add(Nonterminal.Else);
        ASTTabMap.put(Nonterminal.Stmt, list);

        // AST bracket map
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Decl);
        ASTBracketMap.put(Nonterminal.FArgs, new Pair<ArrayList<Symbol>, Pair<String, String>>(list, new Pair<String, String>("(", ")")));
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Decl);
        ASTBracketMap.put(Nonterminal.FuncBody, new Pair<ArrayList<Symbol>, Pair<String, String>>(list, new Pair<String, String>("{\n", "}")));
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Stmt);
        list.add(Nonterminal.Else);
        ASTBracketMap.put(Nonterminal.Stmt, new Pair<ArrayList<Symbol>, Pair<String, String>>(list, new Pair<String, String>("{\n", "}")));
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Stmt);
        ASTBracketMap.put(Nonterminal.ActArgs, new Pair<ArrayList<Symbol>, Pair<String, String>>(list, new Pair<String, String>("(", ")")));
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
        list.add(Nonterminal.Decl);
        ASTBracketMap.put(Nonterminal.FArgs, new Pair<ArrayList<Symbol>, Pair<String, String>>(list, new Pair<String, String>("(", ")")));

        // AST coma map
        ASTComaMap.put(Nonterminal.FSingleArg, ",");

        // AST semicolon map
        ASTSemicolonMap.put(Nonterminal.VarDecl, ";");
        ASTSemicolonMap.put(Nonterminal.RestOfStmt, ";");
        ASTSemicolonMap.put(Nonterminal.Ret, ";");


//        ArrayList<String> list2 = new ArrayList<String>();
//        list2.add(" ");
//        afterMap.put(Nonterminal.Type, list2);
    }
}
