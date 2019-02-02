package com.gigssandbox.command;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private CommandFactory() {

    }

    public static Command createCommandFrom(String... strings) {
        CommandType currentType;
        Map<String, String> parameters = new HashMap<>();

        try {
            currentType = CommandType.valueOf(strings[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            currentType = CommandType.UNSUPPORTED;
        }

        currentType = verifyParametersForCommand(strings, currentType);

        switch (currentType) {
            case LOG_IN:
            case REGISTER:
                parameters.put("username", strings[1]);
                parameters.put("password", strings[2]);
                break;
            case GET_GIGS_BY_BAND:
                parameters.put("band_name", strings[1]);
                break;
            case JOIN_COMMUNITY:
                parameters.put("community_name", strings[1]);
                break;
            default:
                break;
        }
        return new Command(currentType, parameters);
    }

    private static CommandType verifyParametersForCommand(String[] strings, CommandType commandType) {
        if (((commandType == CommandType.LOG_IN || commandType == CommandType.REGISTER) && strings.length < 3)
                || ((commandType == CommandType.GET_GIGS_BY_BAND || commandType == CommandType.JOIN_COMMUNITY) && strings.length < 2)) {
            return CommandType.NOT_ENOUGH_PARAMETERS;
        }
        return commandType;
    }
}