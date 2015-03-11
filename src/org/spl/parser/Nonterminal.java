package org.spl.parser;

import org.spl.common.Symbol;

public enum Nonterminal implements Symbol {

    SPL, MoreDecl, Decl, DeclRest, FuncRest, MoreVarDecl, VarDecl, MoreFArgs, FArgs, Type, TypeWithoutId, VarDeclOrStmt,
    BasicType, MoreActArgs, ActArgs, FunCallRest, MoreField, FieldRest, Field, ExpFieldOrFunc, RestOfStmt, Ret, E4, E3,
    E2, E1, Exp, E5, MoreOp25, MoreOp24, MoreOp23, MoreOp22, MoreOp21, Op25, Op24, Op23, Op22, Op21, Op1, Stmt, Else,
    MoreStmt;

    @Override
    public String getType() {
        return "NONTERMINAL";
    }
}
