package org.spl.application;


import org.spl.common.TokenInfo;
import org.spl.parser.Parser;
import org.spl.parser.exception.ParsingException;
import org.spl.scanner.Scanner;
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
            LinkedList<TokenInfo> tokenList = Scanner.scan(new String(byteArray));

            // DEBUG PRINT
//            for (TokenInfo token : tokenList) {
//                System.out.println(token.getToken() + " " + token.getString() + " " + token.getLineNumber() + ":" + token.getColumnNumber());
//            }

            Parser parser = new Parser();
            parser.parse(tokenList);
        } catch (IOException e) {
            System.out.println("Error: no such file: " + e.getMessage());
        } catch (ScanningException e) {
            System.out.println(e.getMessage());
        } catch (TokenizationException e) {
            System.out.println(e.getMessage());
        } catch (ParsingException e) {
            System.out.println(e.getMessage());
        }
    }
}
