package com.gigssandbox.command;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class CommandFactoryTest {
    private CommandFactory commandFactory;

    @Before
    public void setUp() {
        this.commandFactory = new CommandFactory();
    }

    @Test
    public void shouldReturnLoginCommandWhenZeroElementConsistsOfLoginCommand() {
        List<String> loginArgs = List.of("log_in", "Bury", "Tomorrow");
        List<String> parameters = new ArrayList<>();
        parameters.add(loginArgs.get(1));
        parameters.add(loginArgs.get(2));
        Command expectedCommand = new Command(CommandType.LOG_IN, parameters);

        Command actualCommand = commandFactory.create(loginArgs);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void shouldReturnGetGigsByBandCommandWhenZeroElementConsistsOfGetGigsByBandCommand() {
        List<String> getGigsByBandArgs = List.of("get_gigs_by_band", "Oleg Gazmanov");
        Command expectedCommand = new Command(CommandType.GET_GIGS_BY_BAND, Collections.singletonList(getGigsByBandArgs.get(1)));

        Command actualCommand = commandFactory.create(getGigsByBandArgs);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void shouldReturnJoinCommunityCommandWhenZeroElementConsistsOfJoinCommunityCommand() {
        List<String> joinCommunityArgs = List.of("join_community", "Autocad");
        Command expectedCommand = new Command(CommandType.JOIN_COMMUNITY, Collections.singletonList(joinCommunityArgs.get(1)));

        Command actualCommand = commandFactory.create(joinCommunityArgs);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void shouldReturnGetGigsCommandWhenZeroElementConsistsOfGetGigsCommand() {
        List<String> getGigsArgs = Collections.singletonList("get_gigs");
        Command expectedCommand = new Command(CommandType.GET_GIGS, Collections.emptyList());

        Command actualCommand = commandFactory.create(getGigsArgs);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void shouldReturnUnsuportedCommandWhenZeroElementConsistsOfUnsupportedText() {
        List<String> unsupportedCommamdArgs = Collections.singletonList("Adem");
        Command expectedCommand = new Command(CommandType.UNSUPPORTED, Collections.emptyList());

        Command actualCommand = commandFactory.create(unsupportedCommamdArgs);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void shouldReturnNotEnoughParametersCommandWhenArrayContainsNotEnoughParameters() {
        String username = "Shamil";
        List<String> notEnoughParametersArray = List.of("log_in", username);
        Command expectedCommand = new Command(CommandType.NOT_ENOUGH_PARAMETERS, notEnoughParametersArray.subList(1, notEnoughParametersArray.size()));

        Command actualCommand = commandFactory.create(notEnoughParametersArray);

        assertEquals(expectedCommand, actualCommand);
    }
}