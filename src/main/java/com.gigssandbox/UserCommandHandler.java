package com.gigssandbox;

import com.gigssandbox.command.Command;
import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.IncorrectPasswordException;
import com.gigssandbox.exceptions.UserIsNotRegisteredException;

import java.util.Map;

class UserCommandHandler {
    private UserService userService;
    private CommunityService communityService;
    private String username;

    UserCommandHandler(UserService userService, CommunityService communityService) {
        this.userService = userService;
        this.communityService = communityService;
    }

    Result process(Command command) {
        Map<String, String> parameters = command.getParameters();
        switch (command.getType()) {
            case REGISTER:
                return registerUser(parameters.get("username"), parameters.get("password").toCharArray());
            case LOG_IN:
                return logUserIn(parameters.get("username"), parameters.get("password").toCharArray());
            case JOIN_COMMUNITY:
                return addUserToCommunity(userService.getUser(username), parameters.get("community_name"));
            case LEAVE_COMMUNITY:
                return removeUserFromCommunity();
            case HELP:
                return Result.HELP;
            case LOG_OUT:
                return Result.LOG_OUT_SUCCESS;
            case NOT_ENOUGH_PARAMETERS:
                return Result.NOT_ENOUGH_PARAMETERS;
            default:
                return Result.UNSUPPORTED;
        }
    }

    private Result registerUser(String username, char[] password) {
        this.username = username;

        userService.registerUser(username, password);
        communityService.addUserToDefaultCommunity(userService.getUser(username));

        return Result.REGISTRATION_SUCCESS;
    }

    private Result logUserIn(String username, char[] password) {
        this.username = username;

        try {
            userService.logUserIn(username, password);
            return Result.LOG_IN_SUCCESS;

        } catch (UserIsNotRegisteredException e) {
            return Result.NOT_REGISTERED;

        } catch (IncorrectPasswordException e) {
            return Result.INCORRECT_PASSWORD;
        }
    }

    private Result addUserToCommunity(User user, String communityName) {
        communityService.addUserToCommunity(user, communityName);
        return Result.JOIN_COMMUNITY_SUCCESS;
    }

    private Result removeUserFromCommunity() {
        communityService.removeUserFromCommunity(userService.getUser(username));
        return Result.LEAVE_COMMUNITY_SUCCESS;
    }
}