package com.comcast.commands.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder({"speaker", "command"})
public class CommandModel {

    @JsonProperty
    private String speaker;

    @JsonProperty
    private String command;
}
