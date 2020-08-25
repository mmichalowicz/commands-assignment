package com.comcast.commands.services;

import com.comcast.commands.models.CommandModel;
import com.comcast.commands.models.TopCommandModel;
import com.comcast.commands.models.TopModel;
import com.comcast.commands.services.tracking.CreatedCommand;
import com.comcast.commands.services.tracking.StateCommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommandServiceImpl implements ICommandService {

    private static final Logger LOG = LogManager.getLogger(CommandServiceImpl.class);

    public static final long ARTIFICIAL_SLEEP_DELAY_MS = 500L;

    protected Map<String, StateCommands> allStateCommands = new HashMap<>();
    protected Map<String, Integer>       topCommandsNationally = new HashMap<>();

    public synchronized TopModel addCommands(Map<String, CommandModel[]> commands) {
        LOG.info("addCommands called, ({}) commands", () -> commands == null ? "null" : commands.size());
        if (commands == null) {
            throw new RuntimeException("commands cannot be null");
        }

        commands.forEach((state, stateCommands) -> {
            addCommand(state, stateCommands);
        });

        TopModel topModel = new TopModel();
        Map<String, TopCommandModel> topCommands = new HashMap<>();
        this.allStateCommands.forEach((stateName, stateCommands) -> {
            CreatedCommand topCommandForState = stateCommands.getTopCommand();
            String commandName = topCommandForState.getCommandName();
            TopCommandModel topCommandModel = TopCommandModel
                    .builder()
                    .mostFrequentCommand(commandName)
                    .startProcessingTime(topCommandForState.getStartProcessingEpochSec())
                    .stopProcessingTime(topCommandForState.getStopProcessingEpochSec())
                    .build();
            topCommands.put(stateCommands.getStateName(), topCommandModel);
            Integer commandCountNational = this.topCommandsNationally.get(commandName);

            // NOTE: I misunderstood requirements, cannot compute top nationally by only looking at top for state
            int topCount = topCommandForState.getCount();
            if (commandCountNational == null) {
                this.topCommandsNationally.put(commandName, topCount);

            } else {
                this.topCommandsNationally.put(commandName, commandCountNational.intValue() + topCount);
            }
        });
        topModel.setTopStateCommands(topCommands);

        // Tracking top commands nationally was an afterthought and done differently, I would have tracked a little
        // differently. I am spending jirations below sorting when I "should have" kept the top national in sorted order
        // to avoid all this sorting code...
        Map<String, Integer> topCommandNamesMap = this.topCommandsNationally.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        Set<String> topCommandNames = topCommandNamesMap.keySet();
        String[] topCommandNamesArray = new String[topCommandNames.size()];
        topCommandNames.toArray(topCommandNamesArray);
        topModel.setTopCommandsNationally(topCommandNamesArray);

        return topModel;
    }

    protected void addCommand(String stateName, CommandModel[] commandModels) {

        Arrays.stream(commandModels).forEach(commandModel -> {
            LOG.info("addCommand, state({}), commandSpeaker({}), commandName({})",
                    () -> stateName, () -> commandModel.getSpeaker(), () -> commandModel.getCommand());

            String upperStateName = stateName.toUpperCase();
            StateCommands stateCommands = this.allStateCommands.get(upperStateName);
            if (stateCommands == null) {
                stateCommands = new StateCommands(stateName);
                this.allStateCommands.put(upperStateName, stateCommands);
            }
            stateCommands.addCommand(commandModel);
            this.artificialSleep();
        });
    }

    /**
     * Artifically delaying between adding commands to demonstrate that the stopProcessingTime is indeed being updated.
     */
    protected void artificialSleep() {
        try {
            Thread.sleep(ARTIFICIAL_SLEEP_DELAY_MS);
        } catch (InterruptedException e) {
            LOG.error("artificialSleep, interrupted");
        }
    }
}
