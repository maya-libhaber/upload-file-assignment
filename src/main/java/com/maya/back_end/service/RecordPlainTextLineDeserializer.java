package com.maya.back_end.service;

import com.maya.back_end.dto.Record;
import org.springframework.stereotype.Component;

@Component
public class RecordPlainTextLineDeserializer implements RecordDeserializer<String> {

    @Override
    public Record deserialize(String line) throws RecordDeserializerException {

        try {

            String docType = line.substring(0,1);
            int companyID = Integer.parseInt(line.substring(1,10));
            String date = line.substring(10,18);
            int docID = Integer.parseInt(line.substring(18,27));
            String sign = line.substring(27,28);
            int amount = Integer.parseInt(line.substring(28,38));

            Record record = new Record(docType, companyID, date, docID, sign, amount);
            return record;

        } catch (Exception ex) {
            throw new RecordDeserializerException("Failed to deserialize line: " + line, ex);

        }
    }

}
