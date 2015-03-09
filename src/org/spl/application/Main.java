package org.spl.application;


import com.sun.tools.javac.util.Pair;
import org.spl.scanner.Scanner;
import org.spl.scanner.ScannerToken;
import org.spl.scanner.exception.ScanningException;
import org.spl.scanner.exception.TokenizationException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        // TODO: save result to data structure
        try {
            byte[] byteArray = Files.readAllBytes(Paths.get("tests/test_1.spl"));
            LinkedList<Pair<ScannerToken.Final, String>> tokenList = Scanner.scan(new String(byteArray));

            // DEBUG PRINT
            for (Pair<ScannerToken.Final, String> token : tokenList) {
                System.out.println(token.fst + " " + token.snd);
            }
        } catch (IOException e) {
            System.out.println("Error: no such file: " + e.getMessage());
        } catch (ScanningException e) {
            System.out.println(e.getMessage());
        } catch (TokenizationException e) {
            System.out.println(e.getMessage());
        }
    }
}
