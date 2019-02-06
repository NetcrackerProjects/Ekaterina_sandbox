package com.gigssandbox;

import com.gigssandbox.command.Command;
import com.gigssandbox.exceptions.AlreadyLoggedInException;
import com.gigssandbox.exceptions.AlreadyRegisteredException;
import com.gigssandbox.exceptions.IncorrectPasswordException;
import com.gigssandbox.exceptions.NotRegisteredException;

import com.gigssandbox.services.CommunityService;
import com.gigssandbox.services.UserService;

class UserCommandHandler {
    private UserService userService;
    private CommunityService communityService;
    private String username;

    UserCommandHandler(UserService userService, CommunityService communityService) {
        this.userService = userService;
        this.communityService = communityService;
    }

    Result process(Command command) {
        switch (command.getType()) {
            case REGISTER:
                return registerUser(command.nextParameter(), command.nextParameter().toCharArray());
            case LOG_IN:
                return logUserIn(command.nextParameter(), command.nextParameter().toCharArray());
            case JOIN_COMMUNITY:
                return addUserToCommunity(command.nextParameter());
            case LEAVE_COMMUNITY:
                return removeUserFromCommunity();
            case HELP:
                return Result.HELP;
            case LOG_OUT:
                return logUserOut();
            case NOT_ENOUGH_PARAMETERS:
                return Result.NOT_ENOUGH_PARAMETERS;
            default:
                return Result.UNSUPPORTED;
        }
    }

    private Result registerUser(String username, char[] password) {
        this.username = username;

        try {
            userService.registerUser(username, password);
            communityService.addUserToDefaultCommunity(userService.getUser(username));
            return Result.REGISTRATION_SUCCESS;

        } catch (AlreadyRegisteredException e) {
            return Result.ALREADY_REGISTERED;
        }
    }

    private Result logUserIn(String username, char[] password) {
        this.username = username;

        try {
            userService.logUserIn(username, password);
            return Result.LOG_IN_SUCCESS;

        } catch (NotRegisteredException e) {
            return Result.NOT_REGISTERED;

        } catch (IncorrectPasswordException e) {
            return Result.INCORRECT_PASSWORD;

        } catch (AlreadyLoggedInException e) {
            return Result.ALREADY_LOGGED_IN;
        }
    }

    private Result addUserToCommunity(String communityName) {
        communityService.addUserToCommunity(userService.getUser(username), communityName);
        return Result.JOIN_COMMUNITY_SUCCESS;
    }

    private Result removeUserFromCommunity() {
        communityService.removeUserFromCommunity(userService.getUser(username));
        return Result.LEAVE_COMMUNITY_SUCCESS;
    }

    private Result logUserOut() {
        try {
            userService.logUserOut(username);
            return Result.LOG_OUT_SUCCESS;

        } catch (NotRegisteredException e) {
            return Result.NOT_REGISTERED;
        }
    }
}