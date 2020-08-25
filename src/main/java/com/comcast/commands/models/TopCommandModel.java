package com.comcast.commands.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder({"mostFrequentCommand", "startProcessingTime", "stopProcessingTime"})
public class TopCommandModel {

    @JsonProperty
    private String mostFrequentCommand;

    @JsonProperty
    private long startProcessingTime;

    @JsonProperty
    private long stopProcessingTime;
}
