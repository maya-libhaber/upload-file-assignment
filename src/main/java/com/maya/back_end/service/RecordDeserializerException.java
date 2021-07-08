package com.maya.back_end.service;

import com.maya.back_end.controllers.RecordParsingException;

public class RecordDeserializerException extends RecordParsingException {

    public RecordDeserializerException(String message, Throwable cause) {
        super(message, cause);
    }

}
