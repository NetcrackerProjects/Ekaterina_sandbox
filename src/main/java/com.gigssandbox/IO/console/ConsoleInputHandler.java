package com.gigssandbox.IO.console;

import com.gigssandbox.IO.InputHandler;
import com.gigssandbox.IO.StringTransformer;

import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {
    private final Scanner in;
    private StringTransformer stringTransformer;

    public ConsoleInputHandler() {
        this.in = new Scanner(System.in);
        this.stringTransformer = new StringTransformer();

    }

    public int readInt() {
        return Integer.parseInt(in.nextLine());
    }

    public String readString() {
        return in.nextLine();
    }

    public String[] readStringAndSplit() {
        String stringWithEntityFields = readString();
        return stringTransformer.split(stringWithEntityFields);
    }
}