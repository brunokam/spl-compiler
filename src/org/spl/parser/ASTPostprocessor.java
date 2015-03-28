package org.spl.parser;

import org.spl.common.ASTNode;
import org.spl.common.Nonterminal;
import org.spl.common.Symbol;
import org.spl.common.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class ASTPostprocessor {

    private final static String SYMBOL_NONTERMINAL = "NONTERMINAL";
    private final static String SYMBOL_TOKEN = "TOKEN";

    private void removeTempNonterminals(ASTNode node) {
        for (Enumeration e = node.children(); e.hasMoreElements(); ) {
            ASTNode child = (ASTNode) e.nextElement();
            removeTempNonterminals(child);
        }

        Symbol nodeSymbol = node.getSymbol();
        // If the node symbol is a temporary nonterminal
        if (nodeSymbol.getType().equals(SYMBOL_NONTERMINAL) &&
                ParserMaps.ASTTempNonterminalList.contains(nodeSymbol)) {
            ASTNode parent = (ASTNode) node.getParent();

            ArrayList children = Collections.list(node.children());
            int i = parent.getIndex(node);
            for (Object child : children) {
                parent.insert((ASTNode) child, i++);
            }

            parent.remove(node);
        }
    }

    private void removeListPaths(ASTNode node) {
        for (Enumeration e = node.children(); e.hasMoreElements(); ) {
            ASTNode child = (ASTNode) e.nextElement();
            removeListPaths(child);
        }

        Symbol nodeSymbol = node.getSymbol();
        if (nodeSymbol instanceof Nonterminal) {
            // If node is a leaf and a nonterminal at the same time, it should be removed from the tree
            // ElseStmt if node is not a leaf and is the only child of its parent, it should be removed from the tree
            if (node.isLeaf()) {
                node.removeFromParent();
            } else if (ParserMaps.ASTPossibleNonterminalList.contains(nodeSymbol) &&
                    node.getParent() != null && node.getParent().getChildCount() == 1) {
                ASTNode parent = (ASTNode) node.getParent();

                ArrayList children = Collections.list(node.children());
                for (Object child : children) {
                    parent.add((ASTNode) child);
                }

                parent.remove(node);
            }
        }
    }

    private void process(ASTNode node) {
        Symbol nodeSymbol = node.getSymbol();
        Token nodeToken = node.getToken();

        ASTNode parent = (ASTNode) node.getParent();
        Symbol parentSymbol = parent != null ? parent.getSymbol() : null;

        if (parent != null) {
            // Collapse conditionals and types
            if (ParserMaps.ASTCollapsingSymbolList.contains(nodeSymbol) || ParserMaps.ASTCollapsingSymbolList.contains(nodeToken)) {
                parent
                        .setSymbol(nodeSymbol)
                        .setToken(nodeToken)
                        .setString(node.getString())
                        .setLineNumber(node.getLineNumber())
                        .setColumnNumber(node.getColumnNumber());

                int i = parent.getIndex(node);
                for (Enumeration e = node.children(); e.hasMoreElements(); ) {
                    parent.insert((ASTNode) e.nextElement(), i++);
                }

                parent.remove(node);
            }


            // Change statement nonterminals to Body nonterminals in conditionals
            Token parentToken = parent.getToken();
            if (nodeSymbol == Nonterminal.Stmt && ParserMaps.ASTBodySymbolList.contains(parentToken)) {
                node.setSymbol(Nonterminal.Body);
            }

            // Adds DeclArgs to function with no arguments
            if (nodeSymbol == Nonterminal.FuncDecl && node.getChildCount() == 3) {
                node.insert(new ASTNode(Nonterminal.DeclArgs), 2);
            }
        }

        // 1. Changes E1, ..., E5 to Exp
        // 2. Changes TypeWithoutId to Type
        if (ParserMaps.ASTExpSymbolList.contains(nodeSymbol)) {
            node.setSymbol(Nonterminal.Exp);
        } else if (nodeSymbol == Nonterminal.TypeWithoutId) {
            node.setSymbol(Nonterminal.Type);
        }

        // Iterates over children
        ArrayList children = Collections.list(node.children());
        for (Object child : children) {
            process((ASTNode) child);
        }

        // Tidies up operators
        if (parent != null) {
            ASTNode grandParent = (ASTNode) parent.getParent();
            Symbol grandParentSymbol = grandParent != null ? grandParent.getSymbol() : null;

            ASTNode leftSibling = (ASTNode) node.getPreviousSibling();
            Symbol leftSiblingSymbol = leftSibling != null ? leftSibling.getSymbol() : null;

            // 1. Simplifies binary operators
            // 2. Simplifies binary operators
            // 3. Simplifies unary operators
            //
            if (ParserMaps.ASTOperator2TokenList.contains(nodeToken) &&
                    grandParent != null &&
                    ParserMaps.ASTOperator2ContainerSymbolList.contains(grandParentSymbol)) {
                grandParent
                        .setSymbol(Nonterminal.Op2)
                        .setToken(nodeToken)
                        .setString(node.getString())
                        .setLineNumber(node.getLineNumber())
                        .setColumnNumber(node.getColumnNumber());
                grandParent.remove(parent);
            } else if (ParserMaps.ASTOperator2TokenList.contains(nodeToken) &&
                    (grandParentSymbol == Nonterminal.Exp || grandParentSymbol == Nonterminal.CallArgs) &&
                    parentSymbol == Nonterminal.Exp) {
                parent
                        .setSymbol(Nonterminal.Op2)
                        .setToken(nodeToken)
                        .setString(node.getString())
                        .setLineNumber(node.getLineNumber())
                        .setColumnNumber(node.getColumnNumber());

                int i = parent.getIndex(node);
                children = Collections.list(node.children());
                for (Object child : children) {
                    parent.insert((ASTNode) child, i++);
                }

                parent.remove(node);
            } else if (node.isLeaf() &&
                    ParserMaps.ASTOperator1TokenList.contains(nodeToken) &&
                    (grandParentSymbol == Nonterminal.Exp || grandParentSymbol == Nonterminal.CallArgs) &&
                    parentSymbol == Nonterminal.Op1) {
                grandParent
                        .setSymbol(Nonterminal.Op1)
                        .setToken(nodeToken)
                        .setString(node.getString())
                        .setLineNumber(node.getLineNumber())
                        .setColumnNumber(node.getColumnNumber());

                grandParent.remove(parent);
            } else if (ParserMaps.ASTOperator2TokenList.contains(nodeToken) &&
                    parentSymbol == Nonterminal.Exp &&
                    leftSiblingSymbol == Nonterminal.Exp) {
                parent
                        .setSymbol(Nonterminal.Op2)
                        .setToken(nodeToken)
                        .setString(node.getString())
                        .setLineNumber(node.getLineNumber())
                        .setColumnNumber(node.getColumnNumber());

                int i = parent.getIndex(node);
                children = Collections.list(node.children());
                for (Object child : children) {
                    parent.insert((ASTNode) child, i++);
                }

                parent.remove(node);
            } /*else if ((ParserMaps.ASTLogOperatorTokenList.contains(nodeToken) ||
                    (ParserMaps.ASTCmpOperatorTokenList.contains(nodeToken) && leftSibling != null))
                    && parentSymbol == Nonterminal.Exp) {
                parent
                        .setSymbol(Nonterminal.Op2)
                        .setToken(nodeToken)
                        .setString(node.getString())
                        .setLineNumber(node.getLineNumber())
                        .setColumnNumber(node.getColumnNumber());

                int i = parent.getIndex(node);
                children = Collections.list(node.children());
                for (Object child : children) {
                    parent.insert((ASTNode) child, i++);
                }

                parent.remove(node);
            }*/
        }
    }

    private void process2(ASTNode node) {
        ArrayList children = Collections.list(node.children());

        Symbol nodeSymbol = node.getSymbol();

        ASTNode parent = (ASTNode) node.getParent();
        Symbol parentSymbol = parent != null ? parent.getSymbol() : null;

        // 1. If node is Exp or Stmt and has 2 children, then it is a function call
        // 2. Enriches function call args
        if (parent != null && (nodeSymbol == Nonterminal.Exp || nodeSymbol == Nonterminal.Stmt) && children.size() == 2) {
            ASTNode leftChild = (ASTNode) node.getFirstChild();
            Token leftChildToken = leftChild != null ? leftChild.getToken() : null;

            ASTNode rightChild = (ASTNode) node.getLastChild();
            Symbol rightChildSymbol = rightChild != null ? rightChild.getSymbol() : null;

            if (leftChildToken == Token.IDENTIFIER && rightChildSymbol == Nonterminal.CallArgs) {
                node.setSymbol(Nonterminal.FuncCall);
            }
        }

        if (parentSymbol == Nonterminal.CallArgs) {
            ASTNode singleArg = new ASTNode(Nonterminal.CallSingleArg);

            int i = parent.getIndex(node);
            parent.insert(singleArg, i);
            singleArg.add(node);
        }

        // Changes all assignment operators to Op2
        if (nodeSymbol == Token.OPERATOR_ASSIGN) {
            node.setSymbol(Nonterminal.Op2);
        }

        // Moves alone tokens to Exp
        if (node.isLeaf() && parentSymbol == Nonterminal.Exp && parent.getChildCount() == 1) {
            parent
                    .setToken(node.getToken())
                    .setString(node.getString())
                    .setLineNumber(node.getLineNumber())
                    .setColumnNumber(node.getColumnNumber());

            parent.remove(node);
        }

        // Iterates over children
        children = Collections.list(node.children());
        for (Object child : children) {
            process2((ASTNode) child);
        }

        if (parent != null) {
            Token nodeToken = node.getToken();

            // 1. Simplifies ReturnStmt
            // 2. Simplifies polymorphic array Type
            // 3. Simplifies basic array Type
            // 4. Simplifies binary operators
            // 5. Fixes function calls
            // 6. Parses polymorphic Type
            // 7. Parses tuple Type
            // 8. Parses global variable declarations
            if (nodeSymbol == Nonterminal.ReturnStmt && parentSymbol == Nonterminal.Stmt) {
                parent
                        .setSymbol(Nonterminal.ReturnStmt)
                        .setLineNumber(node.getLineNumber())
                        .setColumnNumber(node.getColumnNumber());

                int i = parent.getIndex(node);
                for (Object child : children) {
                    parent.insert((ASTNode) child, i++);
                }

                parent.remove(node);
            } else if (node.isLeaf() && parentSymbol == Nonterminal.BasicType) {
                parent
                        .setSymbol(Nonterminal.BasicType)
                        .setToken(nodeToken)
                        .setString(node.getString())
                        .setLineNumber(node.getLineNumber())
                        .setColumnNumber(node.getColumnNumber());

                parent.remove(node);
            } else if (node.isLeaf() && nodeSymbol == Token.TYPE_VOID) {
                node.setSymbol(Nonterminal.BasicType);
            } else if (node.isLeaf() && ParserMaps.ASTGetterTokenList.contains(nodeToken)) {
                node.setSymbol(Nonterminal.Getter);
            } else if (node.isLeaf() && nodeSymbol == Token.IDENTIFIER && parentSymbol == Nonterminal.Type) {
                parent
                        .setSymbol(Nonterminal.PolymorphicType)
                        .setToken(nodeToken)
                        .setString(node.getString())
                        .setLineNumber(node.getLineNumber())
                        .setColumnNumber(node.getColumnNumber());

                parent.remove(node);
            } else if (ParserMaps.ASTTypeNonterminalList.contains(nodeSymbol) &&
                    parentSymbol == Nonterminal.Type && node.getSiblingCount() == 3) {
                parent
                        .setSymbol(Nonterminal.ArrayType)
                        .setLineNumber(node.getLineNumber())
                        .setColumnNumber(node.getColumnNumber());

                parent.remove(2);
                parent.remove(0);
            } else if (ParserMaps.ASTOperator2TokenList.contains(nodeToken) &&
                    nodeSymbol == Nonterminal.Op2 &&
                    parentSymbol == Nonterminal.Exp) {
                parent
                        .setSymbol(Nonterminal.Op2)
                        .setToken(nodeToken)
                        .setString(node.getString())
                        .setLineNumber(node.getLineNumber())
                        .setColumnNumber(node.getColumnNumber());

                int i = parent.getIndex(node);
                for (Object child : children) {
                    parent.insert((ASTNode) child, i++);
                }

                parent.remove(node);
            } else if (node.isLeaf() && // Weak
                    nodeSymbol == Token.IDENTIFIER &&
                    parentSymbol == Nonterminal.Stmt &&
                    parent.getChildCount() == 1) {
                parent.setSymbol(Nonterminal.FuncCall);
                parent.insert(new ASTNode(Nonterminal.CallArgs), 1);
            } /*else if (node.isLeaf() &&
                    nodeSymbol == Token.IDENTIFIER &&
                    parentSymbol == Nonterminal.VarDecl &&
                    parent.getIndex(node) == 0) {
                node
                        .setSymbol(Nonterminal.Type)
                        .setToken(Token.TYPE_POLYMORPHIC);
            }*/ else if (nodeSymbol == Nonterminal.Type && node.getChildCount() == 2) {
                node.setSymbol(Nonterminal.TupleType);
            } else if (nodeToken == Token.OPERATOR_ASSIGN && parentSymbol == Nonterminal.FuncDecl) {
                parent.setSymbol(Nonterminal.GlobalVarDecl);
            } else if (nodeSymbol == Nonterminal.VarDeclOrStmt) {
                int i = parent.getIndex(node);
                for (Object child : Collections.list(node.children())) {
                    parent.insert((ASTNode) child, i++);
                }

                parent.remove(node);
                parent.setSymbol(Nonterminal.Stmt);

                ASTNode identifier = (ASTNode) parent.getChildAt(0);
                identifier
                        .setSymbol(Token.IDENTIFIER)
                        .setToken(Token.IDENTIFIER);
            } else if (nodeSymbol == Nonterminal.Getter && parentSymbol == Nonterminal.Stmt) {
                ASTNode identifier = (ASTNode) parent.getChildAt(0);

                ASTNode expression = new ASTNode(Nonterminal.Exp);
                expression.add(identifier);
                expression.add(node);

                parent.insert(expression, 0);
            }
        }
    }

    private void process3(ASTNode node) {
        Symbol nodeSymbol = node.getSymbol();
        Token nodeToken = node.getToken();

        // Iterates over children
        ArrayList children = Collections.list(node.children());
        for (Object child : children) {
            process3((ASTNode) child);
        }

        if (ParserMaps.ASTOperator1TokenList.contains(nodeToken) ||
                ParserMaps.ASTOperator2TokenList.contains(nodeToken)) {
            if (ParserMaps.ASTOperator2TokenList.contains(nodeToken) && node.getChildCount() == 2) {
                node.insert(new ASTNode(nodeSymbol, nodeToken, node.getString(), node.getLineNumber(), node.getColumnNumber()), 1);
            } else {
                node.insert(new ASTNode(nodeSymbol, nodeToken, node.getString(), node.getLineNumber(), node.getColumnNumber()), 0);
            }

            node
                    .setSymbol(Nonterminal.Exp)
                    .setToken(null)
                    .setString(null);

            nodeSymbol = Nonterminal.Exp;
        } else if (nodeToken == Token.CONDITIONAL_IF) {
            node.insert(new ASTNode(nodeToken, nodeToken, node.getString(), node.getLineNumber(), node.getColumnNumber()), 0);
            node
                    .setSymbol(Nonterminal.IfStmt)
                    .setToken(null)
                    .setString(null);

            nodeSymbol = Nonterminal.IfStmt;

            // If the conditional statement has empty body
            if (node.getChildCount() == 2) { // Weak
                node.add(new ASTNode(Nonterminal.Body));
            }
        } else if (nodeToken == Token.CONDITIONAL_ELSE) {
            node.insert(new ASTNode(nodeToken, nodeToken, node.getString(), node.getLineNumber(), node.getColumnNumber()), 0);
            node
                    .setSymbol(Nonterminal.ElseStmt)
                    .setToken(null)
                    .setString(null);

            nodeSymbol = Nonterminal.ElseStmt;

            // If the conditional statement has empty body
            if (node.getChildCount() == 1) { // Weak
                node.add(new ASTNode(Nonterminal.Body));
            }
        } else if (nodeToken == Token.CONDITIONAL_WHILE) {
            node.insert(new ASTNode(nodeToken, nodeToken, node.getString(), node.getLineNumber(), node.getColumnNumber()), 0);
            node
                    .setSymbol(Nonterminal.WhileStmt)
                    .setToken(null)
                    .setString(null);

            nodeSymbol = Nonterminal.WhileStmt;

            // If the conditional statement has empty body
            if (node.getChildCount() == 2) { // Weak
                node.add(new ASTNode(Nonterminal.Body));
            }
        } else if (nodeSymbol == Nonterminal.ReturnStmt) {
            ASTNode leftSibling = (ASTNode) node.getPreviousSibling();
            Token leftSiblingToken = leftSibling != null ? leftSibling.getToken() : null;

            if (leftSiblingToken == Token.RETURN) {
                node.insert(leftSibling, 0);
            }
        }

        ASTNode parent = (ASTNode) node.getParent();
        Symbol parentSymbol = parent != null ? parent.getSymbol() : null;
        Token parentToken = parent != null ? parent.getToken() : null;

        if (nodeSymbol == Nonterminal.Exp &&
                (parentToken == Token.CONDITIONAL_IF || parentToken == Token.CONDITIONAL_WHILE)) {
            node.setSymbol(Nonterminal.ConditionalExp);
        } else if (nodeSymbol == Nonterminal.Exp && parentSymbol == Nonterminal.Exp && parent.getChildCount() == 1) {
            children = Collections.list(node.children());
            for (Object child : children) {
                parent.add((ASTNode) child);
            }

            parent.remove(node);
        } else if (nodeSymbol == Nonterminal.RestAfterBracket && parentSymbol == Nonterminal.Exp) {
            int i = parent.getIndex(node);
            children = Collections.list(node.children());
            for (Object child : children) {
                parent.insert((ASTNode) child, i++);
            }

            parent.setSymbol(Nonterminal.TupleExp);
            parent.remove(node);
        } else if (nodeSymbol == Token.IDENTIFIER &&
                parentSymbol == Nonterminal.Stmt &&
                node.getPreviousSibling() != null &&
                node.getNextSibling() != null) {
            ASTNode leftSibling = (ASTNode) node.getPreviousSibling();
            Token leftSiblingToken = leftSibling.getToken();

            ASTNode rightSibling = (ASTNode) node.getNextSibling();
            Token rightSiblingToken = rightSibling.getToken();

            if (leftSiblingToken == Token.IDENTIFIER && rightSiblingToken == Token.OPERATOR_ASSIGN) {
                parent.setSymbol(Nonterminal.VarDecl);
                leftSibling.setSymbol(Nonterminal.PolymorphicType);
            }
        } else if (nodeSymbol == Token.IDENTIFIER &&
                parentSymbol == Nonterminal.FuncDecl) {
            parent
                    .setLineNumber(node.getLineNumber())
                    .setColumnNumber(node.getColumnNumber());
        }
    }

    public void run(ASTNode root) {
        removeTempNonterminals(root);
        removeListPaths(root);
        process(root);
        process2(root);
        process3(root);
    }
}
