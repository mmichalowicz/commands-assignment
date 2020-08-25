package com.comcast.commands.services;

import com.comcast.commands.models.CommandModel;
import com.comcast.commands.models.TopModel;

import java.util.Map;

public interface ICommandService {
    TopModel addCommands(Map<String, CommandModel[]> commands);
}
