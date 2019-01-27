package com.gigssandbox;

import com.gigssandbox.IO.Input;
import com.gigssandbox.IO.Output;
import com.gigssandbox.command.Command;
import com.gigssandbox.command.CommandFromStringArray;
import com.gigssandbox.command.CommandType;

class Activity {
    private Input input;
    private Output output;
    private MainService mainService;

    Activity(Input input, Output output) {
        this.input = input;
        this.output = output;
        this.mainService = new MainService();
    }

    void beginReceivingAndProcessingCommands() {
        output.writeStored("help");

        Command currentCommand;
        do {
            currentCommand = new CommandFromStringArray(input.nextStringSplitted()).value();

            mainService.process(currentCommand);

            output.writeStored(mainService.response());

        } while (currentCommand.getType() != CommandType.LOG_OUT);
    }
}
