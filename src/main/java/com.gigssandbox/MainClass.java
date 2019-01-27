package com.gigssandbox;

import com.gigssandbox.IO.console.ConsoleInput;
import com.gigssandbox.IO.console.ConsoleOutput;

public class MainClass {
    public static void main(String[] args) {
        new Activity(new ConsoleInput(), new ConsoleOutput()).beginReceivingAndProcessingCommands();
    }
}