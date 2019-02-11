package com.gigssandbox.command;

import com.gigssandbox.exceptions.CommandValidationException;
import com.gigssandbox.exceptions.UnsupportedCommandException;
import java.util.List;

public class CommandFactory {
    private final CommandValidator validator;

    public CommandFactory() {
        this.validator = new CommandValidator();
    }

    public Command create(List<String> strings) {
        CommandType type;
        List<String> parameters = strings.subList(1, strings.size());

        try {
            type = extractType(strings);

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
}