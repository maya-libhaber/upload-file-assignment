package com.maya.back_end.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Record {

    @JsonProperty
    String docType;

    @JsonProperty
    int companyID;

    @JsonProperty
    String date;

    @JsonProperty
    int docID;

    @JsonProperty
    String sign;

    @JsonProperty
    int amount;
}
