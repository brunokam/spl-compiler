package org.spl.printer;

import org.spl.common.*;

import java.util.ArrayList;
import java.util.Collections;

public class PrettyPrinter {

    private String print(ASTNode node, String indent) {
        String str = "";
        String newIndent = indent;

        Symbol nodeSymbol = node.getSymbol();
        Token nodeToken = node.getToken();
        String nodeString = node.getString();

        ASTNode parent = (ASTNode) node.getParent();
        Symbol parentSymbol = parent != null ? parent.getSymbol() : null;

        if (PrettyPrinterMaps.ASTNewLineBeforeSymbolMap.containsKey(nodeSymbol) &&
                node.getPreviousSibling() != null &&
                (parentSymbol == Nonterminal.Body || parentSymbol == Nonterminal.SPL)) {
            str += PrettyPrinterMaps.ASTNewLineBeforeSymbolMap.get(nodeSymbol) + newIndent;
        }

        if (PrettyPrinterMaps.ASTBracketSymbolMap.containsKey(nodeSymbol)) {
            str += PrettyPrinterMaps.ASTBracketSymbolMap.get(nodeSymbol).getKey();
        }

        if (PrettyPrinterMaps.ASTSpaceBeforeSymbolList.contains(nodeSymbol) &&
                (nodeSymbol == Nonterminal.Op2 || node.getPreviousSibling() != null)) {
            str += " ";
        }

        if (PrettyPrinterMaps.ASTGetterTokenList.contains(nodeToken)) {
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
            str += print((ASTNode) child, newIndent);
        }

        if (PrettyPrinterMaps.ASTBracketSymbolMap.containsKey(nodeSymbol)) {
            if (nodeSymbol == Nonterminal.Body) {
                str += "\n" + indent;
            }
            str += PrettyPrinterMaps.ASTBracketSymbolMap.get(nodeSymbol).getValue();
        }

        if ((PrettyPrinterMaps.ASTCommaAfterSymbolList.contains(nodeSymbol) ||
                    parentSymbol == Nonterminal.TupleType ||
                    parentSymbol == Nonterminal.TupleExp) &&
                node.getNextSibling() != null) {
            str += ",";
        } else if (PrettyPrinterMaps.ASTSemicolonAfterSymbolList.contains(nodeSymbol) &&
                (parentSymbol == Nonterminal.Body || parentSymbol == Nonterminal.SPL)) {
            str += ";";
        }

        if (PrettyPrinterMaps.ASTNewLineAfterSymbolMap.containsKey(nodeSymbol) &&
                node.getNextSibling() != null &&
                (parentSymbol == Nonterminal.Body || parentSymbol == Nonterminal.SPL)) {
            str += PrettyPrinterMaps.ASTNewLineAfterSymbolMap.get(nodeSymbol) + newIndent;
        }

        if ((PrettyPrinterMaps.ASTSpaceAfterSymbolList.contains(nodeSymbol)) &&
                (nodeSymbol == Nonterminal.Op2 || node.getNextSibling() != null)) {
            str += " ";
        } else if (parentSymbol == Nonterminal.TupleExp && node.getNextSibling() != null) {
            str += " ";
        }

        return str;
    }

    public String run(ASTNode root) {
        return print(root, "");
    }
}
