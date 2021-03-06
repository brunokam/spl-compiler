package org.spl.parser;

import org.spl.common.Nonterminal;
import org.spl.common.Symbol;
import org.spl.common.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Grammar {

    public final static HashMap<Nonterminal, HashSet<Token>> firstSetMap = new HashMap<Nonterminal, HashSet<Token>>();
    public final static HashMap<Nonterminal, ArrayList<ArrayList<Symbol>>> rulesMap = new HashMap<Nonterminal, ArrayList<ArrayList<Symbol>>>();

    static {
        // FIRST SET
        HashSet<Token> set;

        set = new HashSet<Token>();
        set.add(Token.BRA_CURLY_START);
        set.add(Token.CONDITIONAL_IF);
        set.add(Token.CONDITIONAL_WHILE);
        set.add(Token.RETURN);
        set.add(Token.IDENTIFIER);
        firstSetMap.put(Nonterminal.Stmt, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.Stmt));
        set.add(null);
        firstSetMap.put(Nonterminal.MoreStmt, set);

        set = new HashSet<Token>();
        set.add(Token.CONDITIONAL_ELSE);
        set.add(null);
        firstSetMap.put(Nonterminal.ElseStmt, set);

        set = new HashSet<Token>();
        set.add(Token.OPERATOR_SUBTRACT);
        set.add(Token.OPERATOR_NEGATE);
        firstSetMap.put(Nonterminal.Op1, set);

        set = new HashSet<Token>();
        set.add(Token.OPERATOR_AND);
        set.add(Token.OPERATOR_OR);
        firstSetMap.put(Nonterminal.Op21, set);

        set = new HashSet<Token>();
        set.add(Token.OPERATOR_EQUAL);
        set.add(Token.OPERATOR_NOT_EQUAL);
        set.add(Token.OPERATOR_LESS_THAN);
        set.add(Token.OPERATOR_GREATER_THAN);
        set.add(Token.OPERATOR_LESS_THAN_OR_EQUAL);
        set.add(Token.OPERATOR_GREATER_THAN_OR_EQUAL);
        set.add(Token.OPERATOR_CONCATENATE);
        firstSetMap.put(Nonterminal.Op22, set);

        set = new HashSet<Token>();
        set.add(Token.OPERATOR_MODULO);
        firstSetMap.put(Nonterminal.Op23, set);

        set = new HashSet<Token>();
        set.add(Token.OPERATOR_ADD);
        set.add(Token.OPERATOR_SUBTRACT);
        firstSetMap.put(Nonterminal.Op24, set);

        set = new HashSet<Token>();
        set.add(Token.OPERATOR_MULTIPLY);
        set.add(Token.OPERATOR_DIVIDE);
        firstSetMap.put(Nonterminal.Op25, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.Op21));
        firstSetMap.put(Nonterminal.MoreOp21, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.Op22));
        firstSetMap.put(Nonterminal.MoreOp22, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.Op23));
        firstSetMap.put(Nonterminal.MoreOp23, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.Op24));
        firstSetMap.put(Nonterminal.MoreOp24, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.Op25));
        firstSetMap.put(Nonterminal.MoreOp25, set);

        set = new HashSet<Token>();
        set.add(Token.PUNC_COMMA);
        set.add(Token.BRA_ROUND_END);
        firstSetMap.put(Nonterminal.RestAfterBracket, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.Op1));
        set.add(Token.IDENTIFIER);
        set.add(Token.NUMERIC);
        set.add(Token.CHARACTER);
        set.add(Token.BOOL_TRUE);
        set.add(Token.BOOL_FALSE);
        set.add(Token.BRA_ROUND_START);
        set.add(Token.EMPTY_ARRAY);
        firstSetMap.put(Nonterminal.E5, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.E5));
        firstSetMap.put(Nonterminal.Exp, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.E5));
        firstSetMap.put(Nonterminal.E1, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.E5));
        firstSetMap.put(Nonterminal.E2, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.E5));
        firstSetMap.put(Nonterminal.E3, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.E5));
        firstSetMap.put(Nonterminal.E4, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.Exp));
        set.add(null);
        firstSetMap.put(Nonterminal.ReturnStmt, set);

        set = new HashSet<Token>();
        set.add(Token.FIELD);
        set.add(Token.OPERATOR_ASSIGN);
        set.add(Token.BRA_ROUND_START);
        firstSetMap.put(Nonterminal.RestOfStmt, set);

        set = new HashSet<Token>();
        set.add(Token.FIELD);
        set.add(Token.BRA_ROUND_START);
        firstSetMap.put(Nonterminal.ExpGetterOrFunc, set);

        set = new HashSet<Token>();
        set.add(Token.FIELD);
        set.add(null);
        firstSetMap.put(Nonterminal.Getter, set);

        set = new HashSet<Token>();
        set.add(Token.GETTER_HEAD);
        set.add(Token.GETTER_TAIL);
        set.add(Token.GETTER_FIRST);
        set.add(Token.GETTER_SECOND);
        firstSetMap.put(Nonterminal.GetterRest, set);

        set = new HashSet<Token>();
        set.add(Token.FIELD);
        firstSetMap.put(Nonterminal.MoreGetter, set);

        set = new HashSet<Token>();
        set.add(Token.BRA_ROUND_START);
        firstSetMap.put(Nonterminal.FuncCallRest, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.Exp));
        set.add(null);
        firstSetMap.put(Nonterminal.CallArgs, set);

        set = new HashSet<Token>();
        set.add(Token.PUNC_COMMA);
        set.add(null);
        firstSetMap.put(Nonterminal.MoreActArgs, set);

        set = new HashSet<Token>();
        set.add(Token.TYPE_INT);
        set.add(Token.TYPE_BOOL);
        set.add(Token.TYPE_CHAR);
        firstSetMap.put(Nonterminal.BasicType, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.BasicType));
        set.add(Token.BRA_ROUND_START);
        set.add(Token.BRA_SQUARE_START);
        set.add(Token.IDENTIFIER);
        firstSetMap.put(Nonterminal.Type, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.BasicType));
        set.add(Token.BRA_ROUND_START);
        set.add(Token.BRA_SQUARE_START);
        firstSetMap.put(Nonterminal.TypeWithoutId, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.RestOfStmt));
        set.add(Token.IDENTIFIER);
        firstSetMap.put(Nonterminal.VarDeclOrStmt, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.Type));
        set.addAll(firstSetMap.get(Nonterminal.Stmt));
        firstSetMap.put(Nonterminal.Body, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.Type));
        firstSetMap.put(Nonterminal.DeclSingleArg, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.Type));
        set.add(null);
        firstSetMap.put(Nonterminal.DeclArgs, set);

        set = new HashSet<Token>();
        set.add(Token.PUNC_COMMA);
        set.add(null);
        firstSetMap.put(Nonterminal.MoreFArgs, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.Type));
        firstSetMap.put(Nonterminal.VarDecl, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.Type));
        set.add(null);
        firstSetMap.put(Nonterminal.MoreVarDecl, set);

        set = new HashSet<Token>();
        set.add(Token.BRA_ROUND_START);
        firstSetMap.put(Nonterminal.FuncRest, set);

        set = new HashSet<Token>();
        set.add(Token.OPERATOR_ASSIGN);
        set.add(Token.BRA_ROUND_START);
        firstSetMap.put(Nonterminal.DeclRest, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.Type));
        set.add(Token.TYPE_VOID);
        firstSetMap.put(Nonterminal.FuncDecl, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.FuncDecl));
        set.add(null);
        firstSetMap.put(Nonterminal.MoreDecl, set);

        set = new HashSet<Token>(firstSetMap.get(Nonterminal.FuncDecl));
        firstSetMap.put(Nonterminal.SPL, set);


        // RULES
        ArrayList<Symbol> list;

        // SPL
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.FuncDecl);
        list.add(Nonterminal.MoreDecl);
        rulesMap.put(Nonterminal.SPL, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.SPL).add(list);

        // MoreDecl
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.FuncDecl);
        list.add(Nonterminal.MoreDecl);
        rulesMap.put(Nonterminal.MoreDecl, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.MoreDecl).add(list);

        list = new ArrayList<Symbol>();
        list.add(null);
        rulesMap.get(Nonterminal.MoreDecl).add(list);

        // FuncDecl
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Type);
        list.add(Token.IDENTIFIER);
        list.add(Nonterminal.DeclRest);
        rulesMap.put(Nonterminal.FuncDecl, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.FuncDecl).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.TYPE_VOID);
        list.add(Token.IDENTIFIER);
        list.add(Nonterminal.FuncRest);
        rulesMap.get(Nonterminal.FuncDecl).add(list);

        // DeclRest
        list = new ArrayList<Symbol>();
        list.add(Token.OPERATOR_ASSIGN);
        list.add(Nonterminal.Exp);
        list.add(Token.PUNC_SEMICOLON);
        rulesMap.put(Nonterminal.DeclRest, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.DeclRest).add(list);

        list = new ArrayList<Symbol>();
        list.add(Nonterminal.FuncRest);
        rulesMap.get(Nonterminal.DeclRest).add(list);

        // FuncRest
        list = new ArrayList<Symbol>();
        list.add(Token.BRA_ROUND_START);
        list.add(Nonterminal.DeclArgs);
        list.add(Token.BRA_ROUND_END);
        list.add(Token.BRA_CURLY_START);
        list.add(Nonterminal.Body);
        list.add(Token.BRA_CURLY_END);
        rulesMap.put(Nonterminal.FuncRest, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.FuncRest).add(list);

        // Body
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.MoreVarDecl);
        list.add(Nonterminal.MoreStmt);
        rulesMap.put(Nonterminal.Body, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.Body).add(list);

        // MoreVarDecl
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.VarDecl);
        list.add(Nonterminal.MoreVarDecl);
        rulesMap.put(Nonterminal.MoreVarDecl, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.MoreVarDecl).add(list);

        list = new ArrayList<Symbol>();
        list.add(null);
        rulesMap.get(Nonterminal.MoreVarDecl).add(list);

        // VarDecl
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.TypeWithoutId);
        list.add(Token.IDENTIFIER);
        list.add(Token.OPERATOR_ASSIGN);
        list.add(Nonterminal.Exp);
        list.add(Token.PUNC_SEMICOLON);
        rulesMap.put(Nonterminal.VarDecl, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.VarDecl).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.IDENTIFIER);
        list.add(Nonterminal.VarDeclOrStmt);
        rulesMap.get(Nonterminal.VarDecl).add(list);

        // VarDeclOrStmt
        list = new ArrayList<Symbol>();
        list.add(Token.IDENTIFIER);
        list.add(Token.OPERATOR_ASSIGN);
        list.add(Nonterminal.Exp);
        list.add(Token.PUNC_SEMICOLON);
        rulesMap.put(Nonterminal.VarDeclOrStmt, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.VarDeclOrStmt).add(list);

        list = new ArrayList<Symbol>();
        list.add(Nonterminal.RestOfStmt);
        rulesMap.get(Nonterminal.VarDeclOrStmt).add(list);

        // TypeWithoutId
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.BasicType);
        rulesMap.put(Nonterminal.TypeWithoutId, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.TypeWithoutId).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.BRA_ROUND_START);
        list.add(Nonterminal.Type);
        list.add(Token.PUNC_COMMA);
        list.add(Nonterminal.Type);
        list.add(Token.BRA_ROUND_END);
        rulesMap.get(Nonterminal.TypeWithoutId).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.BRA_SQUARE_START);
        list.add(Nonterminal.Type);
        list.add(Token.BRA_SQUARE_END);
        rulesMap.get(Nonterminal.TypeWithoutId).add(list);

        // Type
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.BasicType);
        rulesMap.put(Nonterminal.Type, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.Type).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.BRA_ROUND_START);
        list.add(Nonterminal.Type);
        list.add(Token.PUNC_COMMA);
        list.add(Nonterminal.Type);
        list.add(Token.BRA_ROUND_END);
        rulesMap.get(Nonterminal.Type).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.BRA_SQUARE_START);
        list.add(Nonterminal.Type);
        list.add(Token.BRA_SQUARE_END);
        rulesMap.get(Nonterminal.Type).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.IDENTIFIER);
        rulesMap.get(Nonterminal.Type).add(list);

        // BasicType
        list = new ArrayList<Symbol>();
        list.add(Token.TYPE_INT);
        rulesMap.put(Nonterminal.BasicType, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.BasicType).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.TYPE_BOOL);
        rulesMap.get(Nonterminal.BasicType).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.TYPE_CHAR);
        rulesMap.get(Nonterminal.BasicType).add(list);

        // DeclArgs
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.DeclSingleArg);
        list.add(Nonterminal.MoreFArgs);
        rulesMap.put(Nonterminal.DeclArgs, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.DeclArgs).add(list);

        list = new ArrayList<Symbol>();
        list.add(null);
        rulesMap.get(Nonterminal.DeclArgs).add(list);

        // DeclSingleArg
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Type);
        list.add(Token.IDENTIFIER);
        rulesMap.put(Nonterminal.DeclSingleArg, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.DeclSingleArg).add(list);

        // MoreFArgs
        list = new ArrayList<Symbol>();
        list.add(Token.PUNC_COMMA);
        list.add(Nonterminal.DeclSingleArg);
        list.add(Nonterminal.MoreFArgs);
        rulesMap.put(Nonterminal.MoreFArgs, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.MoreFArgs).add(list);

        list = new ArrayList<Symbol>();
        list.add(null);
        rulesMap.get(Nonterminal.MoreFArgs).add(list);

        // MoreStmt
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Stmt);
        list.add(Nonterminal.MoreStmt);
        rulesMap.put(Nonterminal.MoreStmt, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.MoreStmt).add(list);

        list = new ArrayList<Symbol>();
        list.add(null);
        rulesMap.get(Nonterminal.MoreStmt).add(list);

        // Stmt
        list = new ArrayList<Symbol>();
        list.add(Token.BRA_CURLY_START);
        list.add(Nonterminal.MoreStmt);
        list.add(Token.BRA_CURLY_END);
        rulesMap.put(Nonterminal.Stmt, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.Stmt).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.CONDITIONAL_IF);
        list.add(Token.BRA_ROUND_START);
        list.add(Nonterminal.Exp);
        list.add(Token.BRA_ROUND_END);
        list.add(Nonterminal.Stmt);
        list.add(Nonterminal.ElseStmt);
        rulesMap.get(Nonterminal.Stmt).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.CONDITIONAL_WHILE);
        list.add(Token.BRA_ROUND_START);
        list.add(Nonterminal.Exp);
        list.add(Token.BRA_ROUND_END);
        list.add(Nonterminal.Stmt);
        rulesMap.get(Nonterminal.Stmt).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.RETURN);
        list.add(Nonterminal.ReturnStmt);
        list.add(Token.PUNC_SEMICOLON);
        rulesMap.get(Nonterminal.Stmt).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.IDENTIFIER);
        list.add(Nonterminal.RestOfStmt);
        rulesMap.get(Nonterminal.Stmt).add(list);

        // ElseStmt
        list = new ArrayList<Symbol>();
        list.add(Token.CONDITIONAL_ELSE);
        list.add(Nonterminal.Stmt);
        rulesMap.put(Nonterminal.ElseStmt, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.ElseStmt).add(list);

        list = new ArrayList<Symbol>();
        list.add(null);
        rulesMap.get(Nonterminal.ElseStmt).add(list);

        // ReturnStmt
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Exp);
        rulesMap.put(Nonterminal.ReturnStmt, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.ReturnStmt).add(list);

        list = new ArrayList<Symbol>();
        list.add(null);
        rulesMap.get(Nonterminal.ReturnStmt).add(list);

        // RestOfStmt
        list = new ArrayList<Symbol>();
        list.add(Token.BRA_ROUND_START);
        list.add(Nonterminal.CallArgs);
        list.add(Token.BRA_ROUND_END);
        list.add(Token.PUNC_SEMICOLON);
        rulesMap.put(Nonterminal.RestOfStmt, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.RestOfStmt).add(list);

        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Getter);
        list.add(Token.OPERATOR_ASSIGN);
        list.add(Nonterminal.Exp);
        list.add(Token.PUNC_SEMICOLON);
        rulesMap.get(Nonterminal.RestOfStmt).add(list);

        // Exp
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.E1);
        list.add(Nonterminal.MoreOp21);
        rulesMap.put(Nonterminal.Exp, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.Exp).add(list);

        // E1
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.E2);
        list.add(Nonterminal.MoreOp22);
        rulesMap.put(Nonterminal.E1, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.E1).add(list);

        // E2
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.E3);
        list.add(Nonterminal.MoreOp23);
        rulesMap.put(Nonterminal.E2, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.E2).add(list);

        // E3
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.E4);
        list.add(Nonterminal.MoreOp24);
        rulesMap.put(Nonterminal.E3, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.E3).add(list);

        // E4
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.E5);
        list.add(Nonterminal.MoreOp25);
        rulesMap.put(Nonterminal.E4, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.E4).add(list);

        // E5
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op1);
        list.add(Nonterminal.E5);
        rulesMap.put(Nonterminal.E5, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.E5).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.NUMERIC);
        rulesMap.get(Nonterminal.E5).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.CHARACTER);
        rulesMap.get(Nonterminal.E5).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.BOOL_TRUE);
        rulesMap.get(Nonterminal.E5).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.BOOL_FALSE);
        rulesMap.get(Nonterminal.E5).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.BRA_ROUND_START);
        list.add(Nonterminal.Exp);
        list.add(Nonterminal.RestAfterBracket);
        rulesMap.get(Nonterminal.E5).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.EMPTY_ARRAY);
        rulesMap.get(Nonterminal.E5).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.IDENTIFIER);
        list.add(Nonterminal.ExpGetterOrFunc);
        rulesMap.get(Nonterminal.E5).add(list);

        // RestAfterBracket
        list = new ArrayList<Symbol>();
        list.add(Token.BRA_ROUND_END);
        rulesMap.put(Nonterminal.RestAfterBracket, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.RestAfterBracket).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.PUNC_COMMA);
        list.add(Nonterminal.Exp);
        list.add(Token.BRA_ROUND_END);
        rulesMap.get(Nonterminal.RestAfterBracket).add(list);

        // MoreOp21
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op21);
        list.add(Nonterminal.Exp);
        rulesMap.put(Nonterminal.MoreOp21, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.MoreOp21).add(list);

        list = new ArrayList<Symbol>();
        list.add(null);
        rulesMap.get(Nonterminal.MoreOp21).add(list);

        // MoreOp22
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op22);
        list.add(Nonterminal.E1);
        rulesMap.put(Nonterminal.MoreOp22, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.MoreOp22).add(list);

        list = new ArrayList<Symbol>();
        list.add(null);
        rulesMap.get(Nonterminal.MoreOp22).add(list);

        // MoreOp23
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op23);
        list.add(Nonterminal.E2);
        rulesMap.put(Nonterminal.MoreOp23, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.MoreOp23).add(list);

        list = new ArrayList<Symbol>();
        list.add(null);
        rulesMap.get(Nonterminal.MoreOp23).add(list);

        // MoreOp24
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op24);
        list.add(Nonterminal.E3);
        rulesMap.put(Nonterminal.MoreOp24, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.MoreOp24).add(list);

        list = new ArrayList<Symbol>();
        list.add(null);
        rulesMap.get(Nonterminal.MoreOp24).add(list);

        // MoreOp25
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Op25);
        list.add(Nonterminal.E4);
        rulesMap.put(Nonterminal.MoreOp25, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.MoreOp25).add(list);

        list = new ArrayList<Symbol>();
        list.add(null);
        rulesMap.get(Nonterminal.MoreOp25).add(list);

        // Op21
        list = new ArrayList<Symbol>();
        list.add(Token.OPERATOR_AND);
        rulesMap.put(Nonterminal.Op21, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.Op21).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.OPERATOR_OR);
        rulesMap.get(Nonterminal.Op21).add(list);

        // Op22
        list = new ArrayList<Symbol>();
        list.add(Token.OPERATOR_EQUAL);
        rulesMap.put(Nonterminal.Op22, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.Op22).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.OPERATOR_NOT_EQUAL);
        rulesMap.get(Nonterminal.Op22).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.OPERATOR_LESS_THAN);
        rulesMap.get(Nonterminal.Op22).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.OPERATOR_GREATER_THAN);
        rulesMap.get(Nonterminal.Op22).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.OPERATOR_LESS_THAN_OR_EQUAL);
        rulesMap.get(Nonterminal.Op22).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.OPERATOR_GREATER_THAN_OR_EQUAL);
        rulesMap.get(Nonterminal.Op22).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.OPERATOR_CONCATENATE);
        rulesMap.get(Nonterminal.Op22).add(list);

        // Op23
        list = new ArrayList<Symbol>();
        list.add(Token.OPERATOR_MODULO);
        rulesMap.put(Nonterminal.Op23, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.Op23).add(list);

        // Op24
        list = new ArrayList<Symbol>();
        list.add(Token.OPERATOR_ADD);
        rulesMap.put(Nonterminal.Op24, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.Op24).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.OPERATOR_SUBTRACT);
        rulesMap.get(Nonterminal.Op24).add(list);

        // Op25
        list = new ArrayList<Symbol>();
        list.add(Token.OPERATOR_MULTIPLY);
        rulesMap.put(Nonterminal.Op25, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.Op25).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.OPERATOR_DIVIDE);
        rulesMap.get(Nonterminal.Op25).add(list);

        // Op1
        list = new ArrayList<Symbol>();
        list.add(Token.OPERATOR_SUBTRACT);
        rulesMap.put(Nonterminal.Op1, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.Op1).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.OPERATOR_NEGATE);
        rulesMap.get(Nonterminal.Op1).add(list);

        // ExpGetterOrFunc
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.FuncCallRest);
        rulesMap.put(Nonterminal.ExpGetterOrFunc, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.ExpGetterOrFunc).add(list);

        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Getter);
        rulesMap.get(Nonterminal.ExpGetterOrFunc).add(list);

        // Getter
        list = new ArrayList<Symbol>();
        list.add(Token.FIELD);
        list.add(Nonterminal.GetterRest);
        list.add(Nonterminal.MoreGetter);
        rulesMap.put(Nonterminal.Getter, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.Getter).add(list);

        list = new ArrayList<Symbol>();
        list.add(null);
        rulesMap.get(Nonterminal.Getter).add(list);

        // GetterRest
        list = new ArrayList<Symbol>();
        list.add(Token.GETTER_HEAD);
        rulesMap.put(Nonterminal.GetterRest, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.GetterRest).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.GETTER_TAIL);
        rulesMap.get(Nonterminal.GetterRest).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.GETTER_FIRST);
        rulesMap.get(Nonterminal.GetterRest).add(list);

        list = new ArrayList<Symbol>();
        list.add(Token.GETTER_SECOND);
        rulesMap.get(Nonterminal.GetterRest).add(list);

        // MoreGetter
        list = new ArrayList<Symbol>();
        list.add(Token.FIELD);
        list.add(Nonterminal.GetterRest);
        list.add(Nonterminal.MoreGetter);
        rulesMap.put(Nonterminal.MoreGetter, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.MoreGetter).add(list);

        list = new ArrayList<Symbol>();
        list.add(null);
        rulesMap.get(Nonterminal.MoreGetter).add(list);

        // FuncCallRest
        list = new ArrayList<Symbol>();
        list.add(Token.BRA_ROUND_START);
        list.add(Nonterminal.CallArgs);
        list.add(Token.BRA_ROUND_END);
        rulesMap.put(Nonterminal.FuncCallRest, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.FuncCallRest).add(list);

        // CallArgs
        list = new ArrayList<Symbol>();
        list.add(Nonterminal.Exp);
        list.add(Nonterminal.MoreActArgs);
        rulesMap.put(Nonterminal.CallArgs, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.CallArgs).add(list);

        list = new ArrayList<Symbol>();
        list.add(null);
        rulesMap.get(Nonterminal.CallArgs).add(list);

        // MoreActArgs
        list = new ArrayList<Symbol>();
        list.add(Token.PUNC_COMMA);
        list.add(Nonterminal.Exp);
        list.add(Nonterminal.MoreActArgs);
        rulesMap.put(Nonterminal.MoreActArgs, new ArrayList<ArrayList<Symbol>>());
        rulesMap.get(Nonterminal.MoreActArgs).add(list);

        list = new ArrayList<Symbol>();
        list.add(null);
        rulesMap.get(Nonterminal.MoreActArgs).add(list);
    }
}
