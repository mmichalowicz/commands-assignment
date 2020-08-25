package com.comcast.commands.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.Map;

@Data
@JsonPropertyOrder({"topStateCommands", "topCommandsNationally"})
public class TopModel {

    @JsonProperty
    private Map<String, TopCommandModel> topStateCommands;

    @JsonProperty
    private String[] topCommandsNationally;
}
