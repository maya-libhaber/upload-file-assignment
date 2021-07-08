package com.maya.back_end.service;

import com.maya.back_end.dto.Record;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecordPlainTextLineDeserializerTest {

    @Test
    void deserialize_positive() {

        // GIVEN
        RecordPlainTextLineDeserializer sut = new RecordPlainTextLineDeserializer();

        // WHEN
        /* intentionally empty */

        // INVOKE
        Record actualRecord = sut.deserialize("Y99999999920190210000027337+0000018897");

        // THEN
        assertEquals(actualRecord.getDocType(), "Y");
        Assertions.assertEquals("+", actualRecord.getSign());
        Assertions.assertEquals("Y", actualRecord.getDocType());
        Assertions.assertEquals(999999999, actualRecord.getCompanyID());
        Assertions.assertEquals(27337, actualRecord.getDocID());
        Assertions.assertEquals("20190210", actualRecord.getDate());
        Assertions.assertEquals(18897, actualRecord.getAmount());
    }
}