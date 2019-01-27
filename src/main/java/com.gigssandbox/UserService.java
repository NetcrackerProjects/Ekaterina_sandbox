package com.gigssandbox;

import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.IncorrectPasswordException;
import com.gigssandbox.exceptions.UserIsNotRegisteredException;

import java.util.HashMap;
import java.util.Map;

class UserService {
    private final Map<String, User> users;
    private String currentUsername;

    UserService() {
        this.users = new HashMap<>();
    }

    void registerUser(String username, int passwordHash) {
        users.put(username, User.builder().username(username).passwordHash(passwordHash).build());
        currentUsername = username;
    }

    void logUserIn(String username, int passwordHash) throws UserIsNotRegisteredException, IncorrectPasswordException {
        if (!users.containsKey(username)) {
            throw new UserIsNotRegisteredException();

        } else if (users.get(username).getPasswordHash() != passwordHash) {
            throw new IncorrectPasswordException();
        }

        currentUsername = username;
    }

    User currentUser() {
        return users.get(currentUsername);
    }

    User getUser(String username) {
        return users.get(username);
    }

    boolean exists(String username) {
        return users.containsKey(username);
    }
}
