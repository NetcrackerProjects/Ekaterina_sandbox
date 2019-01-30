package com.gigssandbox.IO.console;

import com.gigssandbox.IO.Input;

import java.util.Scanner;

public class ConsoleInput implements Input {
    private final Scanner in;

    public ConsoleInput() {
        this.in = new Scanner(System.in);
    }

    public String[] parametersForCommand() {
        return splitted(in.nextLine());
    }
}