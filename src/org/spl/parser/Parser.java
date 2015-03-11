package org.spl.parser;

import org.spl.common.Symbol;
import org.spl.common.Token;
import org.spl.common.TokenInfo;
import org.spl.parser.exception.ParsingException;
import org.spl.scanner.ASTNodeObject;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Enumeration;
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

    private String getInputForTreeGenerator(DefaultMutableTreeNode node) {
        if (node.isLeaf()) {
            return "[" + node.getUserObject().toString() + "] ";
        } else {
            String str = "[" + node.getUserObject().toString() + " ";

            for (Enumeration e = node.children(); e.hasMoreElements(); ) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) e.nextElement();
                str += getInputForTreeGenerator(child);

                if (e.hasMoreElements()) {
                    str += " ";
                }
            }

            return str + "]";
        }
    }

    private boolean testFirst(Symbol symbol, Token token) {
        if (symbol.getType().equals(SYMBOL_NONTERMINAL)) {
            return Grammar.firstSetMap.get((Nonterminal) symbol).contains(token);
        } else {
            return symbol == token;
        }
    }

    public void parse(LinkedList<TokenInfo> tokenList) throws ParsingException {
        Stack<StackPair> stack = new Stack<StackPair>();
        DefaultMutableTreeNode root = null; //new DefaultMutableTreeNode(new StackUserObject(Nonterminal.SPL));
        stack.push(new StackPair(new ASTNodeObject(Nonterminal.SPL), null)); // Starting nonterminal

        // Iterates over the set of tokens
        for (TokenInfo tokenInfo : tokenList) {
            Token currentToken = tokenInfo.getToken(); // Currently processed token
            StackPair peek = stack.peek();

            // Iterates until a token is on the peek
            while (!peek.getObject().getSymbol().getType().equals(SYMBOL_TOKEN)) {
                stack.pop();
                boolean ok = false; // If the current nonterminal is a proper one

                for (ArrayList<Symbol> rules : Grammar.rulesMap.get((Nonterminal) peek.getObject().getSymbol())) {
                    Symbol firstRule = rules.get(0);

                    if (firstRule == null) {
                        ok = true;
                        break;
                    } else if (testFirst(firstRule, currentToken) || testFirst(firstRule, null)) {
                        DefaultMutableTreeNode node = new DefaultMutableTreeNode(new ASTNodeObject(peek.getObject().getSymbol()));
                        if (peek.getParent() != null) {
//                            System.out.println(peek.getObject() + " " + rules.toString());
                            peek.getParent().add(node);
                        } else {
                            root = node;
                        }

                        for (int i = rules.size() - 1; i >= 0; --i) {
                            Symbol rule = rules.get(i);
                            stack.push(new StackPair(new ASTNodeObject(rule), node));
                        }

                        ok = true;
                        break;
                    }
                }

                if (!ok) {
                    throw new ParsingException(tokenInfo.getString(), tokenInfo.getLineNumber(),
                            tokenInfo.getColumnNumber(), tokenList);
                }

                peek = stack.peek();
//                System.out.println(stack.toString() + " - \"" + tokenInfo.getString() + "\"\n");
            }

            if (currentToken == peek.getObject().getSymbol()) {
                if (ParserTokenMaps.ASTTokenList.contains(currentToken)) {
                    StackPair last = stack.peek();
                    last.getParent().add(new DefaultMutableTreeNode(new ASTNodeObject(currentToken)));
                }

                stack.pop(); // Removes matched terminal from the stack
            } else {
                throw new ParsingException(tokenInfo.getString(), tokenInfo.getLineNumber(),
                        tokenInfo.getColumnNumber(), tokenList);
            }
        }

        Postprocessor postprocessor = new Postprocessor();
        postprocessor.run(root);

        String output = getInputForTreeGenerator(root);
        System.out.println(output);
    }
}
