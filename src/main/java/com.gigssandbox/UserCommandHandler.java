package com.gigssandbox;

import com.gigssandbox.command.Command;
import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.IncorrectPasswordException;
import com.gigssandbox.exceptions.UserIsNotRegisteredException;

import java.util.Map;

class UserCommandHandler {
    private Response response;
    private UserService userService;
    private CommunityService communityService;
    private ResponseReceiver responseReceiver;

    UserCommandHandler(UserService userService, CommunityService communityService, ResponseReceiver responseReceiver) {
        this.userService = userService;
        this.communityService = communityService;
        this.responseReceiver = responseReceiver;
    }

    void process(Command command) {
        Map<String, String> parameters = command.getParameters();
        switch (command.getType()) {
            case REGISTER:
                registerUser(parameters.get("username"), Integer.parseInt(parameters.get("password_hash")));
                break;
            case LOG_IN:
                logUserIn(parameters.get("username"), Integer.parseInt(parameters.get("password_hash")));
                break;
            case JOIN_COMMUNITY:
                addUserToCommunity(userService.currentUser(), parameters.get("community_name"));
                break;
            case LEAVE_COMMUNITY:
                removeUserFromCommunity();
                break;
            case HELP:
                response = Response.HELP;
                break;
            case LOG_OUT:
                response = Response.LOG_OUT_SUCCESS;
                break;
            case NOT_ENOUGH_PARAMETERS:
                response = Response.NOT_ENOUGH_PARAMETERS;
                break;
            default:
                response = Response.UNSUPPORTED;
                break;
        }
        responseReceiver.receive(response);
    }

    private void registerUser(String username, int passwordHash) {
        userService.registerUser(username, passwordHash);
        communityService.addUserToDefaultCommunity(userService.getUser(username));
        response = Response.REGISTRATION_SUCCESS;
    }

    private void logUserIn(String username, int passwordHash) {
        try {
            userService.logUserIn(username, passwordHash);
            response = Response.LOG_IN_SUCCESS;

        } catch (UserIsNotRegisteredException e) {
            response = Response.NOT_REGISTERED;

        } catch (IncorrectPasswordException e) {
            response = Response.INCORRECT_PASSWORD;
        }
    }

    private void addUserToCommunity(User user, String communityName) {
        communityService.addUserToCommunity(user, communityName);
        response = Response.JOIN_COMMUNITY_SUCCESS;
    }

    private void removeUserFromCommunity() {
        communityService.removeUserFromCommunity(userService.currentUser());
        response = Response.LEAVE_COMMUNITY_SUCCESS;
    }
}
