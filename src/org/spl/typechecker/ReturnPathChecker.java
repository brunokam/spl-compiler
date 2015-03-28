package org.spl.typechecker;

import org.spl.common.Nonterminal;
import org.spl.common.structure.FunctionObject;
import org.spl.common.structure.ScopeObject;
import org.spl.typechecker.exception.MissingReturnStatement;

public class ReturnPathChecker {

    private boolean processFunction(ScopeObject scopeObject) {
        FunctionObject functionObject = scopeObject.getFunctionObject();
        if (functionObject != null && functionObject.isPredefined()) {
            return true;
        }

        if (scopeObject.containsReturnStatement()) {
            return true;
        }

        boolean scopeState = false;

        // Iterates over scope's children
        for (ScopeObject child : scopeObject.getChildren()) {
            if (child.getNonterminal() == Nonterminal.IfStmt) {
                int grandChildrenSize = child.getChildren().size();

                if (grandChildrenSize > 0) {
                    ScopeObject lastGrandChild = child.getChildren().get(grandChildrenSize - 1);

                    if (lastGrandChild.getNonterminal() == Nonterminal.ElseStmt) {
                        scopeState = scopeState || (processFunction(child) && processFunction(lastGrandChild));
                    }
                }
            }
        }

        return scopeState;
    }

    private void process(ScopeObject scopeObject) throws MissingReturnStatement {
        // Iterates over scope's children
        for (ScopeObject child : scopeObject.getChildren()) {
            if (child.getNonterminal() == Nonterminal.FuncDecl) {
                FunctionObject childFunctionObject = child.getFunctionObject();

                if (!processFunction(child) && !childFunctionObject.isVoid()) {
                    throw new MissingReturnStatement(childFunctionObject.getIdentifier(),
                            childFunctionObject.getDeclarationNode());
                }
            }
        }
    }

    public void run(ScopeObject globalScopeObject) throws MissingReturnStatement {
        process(globalScopeObject);
    }
}
