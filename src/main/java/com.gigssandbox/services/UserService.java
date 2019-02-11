package com.gigssandbox.services;

import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.AlreadyLoggedInException;
import com.gigssandbox.exceptions.AlreadyRegisteredException;
import com.gigssandbox.exceptions.IncorrectPasswordException;
import com.gigssandbox.exceptions.NotRegisteredException;
import java.util.Map;

public class UserService {
    private final Map<String, User> users;

    public UserService(Map<String, User> users) {
        this.users = users;
    }

    public void registerUser(String username, String password) throws AlreadyRegisteredException {
        if (users.containsKey(username)) {
            throw new AlreadyRegisteredException();
        }

        User user = User.builder()
                .username(username)
                .passwordHash(password.hashCode())
                .loggedIn(true)
                .build();

        users.put(username, user);
    }

    public void logUserIn(String username, String password) throws NotRegisteredException, IncorrectPasswordException, AlreadyLoggedInException {
        if (!users.containsKey(username)) {
            throw new NotRegisteredException();

        } else if (users.get(username).getPasswordHash() != password.hashCode()) {
            throw new IncorrectPasswordException();

        } else if (users.get(username).isLoggedIn()) {
            throw new AlreadyLoggedInException();
        }

        users.get(username).setLoggedIn(true);
    }

    public void logUserOut(String username) throws NotRegisteredException {
        if (!users.containsKey(username)) {
            throw new NotRegisteredException();
        }
        users.get(username).setLoggedIn(false);
    }

    public User getUser(String username) {
        return users.get(username);
    }

    boolean exists(String username) {
        return users.containsKey(username);
    }
}
