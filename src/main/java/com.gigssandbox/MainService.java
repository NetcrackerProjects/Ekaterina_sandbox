package com.gigssandbox;

import com.gigssandbox.command.Command;
import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.IncorrectPasswordException;
import com.gigssandbox.exceptions.UserIsNotRegisteredException;

import java.util.Map;

class MainService {
    private String response;
    private UserService userService;
    private CommunityService communityService;

    MainService() {
        this.userService = new UserService();
        this.communityService = new CommunityService();
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
                response = "help";
                break;
            case LOG_OUT:
                response = "log_out";
                break;
            case NOT_ENOUGH_PARAMETERS:
                response = "not_enough_parameters";
                break;
            default:
                response = "unsupported";
                break;
        }
    }

    private void registerUser(String username, int passwordHash) {
        userService.registerUser(username, passwordHash);
        communityService.addUserToDefaultCommunity(userService.getUser(username));
        response = "register";
    }

    private void logUserIn(String username, int passwordHash) {
        try {
            userService.logUserIn(username, passwordHash);
            response = "log_in";

        } catch (UserIsNotRegisteredException e) {
            response = "not_registered";

        } catch (IncorrectPasswordException e) {
            response = "incorrect_password";
        }
    }

    private void addUserToCommunity(User user, String communityName) {
        communityService.addUserToCommunity(user, communityName);
        response = "join_community";
    }

    private void removeUserFromCommunity() {
        communityService.removeUserFromCommunity(userService.currentUser());
    }

    String response() {
        return response;
    }
}
