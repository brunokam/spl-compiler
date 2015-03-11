package org.spl.parser;

import org.spl.common.Nonterminal;
import org.spl.common.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class ASTPostprocessor {

    private final static String SYMBOL_NONTERMINAL = "NONTERMINAL";

    private void removeTempNonterminals(DefaultMutableTreeNode node) {
        for (Enumeration e = node.children(); e.hasMoreElements();) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) e.nextElement();
            removeTempNonterminals(child);
        }

        Symbol nodeSymbol = ((ASTNodeObject) node.getUserObject()).getSymbol();
        // If the node symbol is a temporary nonterminal
        if (nodeSymbol.getType().equals(SYMBOL_NONTERMINAL) &&
                ParserTokenMaps.ASTTempNonterminalList.contains((Nonterminal) nodeSymbol)) {
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
        for (Enumeration e = node.children(); e.hasMoreElements();) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) e.nextElement();
            removeListPaths(child);
        }

        if (((ASTNodeObject) node.getUserObject()).getSymbol().getType().equals(SYMBOL_NONTERMINAL)) {
            // If node is a leaf and a nonterminal at the same time, it should be removed from the tree
            // Else if node is not a leaf and is the only child of its parent, it should be removed from the tree
            if (node.isLeaf()) {
                node.removeFromParent();
            } else if (node.getParent() != null && node.getParent().getChildCount() == 1) {
                DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();

                ArrayList children = Collections.list(node.children());
                for (Object child : children) {
                    parent.add((DefaultMutableTreeNode) child);
                }

                parent.remove(node);
            }
        }
    }

    public void run(DefaultMutableTreeNode root) {
        removeTempNonterminals(root);
        removeListPaths(root);
    }
}
