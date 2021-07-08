package com.maya.back_end.controllers;

import com.maya.back_end.dto.Record;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

@FunctionalInterface
public interface RecordInputStreamParser {

    public Collection<Record> parse(InputStream inputStream) throws RecordParsingException;

}
