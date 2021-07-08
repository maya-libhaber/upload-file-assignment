package com.maya.back_end.service;

import com.maya.back_end.controllers.RecordInputStreamParser;
import com.maya.back_end.controllers.RecordParsingException;
import com.maya.back_end.dto.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.toUnmodifiableList;

@Slf4j
@Component
public class RecordPlainTextFileParser implements RecordInputStreamParser {

    private RecordDeserializer<String> recordDeserializer;

    @Autowired
    public RecordPlainTextFileParser(RecordDeserializer<String> recordDeserializer) {
        this.recordDeserializer = recordDeserializer;
    }

    @Override
    public Collection<Record> parse(InputStream inputStream) throws RecordParsingException {

        try {

            return
            new BufferedReader(
                new InputStreamReader(
                    new BufferedInputStream(
                        Optional.of(inputStream)
                                .orElseGet(() -> {
                                    log.error("Failed to read contents of inputStream null was passed to method call");
                                    return InputStream.nullInputStream();
                                })
                            ), StandardCharsets.UTF_8
                    )

            ).lines()
             .map(recordDeserializer::deserialize)
             .collect(toUnmodifiableList());

        } catch (UncheckedIOException ioe) {
            throw new RecordParsingException("Failed to parse inputStream", ioe);

        }

    }
}
