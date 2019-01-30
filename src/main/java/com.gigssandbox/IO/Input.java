package com.gigssandbox.IO;

public interface Input {
    String[] parametersForCommand();

    default String[] splitted(String string) {
        return string.trim().split(" ");
    }
}
