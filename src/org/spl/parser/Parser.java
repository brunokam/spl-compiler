package org.spl.parser;

import org.spl.common.Nonterminal;
import org.spl.common.Symbol;
import org.spl.common.Token;
import org.spl.common.TokenInfo;
import org.spl.parser.exception.ParsingException;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Parser {

    private final static String SYMBOL_NONTERMINAL = "NONTERMINAL";
    private final static String SYMBOL_TOKEN = "TOKEN";

    private class StackPair {

        private ASTNodeObject m_nodeObject;
        private DefaultMutableTreeNode m_parent;

        public StackPair(ASTNodeObject nodeObject, DefaultMutableTreeNode parent) {
            m_nodeObject = nodeObject;
            m_parent = parent;
        }

        public ASTNodeObject getObject() {
            return m_nodeObject;
        }

        public DefaultMutableTreeNode getParent() {
            return m_parent;
        }

        @Override
        public String toString() {
            if (m_parent != null) {
                return "<" + m_nodeObject.toString() + ", " + m_parent.toString() + ">";
            } else {
                return "<" + m_nodeObject.toString() + ", null>";
            }
        }
    }

    // Checks whether the symbol's FIRST set contains the token
    private boolean testFirst(Symbol symbol, Token token) {
        if (symbol.getType().equals(SYMBOL_NONTERMINAL)) {
            return Grammar.firstSetMap.get((Nonterminal) symbol).contains(token);
        } else {
            return symbol == token;
        }
    }

    public DefaultMutableTreeNode parse(LinkedList<TokenInfo> tokenList) throws ParsingException {
        Stack<StackPair> stack = new Stack<StackPair>();
        DefaultMutableTreeNode root = null;
        stack.push(new StackPair(new ASTNodeObject(Nonterminal.SPL), null)); // Starting nonterminal

        // Iterates over the set of tokens
        for (TokenInfo tokenInfo : tokenList) {
            Token currentToken = tokenInfo.getToken(); // Currently processed token
            String currentString = tokenInfo.getString();
            StackPair peek = stack.peek();

            // Iterates until a token is on the peek
            while (!peek.getObject().getSymbol().getType().equals(SYMBOL_TOKEN)) {
                stack.pop();
                boolean ok = false; // If the current nonterminal is a proper one

                for (ArrayList<Symbol> nextSymbols : Grammar.rulesMap.get((Nonterminal) peek.getObject().getSymbol())) {
                    Symbol firstSymbol = nextSymbols.get(0);

                    // If the current nonterminal can advance to an epsilon
                    // Else if the current token or epsilon are included in the current nonterminal's FIRST set
                    if (firstSymbol == null) {
                        ok = true;
                        break;
                    } else if (testFirst(firstSymbol, currentToken) || testFirst(firstSymbol, null)) {
                        DefaultMutableTreeNode node = new DefaultMutableTreeNode(new ASTNodeObject(peek.getObject().getSymbol()));

                        // Adds the current nonterminal to the tree
                        if (peek.getParent() != null) {
                            peek.getParent().add(node);
                        } else {
                            root = node;
                        }

                        // Pushes the symbols of the right side of the rule to the stack
                        for (int i = nextSymbols.size() - 1; i >= 0; --i) {
                            Symbol rule = nextSymbols.get(i);
                            stack.push(new StackPair(new ASTNodeObject(rule), node));
                        }

                        ok = true;
                        break;
                    }
                }

                // If the current terminal is not the proper one, an exception is thrown
                if (!ok) {
                    throw new ParsingException(tokenInfo.getString(), tokenInfo.getLineNumber(),
                            tokenInfo.getColumnNumber(), tokenList);
                }

                peek = stack.peek();
            }

            if (currentToken == peek.getObject().getSymbol()) {
                // Adds the current terminal to the tree if applicable
                if (ParserMaps.ASTAllowedTokenList.contains(currentToken)) {
                    StackPair last = stack.peek();
                    last.getParent().add(
                            new DefaultMutableTreeNode(
                                    new ASTNodeObject(currentToken, currentToken, currentString)));
                }

                stack.pop(); // Removes the matched terminal from the stack
            } else {
                throw new ParsingException(tokenInfo.getString(), tokenInfo.getLineNumber(),
                        tokenInfo.getColumnNumber(), tokenList);
            }
        }

        // Apply postprocessing to the tree
        ASTPostprocessor postprocessor = new ASTPostprocessor();
        postprocessor.run(root);

        return root;
    }
}
