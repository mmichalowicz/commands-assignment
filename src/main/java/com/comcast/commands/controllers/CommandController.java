package com.comcast.commands.controllers;

import com.comcast.commands.models.CommandModel;
import com.comcast.commands.models.TopModel;
import com.comcast.commands.services.ICommandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.MediaType;

import java.util.Map;

@RestController
@RequestMapping(value = "/command", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CommandController {

    private static final Logger LOG = LogManager.getLogger(CommandController.class);

    protected ICommandService commandService;

    @Autowired
    public CommandController(ICommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TopModel> createCommands(@RequestBody Map<String, CommandModel[]> commands) {
        LOG.info("createCommands POST called");

        TopModel topModel = commandService.addCommands(commands);
        return ResponseEntity.ok(topModel);
    }
}
