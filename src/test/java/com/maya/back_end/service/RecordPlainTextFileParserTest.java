package com.maya.back_end.service;

import com.maya.back_end.dto.Record;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecordPlainTextFileParserTest {

    @Test
    void parse_positive() throws IOException {

        // GIVEN
        RecordDeserializer<String> recordDeserializer = new RecordPlainTextLineDeserializer();

        ByteArrayInputStream inputStreamMock =
                new ByteArrayInputStream("Y99999999920190210000027337+0000018897".getBytes());

        RecordPlainTextFileParser sut =
                new RecordPlainTextFileParser(recordDeserializer);

        // WHEN
        /* intentionally empty */

        // INVOKE
        Collection<Record> actualRecords = sut.parse(inputStreamMock);

        // THEN
        Assertions.assertFalse(actualRecords.isEmpty(), "actualRecords should not be empty");
        Record actualRecord = actualRecords.iterator().next();
        Assertions.assertEquals("+", actualRecord.getSign());
        Assertions.assertEquals("Y", actualRecord.getDocType());
        Assertions.assertEquals(999999999, actualRecord.getCompanyID());
        Assertions.assertEquals(27337, actualRecord.getDocID());
        Assertions.assertEquals("20190210", actualRecord.getDate());
        Assertions.assertEquals(18897, actualRecord.getAmount());
    }

}