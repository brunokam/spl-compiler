package org.spl.typechecker.exception;

public class EmptyException extends Exception {

    public EmptyException() {
        super("Error: unknown exception");
    }
}
