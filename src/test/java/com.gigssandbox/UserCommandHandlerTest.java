package com.gigssandbox;

import com.gigssandbox.command.CommandFactory;
import com.gigssandbox.entities.Community;
import com.gigssandbox.entities.Gig;
import com.gigssandbox.entities.User;
import com.gigssandbox.services.CommunityService;
import com.gigssandbox.services.GigService;
import com.gigssandbox.services.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserCommandHandlerTest {
    private UserCommandHandler userCommandHandler;
    private String username;
    private String password;
    private String headliner;
    private String gigDate;
    private User user;
    private Gig gig;
    private String communityName;
    private Community community;
    private String gigCredentials;
    private Map<String, Gig> gigs;
    private Map<String, Community> communities;
    private CommandFactory commandFactory;

    @Before
    public void setUp() {
        this.username = "John";
        this.password = "Senya MC";
        this.headliner = "Attack attack!";
        this.gigDate = "2016-06-19";
        this.gigCredentials = "Attack attack!:2016-06-19";

        this.user = User.builder()
                .username(username)
                .passwordHash(password.hashCode())
                .build();

        this.gig = Gig.builder()
                .headliner(headliner)
                .gigDate(gigDate)
                .attendees(new ArrayList<>())
                .build();

        this.gigs = new HashMap<>();

        this.communityName = "very progressive";
        this.community = Community.builder()
                .name(communityName)
                .members(new HashSet<>())
                .build();

        this.communities = new HashMap<>();

        UserService userService = new UserService(new HashMap<>());
        CommunityService communityService = new CommunityService(communities);
        GigService gigService = new GigService(gigs);

        this.userCommandHandler = new UserCommandHandler(userService, communityService, gigService);
        this.commandFactory = new CommandFactory();
    }

    @Test
    public void shouldReturnRegistrationSuccessResultWhenLastCommandWasRegistration() {
        Result expectedResult = Result.REGISTRATION_SUCCESS;

        Result actualResult = userCommandHandler.process(commandFactory.create("register,".concat(username).concat(",").concat(password)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnLoginSuccessResultWhenLastCommandWasLogIn() {
        userCommandHandler.process(commandFactory.create("register,".concat(username).concat(",").concat(password)));
        userCommandHandler.process(commandFactory.create("log_out"));
        Result expectedResult = Result.LOG_IN_SUCCESS;

        Result actualResult = userCommandHandler.process(commandFactory.create("log_in,".concat(username).concat(",").concat(password)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNotRegisteredResultWhenLastCommandWasLogInAndUserWasNotRegisteredYet() {
        Result expectedResult = Result.NOT_REGISTERED;

        Result actualResult = userCommandHandler.process(commandFactory.create("log_in,".concat(username).concat(",").concat(password)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnIncorrectPasswordResultWhanLastCommandWasLogInAndUserEnteredWrongPassword() {
        String incorrectPassword = "in black";
        userCommandHandler.process(commandFactory.create("register,".concat(username).concat(",").concat(password)));
        Result expectedResult = Result.INCORRECT_PASSWORD;

        Result actualResult = userCommandHandler.process(commandFactory.create("log_in,".concat(username).concat(",").concat(incorrectPassword)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnJoinCommunitySuccessResultWhenLastCommandWasJoinCommunity() {
        userCommandHandler.process(commandFactory.create("register,".concat(username).concat(",").concat(password)));
        communities.put(communityName, community);
        Result expectedResult = Result.JOIN_COMMUNITY_SUCCESS;

        Result actualResult = userCommandHandler.process(commandFactory.create("join_community,".concat(communityName)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnLeaveCommunitySuccessResultWhenLastCommandWasLeaveCommunity() {
        Result expectedResult = Result.LEAVE_COMMUNITY_SUCCESS;

        Result actualResult = userCommandHandler.process(commandFactory.create("leave_community"));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnLogOutSuccessResultWhenLastCommandWasLogOut() {
        Result expectedResult = Result.LOG_OUT_SUCCESS;
        userCommandHandler.process(commandFactory.create("register,".concat(username).concat(",").concat(password)));

        Result actualResult = userCommandHandler.process(commandFactory.create("log_out"));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnUnsupportedResultWhenEnteredCommandIsUnsupported() {
        Result expectedResult = Result.UNSUPPORTED;

        Result actualResult = userCommandHandler.process(commandFactory.create("unsupported"));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnHelpResultWhenUserHasEnteredHelpCommand() {
        Result expectedResult = Result.HELP;

        Result actualResult = userCommandHandler.process(commandFactory.create("help"));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNotEnoughParametersResultWhenInputContainsNotAllParameters() {
        Result expectedResult = Result.NOT_ENOUGH_PARAMETERS;

        Result actualResult = userCommandHandler.process(commandFactory.create("not_enough_parameters"));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnAlreadyLoggedInResultWhenUserIsAlreadyLoggedIn() {
        userCommandHandler.process(commandFactory.create("register,".concat(username).concat(",").concat(password)));
        Result expectredResult = Result.ALREADY_LOGGED_IN;

        Result actualResult = userCommandHandler.process(commandFactory.create("log_in,".concat(username).concat(",").concat(password)));

        assertEquals(expectredResult, actualResult);
    }

    @Test
    public void shouldReturnAlreadyRegisteredResultWhenUserIsAlreadyRegistered() {
        userCommandHandler.process(commandFactory.create("register,".concat(username).concat(",").concat(password)));
        Result expectedResult = Result.ALREADY_REGISTERED;

        Result actualResult = userCommandHandler.process(commandFactory.create("register,".concat(username).concat(",").concat(password)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNotRegisteredResultWhenUnregisteredUserTriesToLogOut() {
        Result expectedResult = Result.NOT_REGISTERED;

        Result actualResult = userCommandHandler.process(commandFactory.create("log_out"));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnJoinGigSuccessResultWhenUserTriesToJoinGig() {
        userCommandHandler.process(commandFactory.create("register,".concat(username).concat(",").concat(password)));
        gigs.put(gigCredentials, gig);
        Result expectedResult = Result.JOIN_GIG_SUCCESS;

        Result actualResult = userCommandHandler.process(commandFactory.create("join_gig,".concat(headliner).concat(",").concat(gigDate)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnIncorrectDateResultWhenUserEnteredDateWithoutDay() {
        String wrongGigDate = "2010-09";
        userCommandHandler.process(commandFactory.create("register,".concat(username).concat(",").concat(password)));
        Result expectedResult = Result.INCORRECT_DATE_FORMAT;

        Result actualResult = userCommandHandler.process(commandFactory.create("join_gig,".concat(headliner).concat(",").concat(wrongGigDate)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnIncorrectDateResultWhenMadeMistakeInDateWhileJoiningGig() {
        String wrongGigDate = "2011-09-39";
        userCommandHandler.process(commandFactory.create("register,".concat(username).concat(",").concat(password)));
        Result expectedResult = Result.INCORRECT_DATE_FORMAT;

        Result actualResult = userCommandHandler.process(commandFactory.create("join_gig,".concat(headliner).concat(",").concat(wrongGigDate)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNoAppropriateGigsResultWhenGigForJoiningCredentialsAreNotPresent() {
        Result expectedResult = Result.NO_SUCH_GIG;
        userCommandHandler.process(commandFactory.create("register,".concat(username).concat(",").concat(password)));

        Result actualResult = userCommandHandler.process(commandFactory.create("join_gig,".concat(headliner).concat(",").concat(gigDate)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnLeaveGigSuccessResultWhenUserTriesToLeaveGig() {
        Result expectedResult = Result.LEAVE_GIG_SUCCESS;
        gig.add(user);
        gigs.put(gigCredentials, gig);
        userCommandHandler.process(commandFactory.create("register,".concat(username).concat(",").concat(password)));

        Result actualResult = userCommandHandler.process(commandFactory.create("leave_gig,".concat(headliner).concat(",").concat(gigDate)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnIncorrectDateResultWhenUserMadeMistakeInDateWhileLeavingGig() {
        String wrongGigDate = "2013-99-21";
        Result expectedResult = Result.INCORRECT_DATE_FORMAT;
        gig.add(user);
        userCommandHandler.process(commandFactory.create("register,".concat(username).concat(",").concat(password)));

        Result actualResult = userCommandHandler.process(commandFactory.create("leave_gig,".concat(headliner).concat(",").concat(wrongGigDate)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNoSuchGigResultWhenGigForLeavingIsNotPresent() {
        String wrongGigDate = "2013-10-12";
        Result expectedResult = Result.NO_SUCH_GIG;
        userCommandHandler.process(commandFactory.create("register,".concat(username).concat(",").concat(password)));

        Result actualResult = userCommandHandler.process(commandFactory.create("leave_gig,".concat(headliner).concat(",").concat(wrongGigDate)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNoSuchCommunityResultWhenEnteredCommunityIsNotPresent() {
        Result expectedResult = Result.NO_SUCH_COMMUNITY;

        Result actualResult = userCommandHandler.process(commandFactory.create("join_community,".concat(communityName)));

        assertEquals(expectedResult, actualResult);
    }
}