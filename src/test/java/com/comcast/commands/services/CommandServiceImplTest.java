package com.comcast.commands.services;

import com.comcast.commands.models.CommandModel;
import com.comcast.commands.models.TopModel;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;


public class CommandServiceImplTest {

    @Test
    public void testAddCommandsHappyPath() {

        CommandModel commandModel1State1 = CommandModel.builder().command("command1").speaker("speaker1.state1").build();
        CommandModel commandModel2State1 = CommandModel.builder().command("command1").speaker("speaker1.state1").build();
        CommandModel commandModel3State1 = CommandModel.builder().command("command3").speaker("speaker2.state1").build();
        CommandModel[] commandModelsState1 = new CommandModel[] { commandModel1State1, commandModel2State1, commandModel3State1 };

        CommandModel commandModel1State2 = CommandModel.builder().command("command2").speaker("speaker1.state2").build();
        CommandModel commandModel2State2 = CommandModel.builder().command("command2").speaker("speaker2.state2").build();
        CommandModel commandModel3State2 = CommandModel.builder().command("command2").speaker("speaker4.state2").build();
        CommandModel commandModel4State2 = CommandModel.builder().command("command3").speaker("speaker4.state2").build();
        CommandModel[] commandModelsState2 = new CommandModel[] { commandModel1State2, commandModel2State2, commandModel3State2, commandModel4State2 };

        Map<String, CommandModel[]> commandModelsByState = new HashMap<>();
        commandModelsByState.put("State1", commandModelsState1);
        commandModelsByState.put("State2", commandModelsState2);

        CommandServiceImpl commandService = new CommandServiceImpl();
        TopModel topModel = commandService.addCommands(commandModelsByState);

        Instant now = Instant.now();
        assertThat(topModel.getTopStateCommands()).isNotNull();
        assertThat(topModel.getTopStateCommands().get("State1").getMostFrequentCommand()).isEqualTo("command1");
        assertThat(topModel.getTopStateCommands().get("State1").getStartProcessingTime()).isLessThan(now.getEpochSecond());
        assertThat(topModel.getTopStateCommands().get("State1").getStopProcessingTime()).isLessThan(now.getEpochSecond());
        assertThat(topModel.getTopStateCommands().get("State2").getMostFrequentCommand()).isEqualTo("command2");
        assertThat(topModel.getTopStateCommands().get("State2").getStartProcessingTime()).isLessThan(now.getEpochSecond());
        assertThat(topModel.getTopStateCommands().get("State2").getStopProcessingTime()).isLessThan(now.getEpochSecond());
        assertThat(topModel.getTopCommandsNationally()).isNotNull();
        assertThat(topModel.getTopCommandsNationally().length).isEqualTo(2);
        assertThat(topModel.getTopCommandsNationally()[0]).isEqualTo("command2");
        assertThat(topModel.getTopCommandsNationally()[1]).isEqualTo("command1");
    }

    @Test
    public void testAddCommands_topCommandsSharedAcrossStates() {

        CommandModel commandModel1State1 = CommandModel.builder().command("command1").speaker("speaker1.state1").build();
        CommandModel commandModel2State1 = CommandModel.builder().command("command1").speaker("speaker1.state1").build();
        CommandModel commandModel3State1 = CommandModel.builder().command("command3").speaker("speaker2.state1").build();
        CommandModel[] commandModelsState1 = new CommandModel[] { commandModel1State1, commandModel2State1, commandModel3State1 };

        CommandModel commandModel1State2 = CommandModel.builder().command("command1").speaker("speaker1.state2").build();
        CommandModel commandModel2State2 = CommandModel.builder().command("command1").speaker("speaker2.state2").build();
        CommandModel commandModel3State2 = CommandModel.builder().command("command3").speaker("speaker4.state2").build();
        CommandModel[] commandModelsState2 = new CommandModel[] { commandModel1State2, commandModel2State2, commandModel3State2 };

        Map<String, CommandModel[]> commandModelsByState = new HashMap<>();
        commandModelsByState.put("State1", commandModelsState1);
        commandModelsByState.put("State2", commandModelsState2);

        CommandServiceImpl commandService = new CommandServiceImpl();
        TopModel topModel = commandService.addCommands(commandModelsByState);

        Instant now = Instant.now();
        assertThat(topModel.getTopStateCommands()).isNotNull();
        assertThat(topModel.getTopStateCommands().get("State1").getMostFrequentCommand()).isEqualTo("command1");
        assertThat(topModel.getTopStateCommands().get("State1").getStartProcessingTime()).isLessThan(now.getEpochSecond());
        assertThat(topModel.getTopStateCommands().get("State1").getStopProcessingTime()).isLessThan(now.getEpochSecond());
        assertThat(topModel.getTopStateCommands().get("State2").getMostFrequentCommand()).isEqualTo("command1");
        assertThat(topModel.getTopStateCommands().get("State2").getStartProcessingTime()).isLessThan(now.getEpochSecond());
        assertThat(topModel.getTopStateCommands().get("State2").getStopProcessingTime()).isLessThan(now.getEpochSecond());
        assertThat(topModel.getTopCommandsNationally()).isNotNull();
        assertThat(topModel.getTopCommandsNationally().length).isEqualTo(1);
        assertThat(topModel.getTopCommandsNationally()[0]).isEqualTo("command1");
    }
}
