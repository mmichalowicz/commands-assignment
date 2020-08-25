package com.comcast.commands.services.tracking;

import java.time.Instant;

public class CreatedCommand {

    protected String  commandName;
    protected int     count;
    protected Instant startProcessingInstant;
    protected Instant stopProcessingInstant;

    public CreatedCommand(String commandName) {
        this.commandName = commandName;
        this.count = 1;
        Instant now = Instant.now();
        this.startProcessingInstant = now;
        this.stopProcessingInstant = now;
    }

    public String getCommandName() {
        return this.commandName;
    }

    public int getCount() {
        return this.count;
    }

    public void updateStopProcessingTime() {
        this.stopProcessingInstant = Instant.now();
    }

    public void incrementCount() {
        ++this.count;
    }

    public long getStartProcessingEpochSec() {
        return this.startProcessingInstant == null ? -1 : this.startProcessingInstant.getEpochSecond();
    }

    public long getStopProcessingEpochSec() {
        return this.stopProcessingInstant == null ? -1 : this.stopProcessingInstant.getEpochSecond();
    }
}
