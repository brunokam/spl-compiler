package org.spl.common.exception;

public class MissingSourceFileException extends Exception {

    public MissingSourceFileException() {
        super("Error: missing SPL source file");
    }
}
