package com.maya.back_end.service;

import com.maya.back_end.dto.Record;

@FunctionalInterface
public interface RecordDeserializer<T> {

    public Record deserialize(T serializedRecord) throws RecordDeserializerException;

}
