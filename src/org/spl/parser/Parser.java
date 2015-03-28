package org.spl.parser;

import org.spl.common.*;
import org.spl.parser.exception.ParsingException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Parser {

    private final static String SYMBOL_NONTERMINAL = "NONTERMINAL";
    private final static String SYMBOL_TOKEN = "TOKEN";

    private class StackPair {

        private ASTNode m_node;
        private ASTNode m_parent;

        public StackPair(ASTNode node, ASTNode parent) {
            m_node = node;
            m_parent = parent;
        }

        public ASTNode getObject() {
            return m_node;
        }

        public ASTNode getParent() {
            return m_parent;
        }

        @Override
        public String toString() {
            if (m_parent != null) {
                return "<" + m_node.toString() + ", " + m_parent.toString() + ">";
            } else {
                return "<" + m_node.toString() + ", null>";
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

    public ASTNode parse(LinkedList<TokenInfo> tokenList) throws ParsingException {
        Stack<StackPair> stack = new Stack<StackPair>();
        ASTNode root = null;
        stack.push(new StackPair(new ASTNode(Nonterminal.SPL), null)); // Starting nonterminal

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
                    // ElseStmt if the current token or epsilon are included in the current nonterminal's FIRST set
                    if (firstSymbol == null) {
                        ok = true;
                        break;
                    } else if (testFirst(firstSymbol, currentToken) || testFirst(firstSymbol, null)) {
                        ASTNode node = new ASTNode(peek.getObject().getSymbol());

                        // Adds the current nonterminal to the tree
                        if (peek.getParent() != null) {
                            peek.getParent().add(node);
                        } else {
                            root = node;
                        }

                        // Pushes the symbols of the right side of the rule to the stack
                        for (int i = nextSymbols.size() - 1; i >= 0; --i) {
                            Symbol rule = nextSymbols.get(i);
                            stack.push(new StackPair(new ASTNode(rule), node));
                        }

                        ok = true;
                        break;
                    }
                }

                // DEBUG
//                for (Enumeration e = stack.elements(); e.hasMoreElements();) {
//                    System.out.print(((StackPair) e.nextElement()).getObject().toString() + " ");
//                }
//                System.out.println();

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
                            new ASTNode(currentToken, currentToken, currentString,
                                    tokenInfo.getLineNumber(), tokenInfo.getColumnNumber()));
//                    System.out.println(tokenInfo.getLineNumber() + " " + tokenInfo.getColumnNumber());
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
