package com.gigssandbox.IO;

public interface Input {
    String[] receive();

    default String[] splitted(String string) {
        return string.trim().split(" ");
    }
}
