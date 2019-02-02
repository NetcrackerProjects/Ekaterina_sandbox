package com.gigssandbox;

import com.gigssandbox.command.Command;
import com.gigssandbox.command.CommandType;
import com.gigssandbox.entities.Community;
import com.gigssandbox.entities.User;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class UserCommandHandlerTest {
    private UserCommandHandler userCommandHandler;
    private UserService userService;
    private CommunityService communityService;

    @Before
    public void setUp() {
        this.userService = new UserService();
        this.communityService = new CommunityService();
        this.userCommandHandler = new UserCommandHandler(userService, communityService);
    }

    @Test
    public void shouldReturnRegistrationSuccessResultWhenLastCommandWasRegistration() {
        Result expectedResult = Result.REGISTRATION_SUCCESS;

        Result actualResult = userCommandHandler.process(new Command(CommandType.REGISTER, Map.of("username", "dark", "password", "skies")));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnLoginSuccessResultWhenLastCommandWasLogIn() {
        String username = "nihilist";
        char[] password = "blues".toCharArray();
        Result expectedResult = Result.LOG_IN_SUCCESS;
        Map<String, User> users = new HashMap<>();
        users.put(username, User.builder().username(username).passwordHash(Arrays.hashCode(password)).build());
        Whitebox.setInternalState(userService, "users", users);

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_IN, Map.of("username", username, "password", String.valueOf(password))));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNotRegisteredResultWhenLastCommandWasLogInAndUserWasNotRegisteredYet() {
        String username = "broken";
        char[] password = "youth".toCharArray();
        Result expectedResult = Result.NOT_REGISTERED;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_IN, Map.of("username", username, "password", String.valueOf(password))));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnIncorrectPasswordResultWhanLastCommandWasLogInAndUserEnteredWrongAnswer() {
        String username = "motionless";
        int correctPasswordHash = Arrays.hashCode("in white".toCharArray());
        char[] incorrectPassword = "in black".toCharArray();
        Whitebox.setInternalState(userService, "users", Map.of(username, User.builder().username(username).passwordHash(correctPasswordHash).build()));
        Result expectedResult = Result.INCORRECT_PASSWORD;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_IN, Map.of("username", username, "password", String.valueOf(incorrectPassword))));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnJoinCommunitySuccessResultWhenLastCommandWasJoinCommunity() {
        String communityName = "beartooth";
        Result expectedResult = Result.JOIN_COMMUNITY_SUCCESS;
        Map<String, Community> communities = new HashMap<>();
        communities.put(communityName, Community.builder().name(communityName).members(new HashSet<>()).build());
        Whitebox.setInternalState(communityService, "communities", communities);

        Result actualResult = userCommandHandler.process(new Command(CommandType.JOIN_COMMUNITY, Map.of("community_name", communityName)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnLeaveCommunitySuccessResultWhenLastCommandWasLeaveCommunity() {
        Result expectedResult = Result.LEAVE_COMMUNITY_SUCCESS;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LEAVE_COMMUNITY, Collections.emptyMap()));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnLogOutSuccessResultWhenLastCommandWasLogOut() {
        Result expectedResult = Result.LOG_OUT_SUCCESS;
        Whitebox.setInternalState(userCommandHandler, "username", "Carrot");
        Whitebox.setInternalState(userService, "users", Collections.singletonMap("Carrot", Mockito.mock(User.class)));

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_OUT, Collections.emptyMap()));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnUnsupportedResultWhenEnteredCommandIsUnsupported() {
        Result expectedResult = Result.UNSUPPORTED;

        Result actualResult = userCommandHandler.process(new Command(CommandType.UNSUPPORTED, Collections.emptyMap()));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnHelpResultWhenUserHasEnteredHelpCommand() {
        Result expectedResult = Result.HELP;

        Result actualResult = userCommandHandler.process(new Command(CommandType.HELP, Collections.emptyMap()));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNotEnoughParametersResultWhenInputContainsNotAllParameters() {
        Result expectedResult = Result.NOT_ENOUGH_PARAMETERS;

        Result actualResult = userCommandHandler.process(new Command(CommandType.NOT_ENOUGH_PARAMETERS, Collections.emptyMap()));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnAlreadyLoggedInResultWhenIsLoggedInFieldIsTrue() {
        String username = "Glen";
        char[] password = "Check".toCharArray();
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).passwordHash(Arrays.hashCode(password)).loggedIn(true).build()));
        Result expectredResult = Result.ALREADY_LOGGED_IN;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_IN, Map.of("username", username, "password", String.valueOf(password))));

        assertEquals(expectredResult, actualResult);
    }

    @Test
    public void shouldReturnAlreadyRegisteredResultWhenUserIsPresentInUsersMap() {
        String username = "John";
        char[] password = "Senya MC".toCharArray();
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).passwordHash(Arrays.hashCode(password)).build()));
        Result expectedResult = Result.ALREADY_REGISTERED;

        Result actualResult = userCommandHandler.process(new Command(CommandType.REGISTER, Map.of("username", username, "password", String.valueOf(password))));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNotRegisteredResultWhenUnregisteredUserTriesToLogOut() {
        String username = "obelisk";
        Whitebox.setInternalState(userService, "users", Collections.emptyMap());
        Result expectedResult = Result.NOT_REGISTERED;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_OUT, Collections.emptyMap()));

        assertEquals(expectedResult, actualResult);
    }
}
