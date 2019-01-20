package com.gigssandbox.IO.console;

import com.gigssandbox.IO.OutputHandler;

import java.util.Collection;
import java.util.Map;

public class ConsoleOutputHandler implements OutputHandler {

    public void write(String string) {
        System.out.println(string);
    }

    public void writePreloaded(String propertyName) {
        write(getStored(propertyName));
    }

    public void write(Collection<?> collection) {
        if (collection.isEmpty()) {
            write(getStored("empty_result"));
        } else {
            collection.forEach(System.out::println);
        }
    }

    public void write(Map<String, String> map) {
        if (map.isEmpty()) {
            write(getStored("empty_result"));
        } else {
            map.values().forEach(this::write);
        }
    }
}
