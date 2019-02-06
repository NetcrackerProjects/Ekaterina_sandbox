package com.gigssandbox;

import com.gigssandbox.IO.Input;
import com.gigssandbox.IO.Output;
import com.gigssandbox.IO.console.ConsoleInput;
import com.gigssandbox.IO.console.ConsoleOutput;
import com.gigssandbox.command.Command;
import com.gigssandbox.command.CommandFactory;
import com.gigssandbox.command.CommandType;
import com.gigssandbox.response.Response;
import com.gigssandbox.response.ResponseFactory;
import com.gigssandbox.services.CommunityService;
import com.gigssandbox.services.UserService;

class UserActivity {
    private final Input input;
    private final Output output;
    private final UserCommandHandler userCommandHandler;

    UserActivity(UserService userService, CommunityService communityService) {
        this.input = new ConsoleInput();
        this.output = new ConsoleOutput();
        this.userCommandHandler = new UserCommandHandler(userService, communityService);
    }

    void start() {

        Command currentCommand;
        do {
            currentCommand = CommandFactory.create((input.receive()));

            Response response = ResponseFactory.create(userCommandHandler.process(currentCommand));

            output.send(response);

        } while (currentCommand.getType() != CommandType.LOG_OUT);
    }
}
