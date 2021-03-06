package com.gigssandbox.io.console;

import com.gigssandbox.io.Input;
import java.util.Scanner;

public class ConsoleInput implements Input {
    private final Scanner in;

    public ConsoleInput() {
        this.in = new Scanner(System.in);
    }

    public String receive() {
        return in.nextLine();
    }
}