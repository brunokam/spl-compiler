package org.spl.parser;

import com.sun.tools.javac.util.Pair;
import org.spl.common.Nonterminal;
import org.spl.common.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class PrettyPrinter {

    private final static String SYMBOL_TOKEN = "TOKEN";

    private String trimSpace(String string) {
        if (string.length() >= 2 && string.charAt(string.length() - 2) != ' ' && string.charAt(string.length() - 1) == ' ') {
            return string.substring(0, string.length() - 1);
        }
        return string;
    }

    private String print(DefaultMutableTreeNode node, String indent) {
        String string = "";
        ASTNodeObject nodeObject = (ASTNodeObject) node.getUserObject();
        Symbol nodeSymbol = nodeObject.getSymbol();
        ArrayList children = Collections.list(node.children());

        DefaultMutableTreeNode parent = null;
        Symbol parentSymbol = null;
        if (node.getParent() != null) {
            parent = (DefaultMutableTreeNode) node.getParent();
            parentSymbol = ((ASTNodeObject) parent.getUserObject()).getSymbol();
        }

        Symbol grandParentSymbol = null;
        if (parent != null && parent.getParent() != null) {
            grandParentSymbol = ((ASTNodeObject) ((DefaultMutableTreeNode) parent.getParent()).getUserObject()).getSymbol();
        }

        if (ParserTokenMaps.ASTBracketMap.containsKey(nodeSymbol)) {
            if (ParserTokenMaps.ASTBracketMap.get(nodeSymbol).fst.contains(parentSymbol)) {
                string += ParserTokenMaps.ASTBracketMap.get(nodeSymbol).snd.fst;
            }
        }


        String newIndent = indent;
        if (ParserTokenMaps.ASTTabMap.containsKey(nodeSymbol)) {
            if (ParserTokenMaps.ASTTabMap.get(nodeSymbol).contains(parentSymbol)) {
                newIndent += "    ";
                string += newIndent;
            }
        }

        for (int i = 0; i < children.size(); ++i) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) children.get(i);
            ASTNodeObject childObject = (ASTNodeObject) child.getUserObject();
            Symbol childSymbol = childObject.getSymbol();

            string += print(child, newIndent);

            if (i < children.size() - 1 && ParserTokenMaps.ASTComaMap.containsKey(childSymbol)) {
                string += ParserTokenMaps.ASTComaMap.get(childSymbol);
            }

            if (ParserTokenMaps.ASTSpaceMap.containsKey(childSymbol)) {
                if (ParserTokenMaps.ASTSpaceMap.get(childSymbol).contains(nodeSymbol)) {
                    string += " ";
                }
            }

            if (ParserTokenMaps.ASTSemicolonMap.containsKey(childSymbol)) {
                string += ParserTokenMaps.ASTSemicolonMap.get(childSymbol);
            }

            Pair<Symbol, Symbol> pair = new Pair<Symbol, Symbol>(childSymbol, nodeSymbol);
            if (i < children.size() - 1 && ParserTokenMaps.ASTNewLineMap.containsKey(pair)) {
                string += ParserTokenMaps.ASTNewLineMap.get(pair);
            }
        }

        if (nodeSymbol.getType().equals(SYMBOL_TOKEN)) {
            string += nodeObject.getString();
        }

        if (ParserTokenMaps.ASTBracketMap.containsKey(nodeSymbol)) {
            if (ParserTokenMaps.ASTBracketMap.get(nodeSymbol).fst.contains(parentSymbol)) {
                Pair<Symbol, Symbol> pair = new Pair<Symbol, Symbol>(nodeSymbol, parentSymbol);
                if (ParserTokenMaps.ASTNewLineMap.containsKey(pair)) {
                    string += ParserTokenMaps.ASTNewLineMap.get(pair);
                }

                if (ParserTokenMaps.ASTTabMap.containsKey(nodeSymbol)) {
                    if (ParserTokenMaps.ASTTabMap.get(nodeSymbol).contains(parentSymbol)) {
                        string += indent;
                    }
                }

                string = trimSpace(string) + ParserTokenMaps.ASTBracketMap.get(nodeSymbol).snd.snd;
            }
        }

        return string;
    }

    public String run(DefaultMutableTreeNode root) {
        return print(root, "");
    }
}
