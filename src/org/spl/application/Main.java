package org.spl.application;


import org.spl.common.TokenInfo;
import org.spl.parser.Parser;
import org.spl.parser.PrettyPrinter;
import org.spl.parser.exception.ParsingException;
import org.spl.scanner.Scanner;
import org.spl.scanner.exception.ScanningException;
import org.spl.scanner.exception.TokenizationException;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.LinkedList;

public class Main {

    private static String ASTToString(DefaultMutableTreeNode node) {
        if (node.isLeaf()) {
            return "[" + node.getUserObject().toString() + "] ";
        } else {
            String str = "[" + node.getUserObject().toString() + " ";

            // Enumerates over children and go down the tree recursively
            for (Enumeration e = node.children(); e.hasMoreElements(); ) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) e.nextElement();
                str += ASTToString(child);

                if (e.hasMoreElements()) {
                    str += " ";
                }
            }

            return str + "]";
        }
    }

    public static void main(String[] args) {
        try {
            byte[] byteArray = Files.readAllBytes(Paths.get("tests/test_1.spl"));
            LinkedList<TokenInfo> tokenList = Scanner.scan(new String(byteArray));

            Parser parser = new Parser();
            DefaultMutableTreeNode AST = parser.parse(tokenList);

            // Gets string representation of the tree
            String output = ASTToString(AST);
            System.out.println(output);

            PrettyPrinter prettyPrinter = new PrettyPrinter();
            System.out.println(prettyPrinter.run(AST));
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
