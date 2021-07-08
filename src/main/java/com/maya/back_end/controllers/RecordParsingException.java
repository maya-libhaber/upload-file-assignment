package com.maya.back_end.controllers;

public class RecordParsingException extends RuntimeException {

    public RecordParsingException(String message) {
        super(message);
    }

    public RecordParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
