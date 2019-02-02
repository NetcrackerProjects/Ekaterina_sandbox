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
    public void shouldSetResponseAsRegistrationSuccessWhenLastCommandWasRegistration() {
        Result expectedResult = Result.REGISTRATION_SUCCESS;

        Result actualResult = userCommandHandler.process(new Command(CommandType.REGISTER, Map.of("username", "dark", "password", "skies")));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldSetResponseAsLoginSuccessWhenLastCommandWasLogIn() {
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
    public void shouldSetResponseAsNotRegisteredWhenLastCommandWasLogInAndUserWasNotRegisteredYet() {
        String username = "broken";
        char[] password = "youth".toCharArray();
        Result expectedResult = Result.NOT_REGISTERED;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_IN, Map.of("username", username, "password", String.valueOf(password))));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldSetResponseAsIncorrectPasswordWhanLastCommandWasLogInAndUserEnteredWrongAnswer() {
        String username = "motionless";
        int correctPasswordHash = Arrays.hashCode("in white".toCharArray());
        char[] incorrectPassword = "in black".toCharArray();
        Whitebox.setInternalState(userService, "users", Map.of(username, User.builder().username(username).passwordHash(correctPasswordHash).build()));
        Result expectedResult = Result.INCORRECT_PASSWORD;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_IN, Map.of("username", username, "password", String.valueOf(incorrectPassword))));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldSetResponseAsJoinCommunitySuccessWhenLastCommandWasJoinCommunity() {
        String communityName = "beartooth";
        Result expectedResult = Result.JOIN_COMMUNITY_SUCCESS;
        Map<String, Community> communities = new HashMap<>();
        communities.put(communityName, Community.builder().name(communityName).members(new HashSet<>()).build());
        Whitebox.setInternalState(communityService, "communities", communities);

        Result actualResult = userCommandHandler.process(new Command(CommandType.JOIN_COMMUNITY, Map.of("community_name", communityName)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldSetResponseAsLeaveCommunitySuccessWhenLastCommandWasLeaveCommunity() {
        Result expectedResult = Result.LEAVE_COMMUNITY_SUCCESS;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LEAVE_COMMUNITY, Collections.emptyMap()));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldSetResponseAsLogOutSuccessWhenLastCommandWasLogOut() {
        Result expectedResult = Result.LOG_OUT_SUCCESS;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_OUT, Collections.emptyMap()));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldSetResponseAsUnsupportedWhenEnteredCommandIsUnsupported() {
        Result expectedResult = Result.UNSUPPORTED;

        Result actualResult = userCommandHandler.process(new Command(CommandType.UNSUPPORTED, Collections.emptyMap()));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldSetResponseAsHelpWhenUserHasEnteredHelpCommand() {
        Result expectedResult = Result.HELP;

        Result actualResult = userCommandHandler.process(new Command(CommandType.HELP, Collections.emptyMap()));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldSetResponseAsNotEnoughParametersWhenInputContainsNotAllParameters() {
        Result expectedResult = Result.NOT_ENOUGH_PARAMETERS;

        Result actualResult = userCommandHandler.process(new Command(CommandType.NOT_ENOUGH_PARAMETERS, Collections.emptyMap()));

        assertEquals(expectedResult, actualResult);
    }
}
