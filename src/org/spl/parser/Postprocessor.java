package org.spl.parser;

import org.spl.scanner.ASTNodeObject;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Enumeration;

public class Postprocessor {

    private final static String SYMBOL_NONTERMINAL = "NONTERMINAL";

    private void traverse(DefaultMutableTreeNode node) {
        Enumeration e = node.children();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) e.nextElement();
            traverse(child);
        }

        if (((ASTNodeObject) node.getUserObject()).getSymbol().getType().equals(SYMBOL_NONTERMINAL)) {
            // If node is a leaf and a nonterminal at the same time, it should be removed from the tree
            if (node.isLeaf()) {
                node.removeFromParent();
            }
        }
    }

    public void run(DefaultMutableTreeNode root) {
        traverse(root);
    }
}
