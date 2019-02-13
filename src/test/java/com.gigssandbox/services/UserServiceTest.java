package com.gigssandbox.services;

import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.AlreadyLoggedInException;
import com.gigssandbox.exceptions.AlreadyRegisteredException;
import com.gigssandbox.exceptions.IncorrectPasswordException;
import com.gigssandbox.exceptions.NotRegisteredException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {
    private UserService userService;
    private String username;
    private String password;
    private User user;
    private Map<String, User> users;

    @Before
    public void setUp() {
        this.username = "oliver_sykes";
        this.password = "poprockisthebest";
        this.user = User.builder()
                .username(username)
                .passwordHash(password.hashCode())
                .build();
        this.users = new HashMap<>();

        this.userService = new UserService(users);
    }

    @Test
    public void shoudAddUserToUsersWhenUserTriesToRegister() throws Exception {
        this.user.setLoggedIn(true);

        userService.registerUser(username, password);

        assertTrue(users.containsKey(username) && users.containsValue(user));
    }

    @Test(expected = AlreadyRegisteredException.class)
    public void shouldThrowWhenUserTriesToRegisterTwice() throws Exception {
        userService.registerUser(username, password);
        userService.registerUser(username, password);
    }

    @Test(expected = NotRegisteredException.class)
    public void shouldThrowWhenUserCredentialsAreNotPresent() throws Exception {
        userService.logUserIn(username, password);
    }

    @Test(expected = IncorrectPasswordException.class)
    public void shouldThrowWhenIncorrectPasswordIsPassed() throws Exception {
        String incorrectPassword = "Pooh";
        userService.registerUser(username, password);

        userService.logUserIn(username, incorrectPassword);
    }

    @Test(expected = AlreadyLoggedInException.class)
    public void shouldThrowWHenUserIsAlreadyLoggedIn() throws Exception {
        user.setLoggedIn(true);
        userService.registerUser(username, password);

        userService.logUserIn(username, password);
    }

    @Test
    public void shouldReturnTrueWhenUserHasSuccessfullyLoggedIn() throws Exception {
        userService.registerUser(username, password);
        userService.logUserOut(username);

        userService.logUserIn(username, password);

        assertTrue(userService.getUser(username).isLoggedIn());
    }

    @Test
    public void shouldReturnUserWhenAppTriesToGetUserFromUsers() throws Exception {
        userService.registerUser(username, password);
        this.user.setLoggedIn(true);

        User actualUser = userService.getUser(username);

        assertEquals(user, actualUser);
    }

    @Test(expected = NotRegisteredException.class)
    public void shouldThrowWhenUnregisteredUserTriesToLogOut() throws Exception {
        userService.logUserOut(username);
    }
}
