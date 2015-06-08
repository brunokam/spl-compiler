package org.spl.typechecker;

import org.spl.common.Nonterminal;
import org.spl.common.structure.FunctionDeclaration;
import org.spl.common.structure.Scope;
import org.spl.typechecker.exception.MissingReturnStatement;

public class ReturnPathChecker {

    private boolean processFunction(Scope scope) {
        FunctionDeclaration functionDeclaration = scope.getFunctionObject();
        if (functionDeclaration != null && functionDeclaration.isPredefined()) {
            return true;
        }

        if (scope.containsReturnStatement()) {
            return true;
        }

        boolean scopeState = false;

        // Iterates over scope's children
        for (Scope child : scope.getChildren()) {
            if (child.getNonterminal() == Nonterminal.IfStmt) {
                int grandChildrenSize = child.getChildren().size();

                if (grandChildrenSize > 0) {
                    Scope lastGrandChild = child.getChildren().get(grandChildrenSize - 1);

                    if (lastGrandChild.getNonterminal() == Nonterminal.ElseStmt) {
                        scopeState = scopeState || (processFunction(child) && processFunction(lastGrandChild));
                    }
                }
            }
        }

        return scopeState;
    }

    private void process(Scope scope) throws MissingReturnStatement {
        // Iterates over scope's children
        for (Scope child : scope.getChildren()) {
            if (child.getNonterminal() == Nonterminal.FuncDecl) {
                FunctionDeclaration childFunctionDeclaration = child.getFunctionObject();

                if (!processFunction(child) && !childFunctionDeclaration.isVoid()) {
                    throw new MissingReturnStatement(childFunctionDeclaration.getIdentifier(),
                            childFunctionDeclaration.getDeclarationNode());
                }
            }
        }
    }

    public void run(Scope globalScope) throws MissingReturnStatement {
        process(globalScope);
    }
}
