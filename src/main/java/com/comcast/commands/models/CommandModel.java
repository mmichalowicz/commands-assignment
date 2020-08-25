package com.comcast.commands.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"speaker", "command"})
public class CommandModel {

    @JsonProperty
    private String speaker;

    @JsonProperty
    private String command;
}
