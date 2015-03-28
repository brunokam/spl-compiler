package org.spl.scanner;

import javafx.util.Pair;
import org.spl.common.TokenInfo;
import org.spl.common.Token;

import java.util.HashMap;
import java.util.LinkedList;

public class TokenListPostprocessor {

    private final static HashMap<Pair<Token, Token>, TokenInfo> doubleTokens =
            new HashMap<Pair<Token, Token>, TokenInfo>();

    static {
        doubleTokens.put(
                new Pair<Token, Token>(Token.OPERATOR_ASSIGN, Token.OPERATOR_ASSIGN),
                new TokenInfo(Token.OPERATOR_EQUAL, "=="));
        doubleTokens.put(
                new Pair<Token, Token>(Token.OPERATOR_NEGATE, Token.OPERATOR_ASSIGN),
                new TokenInfo(Token.OPERATOR_NOT_EQUAL, "!="));
        doubleTokens.put(
                new Pair<Token, Token>(Token.OPERATOR_LESS_THAN, Token.OPERATOR_ASSIGN),
                new TokenInfo(Token.OPERATOR_LESS_THAN_OR_EQUAL, "<="));
        doubleTokens.put(
                new Pair<Token, Token>(Token.OPERATOR_GREATER_THAN, Token.OPERATOR_ASSIGN),
                new TokenInfo(Token.OPERATOR_GREATER_THAN_OR_EQUAL, ">="));
        doubleTokens.put(
                new Pair<Token, Token>(Token.BRA_SQUARE_START, Token.BRA_SQUARE_END),
                new TokenInfo(Token.EMPTY_ARRAY, "[]"));
    }

    public LinkedList<TokenInfo> run(LinkedList<TokenInfo> tokenList) {
        LinkedList<TokenInfo> finalTokenList = new LinkedList<TokenInfo>();

        for (int i = 1; i <= tokenList.size(); ++i) {
            if (i < tokenList.size()) {
                Pair<Token, Token> tokenPair =
                        new Pair<Token, Token>(tokenList.get(i - 1).getToken(), tokenList.get(i).getToken());
                if (doubleTokens.containsKey(tokenPair)) {
                    // Updates line number and column number and adds double token to the final list
                    TokenInfo tokenInfo = doubleTokens.get(tokenPair);
                    tokenInfo.setLineNumber(tokenList.get(i - 1).getLineNumber());
                    tokenInfo.setColumnNumber(tokenList.get(i - 1).getColumnNumber());

                    finalTokenList.add(tokenInfo);
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
