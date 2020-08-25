package com.comcast.commands.services.tracking;

import com.comcast.commands.models.CommandModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class StateCommands {

    private static final Logger LOG = LogManager.getLogger(StateCommands.class);

    protected String                      stateName;
    protected CreatedCommand              topCommand;
    protected Map<String, CreatedCommand> commandsByName;

    public StateCommands(String stateName) {
        this.stateName = stateName;
        this.commandsByName = new HashMap<>();
    }

    public String getStateName() {
        return this.stateName;
    }

    public CreatedCommand getTopCommand() {
        return this.topCommand;
    }

    public void addCommand(CommandModel commandModel) {
        LOG.info("addCommand, commandName({}), commandSpeaker({})", commandModel.getCommand(), commandModel.getSpeaker());
        String commandNameUpper = commandModel.getCommand().toUpperCase();
        CreatedCommand createdCommand = this.commandsByName.get(commandNameUpper);
        if (createdCommand == null) {
            createdCommand = new CreatedCommand(commandModel.getCommand());
            this.commandsByName.put(commandNameUpper, createdCommand);

        } else {
            createdCommand.incrementCount();
            createdCommand.updateStopProcessingTime();
        }
        this.trackTopCommandForState(createdCommand);
    }

    /**
     * Track top command for a particular state.
     * NOTE: I probably would have tracked national and state at the same time if I had to do this again.
     */
    protected void trackTopCommandForState(CreatedCommand createdCommand) {
        if (this.topCommand == null) {
            this.topCommand = createdCommand;
            LOG.info("trackTopCommand, command({}) for state({}) is new, now is top command",
                    () -> createdCommand.getCommandName(), () -> this.stateName);

        } else if (createdCommand.getCommandName().equals(this.topCommand.getCommandName())) {
            LOG.info("Updating processing time for command({})", () -> createdCommand.getCommandName());
            this.topCommand.updateStopProcessingTime();
        }
        else if (createdCommand.getCount() > this.topCommand.getCount()) {
            LOG.info("trackTopCommand, command({}) for state({}) count is greater than top",
                    () -> createdCommand.getCommandName(), () -> this.stateName);
            this.topCommand = createdCommand;
            this.topCommand.updateStopProcessingTime();
        }
    }
}
