package com.gigssandbox.command;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;

@EqualsAndHashCode
@Getter
public class Command {
    private final CommandType type;
    private final Map<String, String> parameters;

    public Command(CommandType type, Map<String, String> parameters) {
        this.type = type;
        this.parameters = parameters;
    }
}
