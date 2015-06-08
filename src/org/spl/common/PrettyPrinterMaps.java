package org.spl.common;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class PrettyPrinterMaps {

    public final static ArrayList<Token> ASTGetterTokenList = new ArrayList<Token>();
    public final static HashMap<Symbol, String> ASTNewLineAfterSymbolMap = new HashMap<Symbol, String>();
    public final static HashMap<Symbol, String> ASTNewLineBeforeSymbolMap = new HashMap<Symbol, String>();
    public final static HashMap<Symbol, Pair<String, String>> ASTBracketSymbolMap = new HashMap<Symbol, Pair<String, String>>();
    public final static ArrayList<Symbol> ASTSpaceBeforeSymbolList = new ArrayList<Symbol>();
    public final static ArrayList<Symbol> ASTSpaceAfterSymbolList = new ArrayList<Symbol>();
    public final static ArrayList<Symbol> ASTCommaAfterSymbolList = new ArrayList<Symbol>();
    public final static ArrayList<Symbol> ASTSemicolonAfterSymbolList = new ArrayList<Symbol>();

    static {
        // AST getter token list
        ASTGetterTokenList.add(Token.GETTER_FIRST);
        ASTGetterTokenList.add(Token.GETTER_SECOND);
        ASTGetterTokenList.add(Token.GETTER_HEAD);
        ASTGetterTokenList.add(Token.GETTER_TAIL);

        // AST new line before symbol map
        ASTNewLineBeforeSymbolMap.put(Nonterminal.FuncDecl, "\n");
        ASTNewLineBeforeSymbolMap.put(Nonterminal.GlobalVarDecl, "\n");
        ASTNewLineBeforeSymbolMap.put(Nonterminal.IfStmt, "\n");
        ASTNewLineBeforeSymbolMap.put(Nonterminal.WhileStmt, "\n");

        // AST new line after symbol map
        ASTNewLineAfterSymbolMap.put(Nonterminal.FuncDecl, "\n");
        ASTNewLineAfterSymbolMap.put(Nonterminal.VarDecl, "\n");
        ASTNewLineAfterSymbolMap.put(Nonterminal.FuncCall, "\n");
        ASTNewLineAfterSymbolMap.put(Nonterminal.Stmt, "\n");
        ASTNewLineAfterSymbolMap.put(Nonterminal.IfStmt, "\n");
        ASTNewLineAfterSymbolMap.put(Nonterminal.WhileStmt, "\n");

        // AST bracket symbol map
        ASTBracketSymbolMap.put(Nonterminal.DeclArgs, new Pair<String, String>("(", ")"));
        ASTBracketSymbolMap.put(Nonterminal.CallArgs, new Pair<String, String>("(", ")"));
        ASTBracketSymbolMap.put(Nonterminal.ConditionalExp, new Pair<String, String>("(", ")"));
        ASTBracketSymbolMap.put(Nonterminal.TupleExp, new Pair<String, String>("(", ")"));
        ASTBracketSymbolMap.put(Nonterminal.TupleType, new Pair<String, String>("(", ")"));
        ASTBracketSymbolMap.put(Nonterminal.Body, new Pair<String, String>(" {\n", "}"));
        ASTBracketSymbolMap.put(Nonterminal.ListType, new Pair<String, String>("[", "]"));

        // AST space before symbol list
        ASTSpaceBeforeSymbolList.add(Nonterminal.Op2);
        ASTSpaceBeforeSymbolList.add(Nonterminal.ElseStmt);

        // AST space after symbol list
        ASTSpaceAfterSymbolList.add(Nonterminal.BasicType);
        ASTSpaceAfterSymbolList.add(Nonterminal.PolymorphicType);
        ASTSpaceAfterSymbolList.add(Nonterminal.ListType);
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
        ASTSemicolonAfterSymbolList.add(Nonterminal.GlobalVarDecl);
        ASTSemicolonAfterSymbolList.add(Nonterminal.VarDecl);
        ASTSemicolonAfterSymbolList.add(Nonterminal.FuncCall);
        ASTSemicolonAfterSymbolList.add(Nonterminal.Stmt);
        ASTSemicolonAfterSymbolList.add(Nonterminal.ReturnStmt);
    }
}
