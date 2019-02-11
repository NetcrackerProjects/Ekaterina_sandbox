package com.gigssandbox;

import com.gigssandbox.command.Command;
import com.gigssandbox.command.CommandType;
import com.gigssandbox.entities.Community;
import com.gigssandbox.entities.Gig;
import com.gigssandbox.entities.User;
import com.gigssandbox.services.CommunityService;
import com.gigssandbox.services.GigService;
import com.gigssandbox.services.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserCommandHandlerTest {
    private UserCommandHandler userCommandHandler;
    private GigService gigService;
    private String username;
    private String password;
    private String headliner;
    private String gigDateText;
    private User user;
    private Gig gig;
    private String communityName;
    private Community community;
    private String gigCredentials;
    private Map<String, User> users;
    private Map<String, Gig> gigs;
    private Map<String, Community> communities;

    @Before
    public void setUp() {
        this.username = "John";
        this.password = "Senya MC";
        this.headliner = "Attack attack!";
        this.gigDateText = "2016-06-19";
        this.gigCredentials = "Attack attack!:2016-06-19";

        this.user = User.builder()
                .username(username)
                .passwordHash(password.hashCode())
                .build();

        this.gig = Gig.builder()
                .credentials(gigCredentials)
                .attendees(new ArrayList<>())
                .build();

        this.gigs = new HashMap<>();

        this.users = new HashMap<>();

        this.communityName = "very progressive";
        this.community = Community.builder()
                .name(communityName)
                .members(new HashSet<>())
                .build();

        this.communities = new HashMap<>();

        UserService userService = new UserService(users);
        CommunityService communityService = new CommunityService(communities);
        this.gigService = new GigService(gigs);

        this.userCommandHandler = new UserCommandHandler(userService, communityService, gigService);
    }

    @Test
    public void shouldReturnRegistrationSuccessResultWhenLastCommandWasRegistration() {
        Result expectedResult = Result.REGISTRATION_SUCCESS;

        Result actualResult = userCommandHandler.process(new Command(CommandType.REGISTER, List.of(username, password)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnLoginSuccessResultWhenLastCommandWasLogIn() {
        userCommandHandler.process(new Command(CommandType.REGISTER, List.of(username, password)));
        userCommandHandler.process(new Command(CommandType.LOG_OUT, Collections.emptyList()));
        Result expectedResult = Result.LOG_IN_SUCCESS;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_IN, List.of(username, password)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNotRegisteredResultWhenLastCommandWasLogInAndUserWasNotRegisteredYet() {
        Result expectedResult = Result.NOT_REGISTERED;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_IN, List.of(username, password)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnIncorrectPasswordResultWhanLastCommandWasLogInAndUserEnteredWrongPassword() {
        String incorrectPassword = "in black";
        userCommandHandler.process(new Command(CommandType.REGISTER, List.of(username, password)));
        Result expectedResult = Result.INCORRECT_PASSWORD;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_IN, List.of(username, incorrectPassword)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnJoinCommunitySuccessResultWhenLastCommandWasJoinCommunity() {
        userCommandHandler.process(new Command(CommandType.REGISTER, List.of(username, password)));
        communities.put(communityName, community);
        Result expectedResult = Result.JOIN_COMMUNITY_SUCCESS;

        Result actualResult = userCommandHandler.process(new Command(CommandType.JOIN_COMMUNITY, Collections.singletonList(communityName)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnLeaveCommunitySuccessResultWhenLastCommandWasLeaveCommunity() {
        Result expectedResult = Result.LEAVE_COMMUNITY_SUCCESS;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LEAVE_COMMUNITY, Collections.emptyList()));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnLogOutSuccessResultWhenLastCommandWasLogOut() {
        Result expectedResult = Result.LOG_OUT_SUCCESS;
        userCommandHandler.process(new Command(CommandType.REGISTER, List.of(username, password)));;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_OUT, Collections.emptyList()));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnUnsupportedResultWhenEnteredCommandIsUnsupported() {
        Result expectedResult = Result.UNSUPPORTED;

        Result actualResult = userCommandHandler.process(new Command(CommandType.UNSUPPORTED, Collections.emptyList()));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnHelpResultWhenUserHasEnteredHelpCommand() {
        Result expectedResult = Result.HELP;

        Result actualResult = userCommandHandler.process(new Command(CommandType.HELP, Collections.emptyList()));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNotEnoughParametersResultWhenInputContainsNotAllParameters() {
        Result expectedResult = Result.NOT_ENOUGH_PARAMETERS;

        Result actualResult = userCommandHandler.process(new Command(CommandType.NOT_ENOUGH_PARAMETERS, Collections.emptyList()));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnAlreadyLoggedInResultWhenUserIsAlreadyLoggedIn() {
        userCommandHandler.process(new Command(CommandType.REGISTER, List.of(username, password)));
        Result expectredResult = Result.ALREADY_LOGGED_IN;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_IN, List.of(username, password)));

        assertEquals(expectredResult, actualResult);
    }

    @Test
    public void shouldReturnAlreadyRegisteredResultWhenUserIsPresent() {
        userCommandHandler.process(new Command(CommandType.REGISTER, List.of(username, password)));
        Result expectedResult = Result.ALREADY_REGISTERED;

        Result actualResult = userCommandHandler.process(new Command(CommandType.REGISTER, List.of(username, password)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNotRegisteredResultWhenUnregisteredUserTriesToLogOut() {
        users = Collections.emptyMap();
        Result expectedResult = Result.NOT_REGISTERED;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_OUT, Collections.emptyList()));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnJoinGigSuccessResultWhenUserTriesToJoinGig() {
        userCommandHandler.process(new Command(CommandType.REGISTER, List.of(username, password)));
        gigs.put(gigCredentials, gig);
        Result expectedResult = Result.JOIN_GIG_SUCCESS;

        Result actualResult = userCommandHandler.process(new Command(CommandType.JOIN_GIG, List.of(headliner, gigDateText)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnIncorrectDateFormatResultWhenUserEnteredDateWithoutDay() {
        String wrongGigDateText = "2010-09";
        userCommandHandler.process(new Command(CommandType.REGISTER, List.of(username, password)));
        Result expectedResult = Result.INCORRECT_DATE_FORMAT;

        Result actualResult = userCommandHandler.process(new Command(CommandType.JOIN_GIG, List.of(headliner, wrongGigDateText)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnIncorrectDateFormatResultWhenMadeMistakeInDateWhileJoiningGig() {
        String wrongGigDateText = "2011-09-39";
        userCommandHandler.process(new Command(CommandType.REGISTER, List.of(username, password)));
        Result expectedResult = Result.INCORRECT_DATE_FORMAT;

        Result actualResult = userCommandHandler.process(new Command(CommandType.JOIN_GIG, List.of(headliner, wrongGigDateText)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNoAppropriateGigsResultWhenGigForJoiningCredentialsAreNotPresent() {
        Result expectedResult = Result.NO_SUCH_GIG;
        userCommandHandler.process(new Command(CommandType.REGISTER, List.of(username, password)));

        Result actualResult = userCommandHandler.process(new Command(CommandType.JOIN_GIG, List.of(headliner, gigDateText)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnLeaveGigSuccessResultWhenUserTriesToLeaveGig() {
        Result expectedResult = Result.LEAVE_GIG_SUCCESS;
        gig.add(user);
        gigs.put(gigCredentials, gig);
        userCommandHandler.process(new Command(CommandType.REGISTER, List.of(username, password)));

        Result actualResult = userCommandHandler.process(new Command(CommandType.LEAVE_GIG, List.of(headliner, gigDateText)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnIncorrectDateValueResultWhenUserMadeMistakeInDateWhileLeavingGig() {
        String incorrectDateText = "2013-99-21";
        Result expectedResult = Result.INCORRECT_DATE_FORMAT;
        gig.add(user);
        userCommandHandler.process(new Command(CommandType.REGISTER, List.of(username, password)));

        Result actualResult = userCommandHandler.process(new Command(CommandType.LEAVE_GIG, List.of(headliner, incorrectDateText)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnIncorrectDateFormatResultWhenUserMadeMistakeInDateWhileLeavingGig() {
        String incorrectDateText = "2013-a1-11";
        Result expectedResult = Result.INCORRECT_DATE_FORMAT;
        gig.add(user);
        userCommandHandler.process(new Command(CommandType.REGISTER, List.of(username, password)));

        Result actualResult = userCommandHandler.process(new Command(CommandType.LEAVE_GIG, List.of(headliner, incorrectDateText)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNoAppropriateGigResultWhenGigForLeavingIsNotPresent() {
        String wrongDateText = "2013-10-12";
        Result expectedResult = Result.NO_SUCH_GIG;
        userCommandHandler.process(new Command(CommandType.REGISTER, List.of(username, password)));
        gigs = Collections.emptyMap();

        Result actualResult = userCommandHandler.process(new Command(CommandType.LEAVE_GIG, List.of(headliner, wrongDateText)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNoAppropriateCommunityResultWhenEnteredCommunityIsNotPresent() {
        Result expectedResult = Result.NO_SUCH_COMMUNITY;

        Result actualResult = userCommandHandler.process(new Command(CommandType.JOIN_COMMUNITY, Collections.singletonList(communityName)));

        assertEquals(expectedResult, actualResult);
    }
}