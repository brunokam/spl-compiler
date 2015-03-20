package org.spl.parser;

import org.spl.common.Nonterminal;
import org.spl.common.Symbol;
import org.spl.common.Token;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class ASTPostprocessor {

    private final static String SYMBOL_NONTERMINAL = "NONTERMINAL";
    private final static String SYMBOL_TOKEN = "TOKEN";

    private void removeTempNonterminals(DefaultMutableTreeNode node) {
        for (Enumeration e = node.children(); e.hasMoreElements(); ) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) e.nextElement();
            removeTempNonterminals(child);
        }

        Symbol nodeSymbol = ((ASTNodeObject) node.getUserObject()).getSymbol();
        // If the node symbol is a temporary nonterminal
        if (nodeSymbol.getType().equals(SYMBOL_NONTERMINAL) &&
                ParserMaps.ASTTempNonterminalList.contains(nodeSymbol)) {
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();

            ArrayList children = Collections.list(node.children());
            int i = parent.getIndex(node);
            for (Object child : children) {
                parent.insert((DefaultMutableTreeNode) child, i++);
            }

            parent.remove(node);
        }
    }

    private void removeListPaths(DefaultMutableTreeNode node) {
        for (Enumeration e = node.children(); e.hasMoreElements(); ) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) e.nextElement();
            removeListPaths(child);
        }

        Symbol nodeSymbol = ((ASTNodeObject) node.getUserObject()).getSymbol();
        if (nodeSymbol.getType().equals(SYMBOL_NONTERMINAL)) {
            // If node is a leaf and a nonterminal at the same time, it should be removed from the tree
            // Else if node is not a leaf and is the only child of its parent, it should be removed from the tree
            if (node.isLeaf()) {
                node.removeFromParent();
            } else if (ParserMaps.ASTPossibleNonterminalList.contains(nodeSymbol) &&
                    node.getParent() != null && node.getParent().getChildCount() == 1) {
                DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();

                ArrayList children = Collections.list(node.children());
                for (Object child : children) {
                    parent.add((DefaultMutableTreeNode) child);
                }

                parent.remove(node);
            }
        }
    }

    private void process(DefaultMutableTreeNode node) {
        ASTNodeObject nodeObject = (ASTNodeObject) node.getUserObject();
        Symbol nodeSymbol = nodeObject.getSymbol();

        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
        ASTNodeObject parentObject = parent != null ? (ASTNodeObject) parent.getUserObject() : null;
        Symbol parentSymbol = parent != null ? parentObject.getSymbol() : null;

        if (parent != null) {
            // Collapse conditionals and types
            if (nodeSymbol.getType().equals(SYMBOL_TOKEN) && ParserMaps.ASTCollapsingTokenList.contains(nodeSymbol)) {
                parentObject.setToken((Token) nodeSymbol);
                parent.remove(node);
                return;
            }

            // Change statement nonterminals to body nonterminals in conditionals
            Token parentToken = parentObject.getToken();
            if (nodeSymbol == Nonterminal.Stmt && ParserMaps.ASTBodySymbolList.contains(parentToken)) {
                nodeObject.setSymbol(Nonterminal.Body);
            }

            // Adds DeclArgs to function with no arguments
            if (nodeSymbol == Nonterminal.FuncDecl && node.getChildCount() == 3) {
                node.insert(new DefaultMutableTreeNode(new ASTNodeObject(Nonterminal.DeclArgs)), 2);
            }
        }

        // 1. Changes E1, ..., E5 to Exp
        // 2. Changes TypeWithoutId to Type
        if (ParserMaps.ASTExpSymbolList.contains(nodeSymbol)) {
            nodeObject.setSymbol(Nonterminal.Exp);
        } else if (nodeSymbol == Nonterminal.TypeWithoutId) {
            nodeObject.setSymbol(Nonterminal.Type);
        }

        // Iterates over children
        ArrayList children = Collections.list(node.children());
        for (Object child : children) {
            process((DefaultMutableTreeNode) child);
        }

        // Tidies up operators
        if (parent != null) {
            Token nodeToken = nodeObject.getToken();

            DefaultMutableTreeNode grandParent = (DefaultMutableTreeNode) parent.getParent();
            ASTNodeObject grandParentObject = grandParent != null ? (ASTNodeObject) grandParent.getUserObject() : null;
            Symbol grandParentSymbol = grandParent != null ? grandParentObject.getSymbol() : null;

            DefaultMutableTreeNode leftSibling = node.getPreviousSibling();
            ASTNodeObject leftSiblingObject = leftSibling != null ? (ASTNodeObject) leftSibling.getUserObject() : null;
            Symbol leftSiblingSymbol = leftSibling != null ? leftSiblingObject.getSymbol() : null;

            if (ParserMaps.ASTOperator2TokenList.contains(nodeToken) &&
                    grandParent != null &&
                    ParserMaps.ASTOperator2ContainerSymbolList.contains(grandParentSymbol)) {
                grandParentObject.setSymbol(Nonterminal.Op2);
                grandParentObject.setToken(nodeToken);
                grandParentObject.setString(nodeObject.getString());
                grandParent.remove(parent);
            } else if (ParserMaps.ASTOperator2TokenList.contains(nodeToken) &&
                    (grandParentSymbol == Nonterminal.Exp || grandParentSymbol == Nonterminal.CallArgs) &&
                    parentSymbol == Nonterminal.Exp) {
                parentObject.setSymbol(Nonterminal.Op2);
                parentObject.setToken(nodeToken);
                parentObject.setString(nodeObject.getString());

                int i = parent.getIndex(node);
                children = Collections.list(node.children());
                for (Object child : children) {
                    parent.insert((DefaultMutableTreeNode) child, i++);
                }

                parent.remove(node);
//                process(parent);
            } else if (node.isLeaf() &&
                    ParserMaps.ASTOperator1TokenList.contains(nodeToken) &&
                    (grandParentSymbol == Nonterminal.Exp || grandParentSymbol == Nonterminal.CallArgs) &&
                    parentSymbol == Nonterminal.Op1) {
                grandParentObject.setSymbol(Nonterminal.Op1);
                grandParentObject.setToken(nodeToken);
                grandParentObject.setString(nodeObject.getString());

                grandParent.remove(parent);
            } else if (ParserMaps.ASTOperator2TokenList.contains(nodeToken) &&
                    parentSymbol == Nonterminal.Exp &&
                    leftSiblingSymbol == Nonterminal.Exp) {
                parentObject.setSymbol(Nonterminal.Op2);
                parentObject.setToken(nodeToken);
                parentObject.setString(nodeObject.getString());

                int i = parent.getIndex(node);
                children = Collections.list(node.children());
                for (Object child : children) {
                    parent.insert((DefaultMutableTreeNode) child, i++);
                }

                parent.remove(node);
//                process(parent);
            } else if ((ParserMaps.ASTLogOperatorTokenList.contains(nodeToken) ||
                    (ParserMaps.ASTCmpOperatorTokenList.contains(nodeToken) && leftSibling != null))
                    && parentSymbol == Nonterminal.Exp) {
                parentObject.setSymbol(Nonterminal.Op2);
                parentObject.setToken(nodeToken);
                parentObject.setString(nodeObject.getString());

                int i = parent.getIndex(node);
                children = Collections.list(node.children());
                for (Object child : children) {
                    parent.insert((DefaultMutableTreeNode) child, i++);
                }

                parent.remove(node);
            }
        }
    }

    private void process2(DefaultMutableTreeNode node) {
        ArrayList children = Collections.list(node.children());

        ASTNodeObject nodeObject = (ASTNodeObject) node.getUserObject();
        Symbol nodeSymbol = nodeObject.getSymbol();

        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
        ASTNodeObject parentObject = parent != null ? (ASTNodeObject) parent.getUserObject() : null;
        Symbol parentSymbol = parent != null ? parentObject.getSymbol() : null;

        // 1. If node is Exp or Stmt and has 2 children, then it is a function call
        // 2. Enriches function call args
        if (parent != null && (nodeSymbol == Nonterminal.Exp || nodeSymbol == Nonterminal.Stmt) && children.size() == 2) {
            DefaultMutableTreeNode leftChild = (DefaultMutableTreeNode) node.getFirstChild();
            ASTNodeObject leftChildObject = leftChild != null ? (ASTNodeObject) leftChild.getUserObject() : null;
            Token leftChildToken = leftChild != null ? leftChildObject.getToken() : null;

            DefaultMutableTreeNode rightChild = (DefaultMutableTreeNode) node.getLastChild();
            ASTNodeObject rightChildObject = rightChild != null ? (ASTNodeObject) rightChild.getUserObject() : null;
            Symbol rightChildSymbol = rightChild != null ? rightChildObject.getSymbol() : null;

            if (leftChildToken == Token.IDENTIFIER && rightChildSymbol == Nonterminal.CallArgs) {
//                DefaultMutableTreeNode newParent = new DefaultMutableTreeNode(new ASTNodeObject(nodeSymbol));
                nodeObject.setSymbol(Nonterminal.FuncCall);

//                int i = parent.getIndex(node);
//                parent.insert(newParent, i);
//                newParent.add(node);
            }
        } else if (parentSymbol == Nonterminal.CallArgs) {
            DefaultMutableTreeNode singleArg = new DefaultMutableTreeNode(new ASTNodeObject(Nonterminal.CallSingleArg));

            int i = parent.getIndex(node);
            parent.insert(singleArg, i);
            singleArg.add(node);
        }

        // Changes all Op1 and Op2 to Exp
//        if (nodeSymbol == Nonterminal.Op1 || nodeSymbol == Nonterminal.Op2) {
//            nodeObject.setSymbol(Nonterminal.Exp);
//        }

        // Moves alone tokens to Exp
        if (node.isLeaf() && parentSymbol == Nonterminal.Exp && parent.getChildCount() == 1) {
            parentObject.setToken(nodeObject.getToken());
            parentObject.setString(nodeObject.getString());
            parent.remove(node);
        }

        // Iterates over children
        children = Collections.list(node.children());
        for (Object child : children) {
            process2((DefaultMutableTreeNode) child);
        }

        if (parent != null) {
            Token nodeToken = nodeObject.getToken();

            // 1. Simplifies Ret
            // 2. Simplifies polymorphic array Type
            // 3. Simplifies basic array Type
            if (nodeSymbol == Nonterminal.Ret) {
                parentObject.setSymbol(Nonterminal.Ret);

                for (Object child : children) {
                    int i = node.getIndex((DefaultMutableTreeNode) child);
                    parent.insert((DefaultMutableTreeNode) child, i);
                }

                parent.remove(node);
            } else if (node.isLeaf() && nodeSymbol == Token.IDENTIFIER && parentSymbol == Nonterminal.Type) {
                parentObject.setToken(Token.IDENTIFIER);
                parentObject.setString(nodeObject.getString());
                parent.remove(node);
            } else if (node.isLeaf() && nodeSymbol == Nonterminal.Type && parentSymbol == Nonterminal.Type &&
                    node.getSiblingCount() == 3) {
                parentObject.setSymbol(Nonterminal.ArrayType);
                parentObject.setToken(ParserMaps.ASTArrayTypeTokenMap.get(nodeObject.getToken()));
                parentObject.setString("[" + nodeObject.getString() + "]");
                parent.removeAllChildren();
            } else if (ParserMaps.ASTMatOperatorTokenList.contains(nodeToken) &&
                    nodeSymbol == Nonterminal.Op2 &&
                    parentSymbol == Nonterminal.Exp) {
                parentObject.setSymbol(Nonterminal.Op2);
                parentObject.setToken(nodeToken);
                parentObject.setString(nodeObject.getString());

                int i = parent.getIndex(node);
                for (Object child : children) {
                    parent.insert((DefaultMutableTreeNode) child, i++);
                }

                parent.remove(node);
            } else if (node.isLeaf() && // Weak
                    nodeSymbol == Token.IDENTIFIER &&
                    parentSymbol == Nonterminal.Stmt &&
                    parent.getChildCount() == 1) {
                parentObject.setSymbol(Nonterminal.FuncCall);
                parent.insert(new DefaultMutableTreeNode(new ASTNodeObject(Nonterminal.CallArgs)), 1);
            } else if (node.isLeaf() &&
                    nodeSymbol == Token.IDENTIFIER &&
                    parentSymbol == Nonterminal.VarDecl &&
                    parent.getIndex(node) == 0) {
                nodeObject.setSymbol(Nonterminal.Type);
                nodeObject.setToken(Token.TYPE_POLYMORPHIC);
            } else if (nodeSymbol == Nonterminal.Type && node.getChildCount() == 2) {
                nodeObject.setSymbol(Nonterminal.TupleType);
            }
        }
    }

    public void run(DefaultMutableTreeNode root) {
        removeTempNonterminals(root);
        removeListPaths(root);
        process(root);
        process2(root);
    }
}
