package org.spl.parser;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class ASTPostprocessor {

    private final static String SYMBOL_NONTERMINAL = "NONTERMINAL";

    private void traverse(DefaultMutableTreeNode node) {
        Enumeration e = node.children();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) e.nextElement();
            traverse(child);
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
        traverse(root);
    }
}
