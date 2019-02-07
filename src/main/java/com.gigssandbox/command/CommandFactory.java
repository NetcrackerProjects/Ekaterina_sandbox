package com.gigssandbox.command;

import java.util.ArrayList;
import java.util.List;

public class CommandFactory {
    private CommandFactory() {

    }

    public static Command create(String... strings) {
        CommandType currentType;
        List<String> parameters = new ArrayList<>();

        try {
            currentType = CommandType.valueOf(strings[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            currentType = CommandType.UNSUPPORTED;
        }

        currentType = verifyParametersForCommand(strings, currentType);

        switch (currentType) {
            case LOG_IN:
            case REGISTER:
            case JOIN_GIG:
            case LEAVE_GIG:
                parameters.add(strings[1]);
                parameters.add(strings[2]);
                break;
            case GET_GIGS_BY_BAND:
                parameters.add(strings[1]);
                break;
            case JOIN_COMMUNITY:
                parameters.add(strings[1]);
                break;
            default:
                break;
        }
        return new Command(currentType, parameters);
    }

    private static CommandType verifyParametersForCommand(String[] strings, CommandType commandType) {
        if (notEnoughArgumentsForCommandWithThreeParameters(strings, commandType)
                || (notEnoughArgumentsForCommandWithTwoParameters(strings, commandType))) {
            return CommandType.NOT_ENOUGH_PARAMETERS;
        }
        return commandType;
    }

    private static boolean notEnoughArgumentsForCommandWithThreeParameters(String[] strings, CommandType commandType) {
        return (commandType == CommandType.LOG_IN || commandType == CommandType.REGISTER) && strings.length < 3;
    }

    private static boolean notEnoughArgumentsForCommandWithTwoParameters(String[] strings, CommandType commandType) {
        return (commandType == CommandType.GET_GIGS_BY_BAND || commandType == CommandType.JOIN_COMMUNITY) && strings.length < 2;
    }
}