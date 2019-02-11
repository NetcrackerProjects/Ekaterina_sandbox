package com.gigssandbox.command;

import com.gigssandbox.exceptions.CommandValidationException;
import com.gigssandbox.exceptions.UnsupportedCommandException;
import java.util.Arrays;
import java.util.List;

public class CommandFactory {
    private final CommandValidator validator;

    public CommandFactory() {
        this.validator = new CommandValidator();
    }

    public Command create(String commandParams) {
        CommandType type;

        List<String> parametersWithType = List.of(split(commandParams));

        List<String> parameters = parametersWithType.subList(1, parametersWithType.size());

        try {
            type = extractType(parametersWithType);

        } catch (UnsupportedCommandException e) {
            type = CommandType.UNSUPPORTED;
        }

        try {
            validator.validate(type, parameters);

        } catch (CommandValidationException e) {
            type = CommandType.NOT_ENOUGH_PARAMETERS;
        }

        return new Command(type, parameters);
    }

    private CommandType extractType(List<String> strings) throws UnsupportedCommandException {
        try {
            return CommandType.valueOf(strings.get(0).toUpperCase());

        } catch (IllegalArgumentException e) {
            throw new UnsupportedCommandException();
        }
    }

    private String[] split(String string) {
        return Arrays.stream(string.strip().split(",")).map(String::strip).toArray(String[]::new);
    }
}