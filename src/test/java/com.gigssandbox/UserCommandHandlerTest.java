package com.gigssandbox;

import com.gigssandbox.command.Command;
import com.gigssandbox.command.CommandType;
import com.gigssandbox.entities.Community;
import com.gigssandbox.entities.User;
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
    private UserActivity userActivity;

    @Before
    public void setUp() {
        this.userService = new UserService();
        this.communityService = new CommunityService();
        this.userActivity = Mockito.mock(UserActivity.class);
        this.userCommandHandler = new UserCommandHandler(userService, communityService, userActivity);
    }

    @Test
    public void shouldSetResponseAsRegistrationSuccessWhenLastCommandWasRegistration() {
        Response expectedResponse = Response.REGISTRATION_SUCCESS;

        userCommandHandler.process(new Command(CommandType.REGISTER, Map.of("username", "dark", "password_hash", String.valueOf(("skies").hashCode()))));

        assertEquals(expectedResponse, Whitebox.getInternalState(userCommandHandler, "response"));
    }

    @Test
    public void shouldSetResponseAsLoginSuccessWhenLastCommandWasLogIn() {
        String username = "nihilist";
        int passwordHash = "blues".hashCode();
        Response expectedResponse = Response.LOG_IN_SUCCESS;
        Map<String, User> users = new HashMap<>();
        users.put(username, User.builder().username(username).passwordHash(passwordHash).build());
        Whitebox.setInternalState(userService, "users", users);

        userCommandHandler.process(new Command(CommandType.LOG_IN, Map.of("username", username, "password_hash", String.valueOf(passwordHash))));

        assertEquals(expectedResponse, Whitebox.getInternalState(userCommandHandler, "response"));
    }

    @Test
    public void shouldSetResponseAsNotRegisteredWhenLastCommandWasLogInAndUserWasNotRegisteredYet() {
        String username = "broken";
        int passwordHash = "youth".hashCode();
        Response expectedResponse = Response.NOT_REGISTERED;

        userCommandHandler.process(new Command(CommandType.LOG_IN, Map.of("username", username, "password_hash", String.valueOf(passwordHash))));

        assertEquals(expectedResponse, Whitebox.getInternalState(userCommandHandler, "response"));
    }

    @Test
    public void shouldSetResponseAsIncorrectPasswordWhanLastCommandWasLogInAndUserEnteredWrongAnswer() {
        UserService localUserService = new UserService();
        UserCommandHandler localUserCommandHandler = new UserCommandHandler(localUserService, communityService, userActivity);
        String username = "motionless";
        int correctPasswordHash = "in white".hashCode();
        int incorrectPasswordHash = "in black".hashCode();
        Whitebox.setInternalState(localUserService, "users", Map.of(username, User.builder().username(username).passwordHash(correctPasswordHash).build()));
        Response expectedResponse = Response.INCORRECT_PASSWORD;

        localUserCommandHandler.process(new Command(CommandType.LOG_IN, Map.of("username", username, "password_hash", String.valueOf(incorrectPasswordHash))));

        assertEquals(expectedResponse, Whitebox.getInternalState(localUserCommandHandler, "response"));
    }

    @Test
    public void shouldSetResponseAsJoinCommunitySuccessWhenLastCommandWasJoinCommunity() {
        String communityName = "beartooth";
        Response expectedResponse = Response.JOIN_COMMUNITY_SUCCESS;
        Map<String, Community> communities = new HashMap<>();
        communities.put(communityName, Community.builder().name(communityName).members(new HashSet<>()).build());
        Whitebox.setInternalState(communityService, "communities", communities);

        userCommandHandler.process(new Command(CommandType.JOIN_COMMUNITY, Map.of("community_name", communityName)));

        assertEquals(expectedResponse, Whitebox.getInternalState(userCommandHandler, "response"));
    }

    @Test
    public void shouldSetResponseAsLeaveCommunitySuccessWhenLastCommandWasLeaveCommunity() {
        Response expectedResponse = Response.LEAVE_COMMUNITY_SUCCESS;

        userCommandHandler.process(new Command(CommandType.LEAVE_COMMUNITY, Collections.emptyMap()));

        assertEquals(expectedResponse, Whitebox.getInternalState(userCommandHandler, "response"));
    }

    @Test
    public void shouldSetResponseAsLogOutSuccessWhenLastCommandWasLogOut() {
        Response expectedResponse = Response.LOG_OUT_SUCCESS;

        userCommandHandler.process(new Command(CommandType.LOG_OUT, Collections.emptyMap()));

        assertEquals(expectedResponse, Whitebox.getInternalState(userCommandHandler, "response"));
    }

    @Test
    public void shouldSetResponseAsUnsupportedWhenEnteredCommandIsUnsupported() {
        Response expectedResponse = Response.UNSUPPORTED;

        userCommandHandler.process(new Command(CommandType.UNSUPPORTED, Collections.emptyMap()));

        assertEquals(expectedResponse, Whitebox.getInternalState(userCommandHandler, "response"));
    }

    @Test
    public void shouldSetResponseAsHelpWhenUserHasEnteredHelpCommand() {
        Response expectedResponse = Response.HELP;

        userCommandHandler.process(new Command(CommandType.HELP, Collections.emptyMap()));

        assertEquals(expectedResponse, Whitebox.getInternalState(userCommandHandler, "response"));
    }

    @Test
    public void shouldSetResponseAsNotEnoughParametersWhenInputContainsNotAllParameters() {
        Response expectedResponse = Response.NOT_ENOUGH_PARAMETERS;

        userCommandHandler.process(new Command(CommandType.NOT_ENOUGH_PARAMETERS, Collections.emptyMap()));

        assertEquals(expectedResponse, Whitebox.getInternalState(userCommandHandler, "response"));
    }
}
