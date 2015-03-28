package org.spl.application;


import org.spl.common.ASTNode;
import org.spl.common.exception.MissingSourceFileException;
import org.spl.typechecker.TypeChecker;
import org.spl.printer.TypedPrettyPrinter;
import org.spl.binder.Binder;
import org.spl.binder.exception.IncorrectTypeDeclarationException;
import org.spl.binder.exception.MultipleDeclarationException;
import org.spl.binder.exception.UndeclaredUseException;
import org.spl.common.TokenInfo;
import org.spl.parser.Parser;
import org.spl.parser.exception.ParsingException;
import org.spl.scanner.Scanner;
import org.spl.scanner.exception.ScanningException;
import org.spl.scanner.exception.TokenizingException;
import org.spl.typechecker.exception.*;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.LinkedList;

public class Main {

    private static String ASTToString(DefaultMutableTreeNode node) {
        if (node.isLeaf()) {
            return "[" + node.toString() + "] ";
        } else {
            String str = "[" + node.toString() + " ";

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
            if (args.length == 0) {
                throw new MissingSourceFileException();
            }

            byte[] byteArray = Files.readAllBytes(Paths.get(args[0]));

            Scanner scanner = new Scanner();
            LinkedList<TokenInfo> tokenList = scanner.scan(new String(byteArray));
//            for (TokenInfo tokenInfo : tokenList) {
//                System.out.println(tokenInfo.getToken() + " " + tokenInfo.getString());
//            }

            Parser parser = new Parser();
            ASTNode AST = parser.parse(tokenList);

            // Prints string representation of the tree
            String output = ASTToString(AST);
//            System.out.println(output.length());
            System.out.println(output);

            Binder binder = new Binder();
            binder.run(AST);

            TypeChecker typeChecker = new TypeChecker();
            typeChecker.run(AST);

            TypedPrettyPrinter prettyPrinter = new TypedPrettyPrinter();
            System.out.println("####### TYPED PRETTY PRINTER CODE #######");
            System.out.println(prettyPrinter.run(AST));
        } catch (IOException e) {
            System.out.println("Error: no such file: " + e.getMessage());
        } catch (MissingSourceFileException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (ScanningException e) {
            System.out.println(e.getMessage());
        } catch (TokenizingException e) {
            System.out.println(e.getMessage());
        } catch (ParsingException e) {
            System.out.println(e.getMessage());
        } catch (MultipleDeclarationException e) {
            System.out.println(e.getMessage());
        } catch (UndeclaredUseException e) {
            System.out.println(e.getMessage());
        } catch (IncorrectTypeDeclarationException e) {
            System.out.println(e.getMessage());
        } catch (IncorrectTypeException e) {
            System.out.println(e.getMessage());
        } catch (IncorrectReturnTypeException e) {
            System.out.println(e.getMessage());
        } catch (IncorrectFunctionCallArguments e) {
            System.out.println(e.getMessage());
        } catch (MissingReturnStatement e) {
            System.out.println(e.getMessage());
        } catch (ExcessReturnStatement e) {
            System.out.println(e.getMessage());
        }
    }
}
