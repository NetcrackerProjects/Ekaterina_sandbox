package com.gigssandbox.command;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Command {
    private final CommandType type;
    private final List<String> parameters;
    @EqualsAndHashCode.Exclude
    private final CommandIterator commandIterator;

    public Command(CommandType type, List<String> parameters) {
        this.type = type;
        this.parameters = parameters;
        this.commandIterator = new CommandIterator();
    }

    public String nextParameter() {
        return commandIterator.next();
    }

    @EqualsAndHashCode
    private class CommandIterator implements Iterator<String> {
        private int cursor;
        private ListIterator<String> parametersIterator;

        CommandIterator() {
            this.cursor = 0;
            this.parametersIterator = parameters.listIterator();
        }

        @Override
        public boolean hasNext() {
            return cursor != parameters.size();
        }

        @Override
        public String next() {
            if (cursor >= parameters.size()) {
                throw new NoSuchElementException();
            }
            cursor++;

            return parametersIterator.next();
        }
    }
}
