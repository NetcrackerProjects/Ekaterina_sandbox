package com.gigssandbox;

import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.IncorrectPasswordException;
import com.gigssandbox.exceptions.UserIsNotRegisteredException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.jupiter.api.Assertions;
import org.mockito.internal.util.reflection.Whitebox;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class UserServiceTest {
    private UserService userService;

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Before
    public void setUp() {
        this.userService = new UserService();
    }

    @Test
    public void shoudAddUserToUsersCollectionWhenUserTriesToRegister() {
        String username = "oliver_sykes";
        String password = "poprockisthebest";
        systemInMock.provideLines(username, password);

        userService.registerUser(username, password.hashCode());

        assertTrue(userService.exists(username));
    }

    @Test
    public void shouldThrowUserNotRegisteredExceptionWhenUserCredentialsAreNotPresentInUsersMap() {
        String username = "ben_bruice";
        String password = "everyday00playing00zeros";

        Assertions.assertThrows(UserIsNotRegisteredException.class, () -> userService.logUserIn(username, password.hashCode()));
    }

    @Test
    public void shouldThrowIncorrectPasswordExceptionWhenUserPassedIncorrectPassword() {
        String username = "Winnie";
        String password = "The Pooh";
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).passwordHash(password.hashCode()).build()));

        String incorrectPassword = "Pooh";

        Assertions.assertThrows(IncorrectPasswordException.class, () -> userService.logUserIn(username, incorrectPassword.hashCode()));
    }

    @Test
    public void shouldNotThrowAnyExceptionIfUserLoginAndPasswordAreCorrectAndPresentInSystem() {
        String username = "Winnie";
        String password = "The Pooh";
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).passwordHash(password.hashCode()).build()));

        Assertions.assertDoesNotThrow(() -> userService.logUserIn(username, password.hashCode()));
    }

    @Test
    public void shouldReturnAliceWhenCurrentUserIsAlice() {
        String username = "Alice";
        User expectedCurrentUser = User.builder().username(username).build();
        Whitebox.setInternalState(userService, "currentUsername", username);
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).build()));

        User actualCurrentUser = userService.currentUser();

        assertEquals(expectedCurrentUser, actualCurrentUser);
    }

    @Test
    public void shouldReturnBobWhenAppTriesToGetBobFromUssCollection() {
        String username = "Bob";
        User expectedUser = User.builder().username(username).build();
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).build()));

        User actualUser = userService.getUser(username);

        assertEquals(expectedUser, actualUser);
    }
}
