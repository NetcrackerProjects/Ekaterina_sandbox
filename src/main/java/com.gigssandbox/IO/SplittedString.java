package com.gigssandbox.IO;

public class SplittedString {
    private String string;

    public SplittedString(String string) {
        this.string = string;
    }

    public String[] value() {
        return string.trim().split(" ");
    }
}
