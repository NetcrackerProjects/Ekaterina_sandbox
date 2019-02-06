package com.gigssandbox;

import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.AlreadyLoggedInException;
import com.gigssandbox.exceptions.AlreadyRegisteredException;
import com.gigssandbox.exceptions.IncorrectPasswordException;
import com.gigssandbox.exceptions.NotRegisteredException;
import com.gigssandbox.services.UserService;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {
    private UserService userService;

    @Before
    public void setUp() {
        this.userService = new UserService();
    }

    @Test
    public void shoudAddUserToUsersCollectionWhenUserTriesToRegister() {
        String username = "oliver_sykes";
        char[] password = "poprockisthebest".toCharArray();

        assertDoesNotThrow(() -> userService.registerUser(username, password));

        assertTrue(userService.exists(username));
    }

    @Test
    public void shouldThrowAlreadyRegisteredExceptionWhenUserCredentialsAreAlreadyPresentInUsersMap() {
        String username = "skip";
        char[] password = "the foreplay".toCharArray();
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).passwordHash(Arrays.hashCode(password)).build()));

        assertThrows(AlreadyRegisteredException.class, () -> userService.registerUser(username, password));
    }

    @Test
    public void shouldThrowUserNotRegisteredExceptionWhenUserCredentialsAreNotPresentInUsersMap() {
        String username = "ben_bruice";
        char[] password = "everyday00playing00zeros".toCharArray();

        assertThrows(NotRegisteredException.class, () -> userService.logUserIn(username, password));
    }

    @Test
    public void shouldThrowIncorrectPasswordExceptionWhenUserPassedIncorrectPassword() {
        String username = "Winnie";
        char[] password = "The Pooh".toCharArray();
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).passwordHash(Arrays.hashCode(password)).build()));

        char[] incorrectPassword = "Pooh".toCharArray();

        assertThrows(IncorrectPasswordException.class, () -> userService.logUserIn(username, incorrectPassword));
    }

    @Test
    public void shouldThrowAlreadyLoggedInExceptionWHenUsersLoggedInFieldIsTrue() {
        String username = "Adept";
        char[] password = "Ivory Tower".toCharArray();
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).passwordHash(Arrays.hashCode(password)).loggedIn(true).build()));

        assertThrows(AlreadyLoggedInException.class, () -> userService.logUserIn(username, password));
    }

    @Test
    public void shouldNotThrowAnyExceptionIfUserLoginAndPasswordAreCorrectAndPresentInSystem() {
        String username = "Winnie";
        char[] password = "The Pooh".toCharArray();
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).passwordHash(Arrays.hashCode(password)).build()));

        assertDoesNotThrow(() -> userService.logUserIn(username, password));
    }

    @Test
    public void shouldReturnBobWhenAppTriesToGetBobFromUsersCollection() {
        String username = "Bob";
        User expectedUser = User.builder().username(username).build();
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).build()));

        User actualUser = userService.getUser(username);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void shouldThrowNotRegisteredExceptionWhenUnregisteredUserTriesToLogOut() {
        String username = "aviana";
        Whitebox.setInternalState(userService, "users", Collections.emptyMap());

        assertThrows(NotRegisteredException.class, () -> userService.logUserOut(username));
    }
}
