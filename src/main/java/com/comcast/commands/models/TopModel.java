package com.comcast.commands.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class TopModel {

    @JsonProperty
    private Map<String, TopCommandModel> topStateCommands;

    @JsonProperty
    private String[] topCommandsNationally;
}
