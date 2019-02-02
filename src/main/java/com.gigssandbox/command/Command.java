package com.gigssandbox.command;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Command {
    private final CommandType type;
    private final Map<String, String> parameters;
}
