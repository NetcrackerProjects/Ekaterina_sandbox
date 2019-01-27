package com.gigssandbox.IO.console;

import com.gigssandbox.IO.Input;
import com.gigssandbox.IO.SplittedString;

import java.util.Scanner;

public class ConsoleInput implements Input {
    private final Scanner in;

    public ConsoleInput() {
        this.in = new Scanner(System.in);
    }

    public String[] nextStringSplitted() {
        return new SplittedString(in.nextLine()).value();
    }
}