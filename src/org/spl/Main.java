package org.spl;


import com.sun.tools.javac.util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.ListIterator;

public class Main {

    public static void main(String[] args) {
        // TODO: save result to data structure
        try {
            byte[] byteArray = Files.readAllBytes(Paths.get("tests/test_0.spl"));
            LinkedList<Pair<Token, String>> tokenList = Scanner.scan(new String(byteArray));

            // DEBUG
            ListIterator<Pair<Token, String>> it = tokenList.listIterator();
            while (it.hasNext()) {
                Pair<Token, String> token = it.next();
                System.out.println(token.fst + " " + token.snd);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
