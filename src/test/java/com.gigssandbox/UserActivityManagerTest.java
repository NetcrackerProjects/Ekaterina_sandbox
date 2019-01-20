package com.gigssandbox;

import com.gigssandbox.IO.console.ConsoleInputHandler;
import com.gigssandbox.IO.console.ConsoleOutputHandler;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;


public class UserActivityManagerTest {
    private ByteArrayOutputStream output;
    private PrintStream oldOutput;
    private UserActivityManager userActivityManager;

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Before
    public void setUp() {
        this.oldOutput = System.out;
        this.output = new ByteArrayOutputStream();
        this.userActivityManager = new UserActivityManager(new ConsoleInputHandler(), new ConsoleOutputHandler());
        System.setOut(new PrintStream(output));
    }

    @Test
    public void shouldGetGetGigSCommandWhenEnteredIntegerIsTwo() {
        systemInMock.provideLines("2");
        Command addGigCommand = Command.GET_GIGS;

        Command fetchedCommand = userActivityManager.getNextCommand();

        assertEquals(addGigCommand, fetchedCommand);
    }

    @Test
    public void shouldGetUnsupportedCommandWhenUnknownIntegerIsEntered() {
        systemInMock.provideLines("28");
        Command unsupportedCommand = Command.UNSUPPORTED;

        Command fetchedCommand = userActivityManager.getNextCommand();

        assertEquals(unsupportedCommand, fetchedCommand);
    }

    @Test
    public void shouldShowUserLoggingInTextWhenUserLoggsIn() {
        Command command = Command.LOG_IN;
        String expectedOutput = "Enter your username and password (in different lines)\n";

        userActivityManager.displayOnlyTextResponse(command);

        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void shouldShowUserLoggingOutTextWhenUserHasLoggedOut() {
        Command command = Command.LOG_OUT;
        String expectedOutput = "You have successfully logged out\n";

        userActivityManager.displayOnlyTextResponse(command);

        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void shouldGetUserLoginAndPasswordWhenAppRequiresUserToLogIn() {
        Command command = Command.LOG_IN;
        systemInMock.provideLines("denis_shaforostov", "asking00alexandria00forever");
        Map<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("username", "denis_shaforostov");
        expectedParameters.put("password_hash",  String.valueOf(("asking00alexandria00forever").hashCode()));

        Map<String, String> actualParameters = userActivityManager.getParametersForRequest(command);

        assertEquals(expectedParameters, actualParameters);
    }

    @Test
    public void shouldGetEmptyParametersMapWhenUserWantsToGetAllGigs() {
        Command command = Command.GET_GIGS;
        systemInMock.provideLines("");
        Map<String, String> expectedEmptyMap = new HashMap<>();

        Map<String, String> actualParameter = userActivityManager.getParametersForRequest(command);

        assertEquals(expectedEmptyMap, actualParameter);
    }

    @Test
    public void shouldGetBandNameWhenUserWantsToGetGigsByBand() {
        Command command = Command.GET_GIGS_BY_BAND;
        systemInMock.provideLines("Alla Pugacheva");
        Map<String, String> expectedParameter = new HashMap<>();
        expectedParameter.put("band_name", "Alla Pugacheva");

        Map<String, String> actualParameter = userActivityManager.getParametersForRequest(command);

        assertEquals(expectedParameter, actualParameter);
    }

    @Test
    public void shouldGetCommunityNameWhenUserWantsToJoinCommunity() {
        Command command = Command.JOIN_COMMUNITY;
        systemInMock.provideLines("BGPE");
        Map<String, String> expectedParameter = new HashMap<>();
        expectedParameter.put("community_name", "BGPE");

        Map<String, String> actualParameter = userActivityManager.getParametersForRequest(command);

        assertEquals(expectedParameter, actualParameter);
    }

    @Test
    public void shouldDisplayAllGigsWhenUserAskedForListOfAllGigs() {
        Command command = Command.GET_GIGS;
        String expectedOutput = "Upcoming gigs:\nfake gigs\nit is a stub\n";

        userActivityManager.processCommand(command);

        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void shouldDisplayResponseForUserCommandWhenAppHasProcessedUserInputRelatedToCommand() {
        Command command = Command.GET_GIGS_BY_BAND;
        systemInMock.provideLines("Yury Antonov");
        String expectedOutput = "Upcoming gigs of chosen band:\nfake gigs\nit is a stub\nYury Antonov\n";

        userActivityManager.processCommand(command);

        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void shouldReturnEmptyMapWhenCommandIsNOtSuitableForCases() {
        Command command = Command.LEAVE_COMMUNITY;
        Map<String, String> expectedEmptyMap = new HashMap<>();

        Map<String, String> actualMapWithParameters = userActivityManager.getParametersForRequest(command);

        assertEquals(expectedEmptyMap, actualMapWithParameters);
    }

    @Test
    public void shouldRegisterUserWhenUserIsNotPresentInSystem() {
        UserActivityService userActivityServiceMock = Mockito.mock(UserActivityService.class);
        systemInMock.provideLines("oliver_sykes", "poprockisthebest");
        Mockito.when(userActivityServiceMock.getUsers()).thenReturn(new HashMap<>());
        String expectedOutput = "You have successfully registered\n";

        userActivityManager.processUserLoggingIn();

        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void shouldRepeatAskingLoginAndPasswordWhenEnteredPasswordIsIncorrect() {
        String username = "oliver_sykes";
        int passwordHash = ("poprockisthebest").hashCode();
        systemInMock.provideLines(username, "poprockisthebest");
        UserActivityService userActivityServiceMock = Mockito.mock(UserActivityService.class);
        Whitebox.setInternalState(userActivityManager, "userActivityService", userActivityServiceMock);


        Map<String, String> parametersForLoggingIn = new HashMap<>();
        parametersForLoggingIn.put("username", username);
        parametersForLoggingIn.put("password_hash", String.valueOf(passwordHash));

        String expectedOutput = "You have successfully logged in\n";

        Mockito.when(userActivityServiceMock.logUserIn(parametersForLoggingIn)).thenReturn("log_in_success");
        userActivityManager.processUserLoggingIn();

        assertEquals(expectedOutput, output.toString());
    }
    @After
    public void tearDown() {
        System.setOut(oldOutput);
    }
}