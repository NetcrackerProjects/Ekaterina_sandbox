package com.gigssandbox;

import com.gigssandbox.command.Command;
import com.gigssandbox.command.CommandFactory;
import com.gigssandbox.command.CommandType;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CommandFactoryTest {

    @Test
    public void shouldReturnLoginCommandWhenZeroElementConsistsOfLoginCommand() {
        String[] loginArgs = new String[]{"log_in", "Bury", "Tomorrow"};
        Map<String, String> parameters = new HashMap<>();
        parameters.put("username", loginArgs[1]);
        parameters.put("password_hash", String.valueOf(loginArgs[2].hashCode()));
        Command expectedCommand = new Command(CommandType.LOG_IN, parameters);

        Command actualCommand = CommandFactory.from(loginArgs);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void shouldReturnGetGigsByBandCommandWhenZeroElementConsistsOfGetGigsByBandCommand() {
        String[] getGigsByBandArgs = new String[]{"get_gigs_by_band", "Oleg Gazmanov"};
        Command commandFromStringArray = CommandFactory.from(getGigsByBandArgs);
        Command expectedCommand = new Command(CommandType.GET_GIGS_BY_BAND, Collections.singletonMap("band_name", getGigsByBandArgs[1]));

        Command actualCommand = CommandFactory.from(getGigsByBandArgs);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void shouldReturnJoinCommunityCommandWhenZeroElementConsistsOfJoinCommunityCommand() {
        String[] joinCommunityArgs = new String[]{"join_community", "Autocad"};
        Command expectedCommand = new Command(CommandType.JOIN_COMMUNITY, Collections.singletonMap("community_name", joinCommunityArgs[1]));

        Command actualCommand = CommandFactory.from(joinCommunityArgs);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void shouldReturnGetGigsCommandWhenZeroElementConsistsOfGetGigsCommand() {
        String[] getGigsArgs = new String[]{"get_gigs"};
        Command expectedCommand = new Command(CommandType.GET_GIGS, Collections.emptyMap());

        Command actualCommand = CommandFactory.from(getGigsArgs);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void shouldReturnUnsuportedCommandWhenZeroElementConsistsOfUnsupportedText() {
        String[] unsupportedCommamdArgs = new String[]{"Adem"};
        Command expectedCommand = new Command(CommandType.UNSUPPORTED, Collections.emptyMap());

        Command actualCommand = CommandFactory.from(unsupportedCommamdArgs);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void shouldReturnNotEnoughParametersCommandWhenArrayContainsNotEnoughParameters() {
        String[] notEnoughParametersArray = new String[] {"log_in", "Shamil"};
        Command expectedCommand = new Command(CommandType.NOT_ENOUGH_PARAMETERS, Collections.emptyMap());

        Command actualCommand = CommandFactory.from(notEnoughParametersArray);

        assertEquals(expectedCommand, actualCommand);
    }
}
