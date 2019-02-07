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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
    private GigService gigService;

    @Before
    public void setUp() {
        this.userService = new UserService();
        this.communityService = new CommunityService();
        this.gigService = new GigService();
        this.userCommandHandler = new UserCommandHandler(userService, communityService, gigService);
    }

    @Test
    public void shouldReturnRegistrationSuccessResultWhenLastCommandWasRegistration() {
        Result expectedResult = Result.REGISTRATION_SUCCESS;

        Result actualResult = userCommandHandler.process(new Command(CommandType.REGISTER, List.of("dark", "skies")));

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

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_IN, List.of(username, String.valueOf(password))));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNotRegisteredResultWhenLastCommandWasLogInAndUserWasNotRegisteredYet() {
        String username = "broken";
        char[] password = "youth".toCharArray();
        Result expectedResult = Result.NOT_REGISTERED;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_IN, List.of(username, String.valueOf(password))));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnIncorrectPasswordResultWhanLastCommandWasLogInAndUserEnteredWrongAnswer() {
        String username = "motionless";
        int correctPasswordHash = Arrays.hashCode("in white".toCharArray());
        char[] incorrectPassword = "in black".toCharArray();
        Whitebox.setInternalState(userService, "users", Map.of(username, User.builder().username(username).passwordHash(correctPasswordHash).build()));
        Result expectedResult = Result.INCORRECT_PASSWORD;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_IN, List.of(username, String.valueOf(incorrectPassword))));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnJoinCommunitySuccessResultWhenLastCommandWasJoinCommunity() {
        String communityName = "beartooth";
        Result expectedResult = Result.JOIN_COMMUNITY_SUCCESS;
        Map<String, Community> communities = new HashMap<>();
        communities.put(communityName, Community.builder().name(communityName).members(new HashSet<>()).build());
        Whitebox.setInternalState(communityService, "communities", communities);

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
        Whitebox.setInternalState(userCommandHandler, "username", "Carrot");
        Whitebox.setInternalState(userService, "users", Collections.singletonMap("Carrot", Mockito.mock(User.class)));

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
    public void shouldReturnAlreadyLoggedInResultWhenIsLoggedInFieldIsTrue() {
        String username = "Glen";
        char[] password = "Check".toCharArray();
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).passwordHash(Arrays.hashCode(password)).loggedIn(true).build()));
        Result expectredResult = Result.ALREADY_LOGGED_IN;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_IN, List.of(username, String.valueOf(password))));

        assertEquals(expectredResult, actualResult);
    }

    @Test
    public void shouldReturnAlreadyRegisteredResultWhenUserIsPresentInUsersMap() {
        String username = "John";
        char[] password = "Senya MC".toCharArray();
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).passwordHash(Arrays.hashCode(password)).build()));
        Result expectedResult = Result.ALREADY_REGISTERED;

        Result actualResult = userCommandHandler.process(new Command(CommandType.REGISTER, List.of(username, String.valueOf(password))));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNotRegisteredResultWhenUnregisteredUserTriesToLogOut() {
        Whitebox.setInternalState(userCommandHandler, "username", "obelisk");
        Whitebox.setInternalState(userService, "users", Collections.emptyMap());
        Result expectedResult = Result.NOT_REGISTERED;

        Result actualResult = userCommandHandler.process(new Command(CommandType.LOG_OUT, Collections.emptyList()));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnJoinGigSuccessResultWhenUserTriesToJoinGig() {
        String username = "aardwolf";
        String headliner = "the fever 333";
        String gigDateText = "2019-06-11";
        Gig.Credentials credentials = new Gig.Credentials(headliner, new GregorianCalendar(2019, Calendar.JUNE, 11));

        Map<Gig.Credentials, Gig> gigs = new HashMap<>();
        gigs.put(credentials, Gig.builder().credentials(credentials).attendees(new ArrayList<>()).build());

        Whitebox.setInternalState(userCommandHandler, "username", username);
        Whitebox.setInternalState(gigService, "gigs", gigs);
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).build()));

        Result expectedResult = Result.JOIN_GIG_SUCCESS;


        Result actualResult = userCommandHandler.process(new Command(CommandType.JOIN_GIG, List.of(headliner, gigDateText)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnIncorrectDateFormatResultWhenUserEnteredDateWithExtraSpace() {
        String username = "agouti";
        String headliner = "soulkeeper";
        String wrongGigDateText = "2020-01- 10";
        Gig.Credentials credentials = new Gig.Credentials(headliner, new GregorianCalendar(2020, Calendar.JANUARY, 10));

        Map<Gig.Credentials, Gig> gigs = new HashMap<>();
        gigs.put(credentials, Gig.builder().credentials(credentials).attendees(new ArrayList<>()).build());

        Whitebox.setInternalState(userCommandHandler, "username", username);
        Whitebox.setInternalState(gigService, "gigs", gigs);
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).build()));

        Result expectedResult = Result.INCORRECT_DATE_FORMAT;


        Result actualResult = userCommandHandler.process(new Command(CommandType.JOIN_GIG, List.of(headliner, wrongGigDateText)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnIncorrectDateFormatResultWhenUserEnteredDateWithoutDay() {
        String username = "alpaca";
        String headliner = "boris the blade";
        String wrongGigDateText = "2010-09";
        Gig.Credentials credentials = new Gig.Credentials(headliner, new GregorianCalendar(2010, Calendar.SEPTEMBER, 2));

        Map<Gig.Credentials, Gig> gigs = new HashMap<>();
        gigs.put(credentials, Gig.builder().credentials(credentials).attendees(new ArrayList<>()).build());

        Whitebox.setInternalState(userCommandHandler, "username", username);
        Whitebox.setInternalState(gigService, "gigs", gigs);
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).build()));

        Result expectedResult = Result.INCORRECT_DATE_FORMAT;


        Result actualResult = userCommandHandler.process(new Command(CommandType.JOIN_GIG, List.of(headliner, wrongGigDateText)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnIncorrectDateValueResultWhenMadeMistakeInDateWhileJoiningGig() {
        String username = "asian_elephant";
        String headliner = "attila";
        String wrongGigDateText = "2011-09-39";
        Gig.Credentials credentials = new Gig.Credentials(headliner, new GregorianCalendar(2011, Calendar.SEPTEMBER, 9));

        Map<Gig.Credentials, Gig> gigs = new HashMap<>();
        gigs.put(credentials, Gig.builder().credentials(credentials).attendees(new ArrayList<>()).build());

        Whitebox.setInternalState(userCommandHandler, "username", username);
        Whitebox.setInternalState(gigService, "gigs", gigs);
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).build()));

        Result expectedResult = Result.INCORRECT_DATE_VALUE;


        Result actualResult = userCommandHandler.process(new Command(CommandType.JOIN_GIG, List.of(headliner, wrongGigDateText)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNoAppropriateGigsResultWhenGigForJoiningCredentialsAreNotPresentInGigsCollection() {
        String username = "badger";
        String headliner = "parkway drive";
        String wrongGigDateText = "2016-08-03";
        Result expectedResult = Result.NO_APPROPRIATE_GIGS;

        Whitebox.setInternalState(userCommandHandler, "username", username);
        Whitebox.setInternalState(gigService, "gigs", Collections.emptyMap());
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).build()));


        Result actualResult = userCommandHandler.process(new Command(CommandType.JOIN_GIG, List.of(headliner, wrongGigDateText)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnLeaveGigSuccessResultWhenUserTriesToLeaveGig() {
        String username = "buffalo";
        String headliner = "sienna skies";
        String gigDate = "2012-10-12";
        Result expectedResult = Result.LEAVE_GIG_SUCCESS;
        Gig.Credentials credentials = new Gig.Credentials(headliner, new GregorianCalendar(2012, Calendar.OCTOBER, 12));

        Collection<User> attendees = new HashSet<>();
        attendees.add(User.builder().username(username).build());

        Whitebox.setInternalState(userCommandHandler, "username", username);
        Whitebox.setInternalState(gigService, "gigs", Collections.singletonMap(credentials, Gig.builder().credentials(credentials).attendees(attendees).build()));
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).build()));

        Result actualResult = userCommandHandler.process(new Command(CommandType.LEAVE_GIG, List.of(headliner, gigDate)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnIncorrectDateValueResultWhenUserMadeMistakeInDateWhileLeavingGig() {
        String username = "agile wallaby";
        String headliner = "adept";
        String gigDate = "2013-99-21";
        Result expectedResult = Result.INCORRECT_DATE_VALUE;
        Gig.Credentials credentials = new Gig.Credentials(headliner, new GregorianCalendar(2013, Calendar.SEPTEMBER, 21));

        Collection<User> attendees = new HashSet<>();
        attendees.add(User.builder().username(username).build());

        Whitebox.setInternalState(userCommandHandler, "username", username);
        Whitebox.setInternalState(gigService, "gigs", Collections.singletonMap(credentials, Gig.builder().credentials(credentials).attendees(attendees).build()));
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).build()));

        Result actualResult = userCommandHandler.process(new Command(CommandType.LEAVE_GIG, List.of(headliner, gigDate)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnIncorrectDateFormatResultWhenUserMadeMistakeInDateWhileLeavingGig() {
        String username = "armadillo";
        String headliner = "woe, is me";
        String gigDate = "2013-a1-11";
        Result expectedResult = Result.INCORRECT_DATE_FORMAT;
        Gig.Credentials credentials = new Gig.Credentials(headliner, new GregorianCalendar(2013, Calendar.JANUARY, 11));

        Collection<User> attendees = new HashSet<>();
        attendees.add(User.builder().username(username).build());

        Whitebox.setInternalState(userCommandHandler, "username", username);
        Whitebox.setInternalState(gigService, "gigs", Collections.singletonMap(credentials, Gig.builder().credentials(credentials).attendees(attendees).build()));
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).build()));

        Result actualResult = userCommandHandler.process(new Command(CommandType.LEAVE_GIG, List.of(headliner, gigDate)));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnNoAppropriateGigsResultWhenGigForLeavingCredentialsAreNotPresentInGigsCollection() {
        String username = "african darter";
        String headliner = "attack attack!";
        String wrongGigDateText = "2013-10-12";
        Result expectedResult = Result.NO_APPROPRIATE_GIGS;

        Whitebox.setInternalState(userCommandHandler, "username", username);
        Whitebox.setInternalState(gigService, "gigs", Collections.emptyMap());
        Whitebox.setInternalState(userService, "users", Collections.singletonMap(username, User.builder().username(username).build()));


        Result actualResult = userCommandHandler.process(new Command(CommandType.LEAVE_GIG, List.of(headliner, wrongGigDateText)));

        assertEquals(expectedResult, actualResult);
    }
}
