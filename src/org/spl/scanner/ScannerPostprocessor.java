package org.spl.scanner;

import com.sun.tools.javac.util.Pair;
import org.spl.common.TokenInfo;
import org.spl.common.Token;

import java.util.HashMap;
import java.util.LinkedList;

public class ScannerPostprocessor {

    private final static HashMap<Pair<Token, Token>, TokenInfo> doubleTokens =
            new HashMap<Pair<Token, Token>, TokenInfo>();

    static {
        doubleTokens.put(
                new Pair<Token, Token>(Token.OP_ASSIGN, Token.OP_ASSIGN),
                new TokenInfo(Token.OP_EQUAL, "=="));
        doubleTokens.put(
                new Pair<Token, Token>(Token.OP_NEGATE, Token.OP_ASSIGN),
                new TokenInfo(Token.OP_NOT_EQUAL, "!="));
        doubleTokens.put(
                new Pair<Token, Token>(Token.OP_LESS_THAN, Token.OP_ASSIGN),
                new TokenInfo(Token.OP_LESS_THAN_OR_EQUAL, "<="));
        doubleTokens.put(
                new Pair<Token, Token>(Token.OP_GREATER_THAN, Token.OP_ASSIGN),
                new TokenInfo(Token.OP_GREATER_THAN_OR_EQUAL, ">="));
        doubleTokens.put(
                new Pair<Token, Token>(Token.BR_SQUARE_START, Token.BR_SQUARE_END),
                new TokenInfo(Token.BR_SQUARE_BOTH, "[]"));
    }

    public LinkedList<TokenInfo> run(LinkedList<TokenInfo> tokenList) {
        LinkedList<TokenInfo> finalTokenList = new LinkedList<TokenInfo>();

        for (int i = 1; i <= tokenList.size(); ++i) {
            if (i < tokenList.size()) {
                Pair<Token, Token> tokenPair =
                        new Pair<Token, Token>(tokenList.get(i - 1).getToken(), tokenList.get(i).getToken());
                if (doubleTokens.containsKey(tokenPair)) {
                    finalTokenList.add(doubleTokens.get(tokenPair));
                    ++i;
                } else {
                    finalTokenList.add(tokenList.get(i - 1));
                }
            } else {
                finalTokenList.add(tokenList.get(i - 1));
            }
        }

        return finalTokenList;
    }
}
