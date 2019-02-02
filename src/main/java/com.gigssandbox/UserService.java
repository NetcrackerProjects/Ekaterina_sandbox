package com.gigssandbox;

import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.AlreadyLoggedInException;
import com.gigssandbox.exceptions.AlreadyRegisteredException;
import com.gigssandbox.exceptions.IncorrectPasswordException;
import com.gigssandbox.exceptions.NotRegisteredException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class UserService {
    private final Map<String, User> users;

    UserService() {
        this.users = new HashMap<>();
    }

    void registerUser(String username, char[] password) throws AlreadyRegisteredException {
        if (users.containsKey(username)) {
            throw new AlreadyRegisteredException();
        }
        users.put(username, User.builder().username(username).passwordHash(Arrays.hashCode(password)).loggedIn(true).build());
    }

    void logUserIn(String username, char[] password) throws NotRegisteredException, IncorrectPasswordException, AlreadyLoggedInException {
        if (!users.containsKey(username)) {
            throw new NotRegisteredException();

        } else if (users.get(username).getPasswordHash() != Arrays.hashCode(password)) {
            throw new IncorrectPasswordException();

        } else if(users.get(username).isLoggedIn()) {
            throw new AlreadyLoggedInException();

        } else {
            users.get(username).setLoggedIn(true);
        }
    }

    void logUserOut(String username) throws NotRegisteredException {
        if (!users.containsKey(username)) {
            throw new NotRegisteredException();
        }
        users.get(username).setLoggedIn(false);
    }

    User getUser(String username) {
        return users.get(username);
    }

    boolean exists(String username) {
        return users.containsKey(username);
    }
}
