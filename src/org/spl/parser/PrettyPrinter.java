package org.spl.parser;

import org.spl.common.Nonterminal;
import org.spl.common.Symbol;
import org.spl.common.Token;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Collections;

public class PrettyPrinter {

    private String print(DefaultMutableTreeNode node, String indent) {
        String str = "";
        String newIndent = indent;

        ASTNodeObject nodeObject = (ASTNodeObject) node.getUserObject();
        Symbol nodeSymbol = nodeObject.getSymbol();
        Token nodeToken = nodeObject.getToken();
        String nodeString = nodeObject.getString();

        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
        ASTNodeObject parentObject = parent != null ? (ASTNodeObject) parent.getUserObject() : null;
        Symbol parentSymbol = parent != null ? parentObject.getSymbol() : null;

        if (ParserMaps.ASTBracketSymbolMap.containsKey(nodeSymbol)) {
            str += ParserMaps.ASTBracketSymbolMap.get(nodeSymbol).getKey();
        }

        if (ParserMaps.ASTSpaceBeforeSymbolList.contains(nodeSymbol) &&
                (nodeSymbol == Nonterminal.Op2 || node.getPreviousSibling() != null)) {
            str += " ";
        }

        if (ParserMaps.ASTFieldTokenList.contains(nodeToken)) {
            str += ".";
        }

        if (nodeSymbol == Nonterminal.Body) {
            newIndent += "    ";
            str += newIndent;
        }

        if (nodeString != null) {
            str += nodeString;
        }

        // Iterates over children
        ArrayList children = Collections.list(node.children());
        for (Object child : children) {
            str += print((DefaultMutableTreeNode) child, newIndent);
        }

        if ((ParserMaps.ASTCommaAfterSymbolList.contains(nodeSymbol) || parentSymbol == Nonterminal.TupleType) &&
                node.getNextSibling() != null) {
            str += ",";
        } else if (ParserMaps.ASTSemicolonAfterSymbolList.contains(nodeSymbol) && parentSymbol == Nonterminal.Body) {
            str += ";";
        }

        if (ParserMaps.ASTNewLineSymbolMap.containsKey(nodeSymbol) &&
                node.getNextSibling() != null &&
                (parentSymbol == Nonterminal.Body || parentSymbol == Nonterminal.SPL)) {
            str += ParserMaps.ASTNewLineSymbolMap.get(nodeSymbol) + newIndent;
        }

        if (ParserMaps.ASTBracketSymbolMap.containsKey(nodeSymbol)) {
            if (nodeSymbol == Nonterminal.Body) {
                str += "\n" + indent;
            }
            str += ParserMaps.ASTBracketSymbolMap.get(nodeSymbol).getValue();
        }

        if ((ParserMaps.ASTSpaceAfterSymbolList.contains(nodeSymbol)) &&
                (nodeSymbol == Nonterminal.Op2 || node.getNextSibling() != null)) {
            str += " ";
        }

        return str;
    }

    public String run(DefaultMutableTreeNode root) {
        return print(root, "");
    }
}
