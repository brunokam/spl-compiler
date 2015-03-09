package org.spl.scanner;

import com.sun.tools.javac.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;

public class ScannerPostprocessor {

    private final static HashMap<Pair<ScannerToken.Final, ScannerToken.Final>, Pair<ScannerToken.Final, String>> doubleOperators =
            new HashMap<Pair<ScannerToken.Final, ScannerToken.Final>, Pair<ScannerToken.Final, String>>();

    static {
        doubleOperators.put(
                new Pair<ScannerToken.Final, ScannerToken.Final>(ScannerToken.Final.OP_ASSIGN, ScannerToken.Final.OP_ASSIGN),
                new Pair<ScannerToken.Final, String>(ScannerToken.Final.OP_EQUAL, "=="));
        doubleOperators.put(
                new Pair<ScannerToken.Final, ScannerToken.Final>(ScannerToken.Final.OP_NEGATE, ScannerToken.Final.OP_ASSIGN),
                new Pair<ScannerToken.Final, String>(ScannerToken.Final.OP_NOT_EQUAL, "!="));
        doubleOperators.put(
                new Pair<ScannerToken.Final, ScannerToken.Final>(ScannerToken.Final.OP_LESS_THAN, ScannerToken.Final.OP_ASSIGN),
                new Pair<ScannerToken.Final, String>(ScannerToken.Final.OP_LESS_THAN_OR_EQUAL, "<="));
        doubleOperators.put(
                new Pair<ScannerToken.Final, ScannerToken.Final>(ScannerToken.Final.OP_GREATER_THAN, ScannerToken.Final.OP_ASSIGN),
                new Pair<ScannerToken.Final, String>(ScannerToken.Final.OP_GREATER_THAN_OR_EQUAL, ">="));
    }

    public LinkedList<Pair<ScannerToken.Final, String>> run(LinkedList<Pair<ScannerToken.Final, String>> tokenList) {
        LinkedList<Pair<ScannerToken.Final, String>> finalTokenList = new LinkedList<Pair<ScannerToken.Final, String>>();

        for (int i = 1; i <= tokenList.size(); ++i) {
            if (i < tokenList.size()) {
                Pair<ScannerToken.Final, ScannerToken.Final> tokenPair =
                        new Pair<ScannerToken.Final, ScannerToken.Final>(tokenList.get(i - 1).fst, tokenList.get(i).fst);
                if (doubleOperators.containsKey(tokenPair)) {
                    finalTokenList.add(doubleOperators.get(tokenPair));
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
