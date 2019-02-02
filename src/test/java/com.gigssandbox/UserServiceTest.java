package com.gigssandbox;

import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.IncorrectPasswordException;
import com.gigssandbox.exceptions.UserIsNotRegisteredException;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.jupiter.api.Assertions;
import org.mockito.internal.util.reflection.Whitebox;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
        char[] password = "poprockisthebest".toCharArray();
        systemInMock.provideLines(username, String.valueOf(password));

        userService.registerUser(username, password);

        assertTrue(userService.exists(username));
    }

    @Test
    public void shouldThrowUserNotRegisteredExceptionWhenUserCredentialsAreNotPresentInUsersMap() {
        String username = "ben_bruice";
        char[] password = "everyday00playing00zeros".toCharArray();

        Assertions.assertThrows(UserIsNotRegisteredException.class, () -> userService.logUserIn(username, password));
    }

    @Test
    public void shouldThrowIncorrectPasswordExceptionWhenUserPassedIncorrectPassword() {
        String username = "Winnie";
        char[] password = "The Pooh".toCharArray();
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).passwordHash(Arrays.hashCode(password)).build()));

        char[] incorrectPassword = "Pooh".toCharArray();

        Assertions.assertThrows(IncorrectPasswordException.class, () -> userService.logUserIn(username, incorrectPassword));
    }

    @Test
    public void shouldNotThrowAnyExceptionIfUserLoginAndPasswordAreCorrectAndPresentInSystem() {
        String username = "Winnie";
        char[] password = "The Pooh".toCharArray();
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).passwordHash(Arrays.hashCode(password)).build()));

        Assertions.assertDoesNotThrow(() -> userService.logUserIn(username, password));
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
