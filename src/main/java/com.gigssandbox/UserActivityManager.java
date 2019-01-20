package com.gigssandbox;

import com.gigssandbox.IO.OutputHandler;
import com.gigssandbox.IO.InputHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class UserActivityManager {
    private InputHandler inputHandler;
    private OutputHandler outputHandler;
    private UserActivityService userActivityService;

    private String username;

    UserActivityManager(InputHandler inputHandler, OutputHandler outputHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.userActivityService = new UserActivityService();
    }

    void startInteracting() {
        processUserLoggingIn();

        while (true) {
            displayPreloaded("choose_action");

            Command currentCommand = getNextCommand();
            processCommand(currentCommand);

            if (currentCommand == Command.LOG_OUT) {
                break;
            }
        }
    }

    void processCommand(Command command) {
        switch (command) {
            case LOG_OUT:
                userActivityService.logUserOut(username);
                displayOnlyTextResponse(command);
                break;
            case JOIN_COMMUNITY:
                userActivityService.addUserToCommunity(username, getParametersForRequest(command));
                displayPreloaded("joining_community_success");
                break;
            case LEAVE_COMMUNITY:
                userActivityService.removeUserFromCommunity(username);
                displayOnlyTextResponse(command);
                break;
            case GET_GIGS:
                displayResponse(command, userActivityService.getUpcomingGigs());
                break;
            case GET_GIGS_BY_BAND:
                displayResponse(command, userActivityService.getUpcomingGigsByBand(getParametersForRequest(command).get("band_name")));
                break;
            default:
                break;
        }
    }

    void processUserLoggingIn() {
        String response = "";

        displayPreloaded("need_to_log_in");
        Map <String, String> currentParameters = getParametersForRequest(Command.LOG_IN);

        while ((response = userActivityService.logUserIn(currentParameters)).equals("incorrect_password")) {
            displayPreloaded(response);
            currentParameters.put("password_hash", String.valueOf(getStringParameter().hashCode()));
        }
        username = currentParameters.get("username");

        displayPreloaded(response);
    }

    Command getNextCommand() {
        int actionCode = getIntParameter();
        Command currentCommand;

        try {
            currentCommand = Command.values()[actionCode];
        } catch (ArrayIndexOutOfBoundsException e) {
            currentCommand = Command.UNSUPPORTED;
        }
        return currentCommand;
    }

    Map<String, String> getParametersForRequest(Command command) {
        Map<String, String> parameters = new HashMap<>();
        switch (command) {
            case LOG_IN:
                String login = getStringParameter();
                String passwordHash = String.valueOf(getStringParameter().hashCode());
                parameters.put("username", login);
                parameters.put("password_hash", passwordHash);
                break;
            case GET_GIGS_BY_BAND:
                displayPreloaded("enter_band_name");
                String bandName = getStringParameter();
                parameters.put("band_name", bandName);
                break;
            case JOIN_COMMUNITY:
                displayPreloaded("join_community");
                String communityName = getStringParameter();
                parameters.put("community_name", communityName);
                break;
            default:
                break;
        }
        return parameters;
    }

    void displayOnlyTextResponse(Command command) {
        displayPreloaded(command.getProperty());
    }

    void displayResponse(Command command, Collection<String> parameters) {
        displayPreloaded(command.getProperty());
        display(parameters);
    }

    private void display(Collection<?> collection) {
        outputHandler.write(collection);
    }

    private void displayPreloaded(String propertyName) {
        outputHandler.writePreloaded(propertyName);
    }

    private String getStringParameter() {
        return inputHandler.readString();
    }

    private int getIntParameter() {
        return inputHandler.readInt();
    }
}