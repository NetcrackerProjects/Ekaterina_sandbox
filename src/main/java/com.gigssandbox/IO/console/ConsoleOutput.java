package com.gigssandbox.IO.console;

import com.gigssandbox.IO.Output;

import java.util.Collection;
import java.util.Map;

public class ConsoleOutput implements Output {
    public void write(Collection<?> collection) {
        if (collection.isEmpty()) {
            write(stored("empty_result"));
        } else {
            collection.forEach(System.out::println);
        }
    }

    public void write(Map<String, String> map) {
        if (map.isEmpty()) {
            write(stored("empty_result"));
        } else {
            map.values().forEach(this::write);
        }
    }

    public void writeStored(String propertyName) {
        write(stored(propertyName));
    }

    public void write(String string) {
        System.out.println(string);
    }
}
