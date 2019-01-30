package com.gigssandbox;

import com.gigssandbox.IO.Input;
import com.gigssandbox.IO.Output;
import com.gigssandbox.IO.console.ConsoleInput;
import com.gigssandbox.IO.console.ConsoleOutput;
import com.gigssandbox.command.Command;
import com.gigssandbox.command.CommandFactory;
import com.gigssandbox.command.CommandType;

class UserActivity implements ResponseReceiver {
    private final Input input;
    private final Output output;
    private final UserCommandHandler userCommandHandler;

    UserActivity(UserService userService, CommunityService communityService) {
        this.input = new ConsoleInput();
        this.output = new ConsoleOutput();
        this.userCommandHandler = new UserCommandHandler(userService, communityService, this);
    }

    void start() {
        output.writeStored("help");

        Command currentCommand;
        do {
            currentCommand = CommandFactory.from((input.parametersForCommand()));

            userCommandHandler.process(currentCommand);

        } while (currentCommand.getType() != CommandType.LOG_OUT);
    }

    @Override
    public void receive(Response response) {
        output.write(response);
    }
}
