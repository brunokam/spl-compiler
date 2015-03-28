package org.spl.common;

import org.spl.common.Symbol;

public enum Nonterminal implements Symbol {

    SPL, MoreDecl, FuncDecl, DeclRest, FuncRest, Body, MoreVarDecl, VarDecl, GlobalVarDecl, MoreFArgs, DeclArgs, DeclSingleArg,
    Type, TypeWithoutId, VarDeclOrStmt, BasicType, PolymorphicType, ArrayType, TupleType, MoreActArgs, CallArgs, CallSingleArg,
    FuncCall, FuncCallRest, MoreGetter, GetterRest, Getter, ExpGetterOrFunc, RestOfStmt, ReturnStmt, E4, E3, E2, E1, Exp, ConditionalExp, E5,
    RestAfterBracket, TupleExp, MoreOp25, MoreOp24, MoreOp23, MoreOp22, MoreOp21, Op25, Op24, Op23, Op22, Op21, Op1, Op2, Stmt,
    IfStmt, ElseStmt, WhileStmt, MoreStmt;

    @Override
    public String getType() {
        return "NONTERMINAL";
    }
}
