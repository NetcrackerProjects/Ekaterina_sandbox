package com.gigssandbox.command;

import com.gigssandbox.exceptions.CommandValidationException;
import java.util.Arrays;
import java.util.List;

public class CommandFactory {
    private static final CommandValidator validator = new CommandValidator();

    private CommandFactory() {

    }

    public static Command create(String... strings) {
        CommandType currentType;
        List<String> parameters = List.of(Arrays.stream(strings).skip(1).toArray(String[]::new));

        try {
            currentType = CommandType.valueOf(strings[0].toUpperCase());

        } catch (IllegalArgumentException e) {
            currentType = CommandType.UNSUPPORTED;
        }

        try {
            validator.validate(currentType, parameters);

        } catch (CommandValidationException e) {
            currentType = CommandType.NOT_ENOUGH_PARAMETERS;
        }

        return new Command(currentType, parameters);
    }
}