package com.gigssandbox;

import com.gigssandbox.command.Command;
import com.gigssandbox.command.CommandFactory;
import com.gigssandbox.command.CommandType;
import com.gigssandbox.io.Input;
import com.gigssandbox.io.Output;
import com.gigssandbox.io.sockets.SocketConnection;
import com.gigssandbox.response.Response;
import com.gigssandbox.response.ResponseFactory;
import com.gigssandbox.services.CommunityService;
import com.gigssandbox.services.GigService;
import com.gigssandbox.services.UserService;

class UserActivity {
    private final Input input;
    private final Output output;
    private final UserCommandHandler userCommandHandler;
    private CommandFactory commandFactory;
    private ResponseFactory responseFactory;

    UserActivity(UserService userService, CommunityService communityService, GigService gigService, SocketConnection socketConnection) {
        this.userCommandHandler = new UserCommandHandler(userService, communityService, gigService);
        this.commandFactory = new CommandFactory();
        this.responseFactory = new ResponseFactory();
        this.output = socketConnection.getOutput();
        this.input = socketConnection.getInput();
    }

    void start() {
        Command currentCommand;

        do {
            currentCommand = commandFactory.create(input.receive());

            Response response = responseFactory.create(userCommandHandler.process(currentCommand));

            output.send(response);

        } while (currentCommand.getType() != CommandType.LOG_OUT);
    }
}
