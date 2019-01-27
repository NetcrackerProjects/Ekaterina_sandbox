package com.gigssandbox.IO;

import java.util.Arrays;
import java.util.stream.Collectors;

public class JoinedStrings {
    private String[] strings;

    public JoinedStrings(String... strings) {
        this.strings = strings;
    }

    public String value() {
        return Arrays.stream(strings).map(x -> x.concat(" ")).collect(Collectors.joining()).trim();
    }
}
