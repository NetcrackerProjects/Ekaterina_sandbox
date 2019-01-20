package com.gigssandbox;

import com.gigssandbox.IO.console.ConsoleInputHandler;
import com.gigssandbox.IO.console.ConsoleOutputHandler;

public class MainClass {
    public static void main(String[] args) {
        new UserActivityManager(new ConsoleInputHandler(), new ConsoleOutputHandler()).startInteracting();
    }
}