package com.gigssandbox;

import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.IncorrectPasswordException;
import com.gigssandbox.exceptions.UserIsNotRegisteredException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class UserService {
    private final Map<String, User> users;

    UserService() {
        this.users = new HashMap<>();
    }

    void registerUser(String username, char[] password) {
        users.put(username, User.builder().username(username).passwordHash(Arrays.hashCode(password)).build());
    }

    void logUserIn(String username, char[] password) throws UserIsNotRegisteredException, IncorrectPasswordException {
        if (!users.containsKey(username)) {
            throw new UserIsNotRegisteredException();

        } else if (users.get(username).getPasswordHash() != Arrays.hashCode(password)) {
            throw new IncorrectPasswordException();
        }
    }

    User getUser(String username) {
        return users.get(username);
    }

    boolean exists(String username) {
        return users.containsKey(username);
    }
}
