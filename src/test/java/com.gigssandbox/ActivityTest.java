package com.gigssandbox;

import com.gigssandbox.IO.console.ConsoleInput;
import com.gigssandbox.IO.console.ConsoleOutput;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class ActivityTest {
    private ByteArrayOutputStream output;
    private PrintStream oldOutput;
    private Activity activity;

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Before
    public void setUp() {
        this.oldOutput = System.out;
        this.output = new ByteArrayOutputStream();
        this.activity = new Activity(new ConsoleInput(), new ConsoleOutput());

        System.setOut(new PrintStream(output));
    }

//    @Test
//    public void shouldWriteHelpWhenAppHasStarted() {
//        String expectedOutput = "Help (command: description, parameters)\nlog in: log_in <username> <password>\nlog out: log_out\nregistration: register <username> <password>\n";
//
//        activity.beginReceivingAndProcessingCommands();
//
//        assertEquals(expectedOutput, output.toString());
//    }
//
//    @Test
//    public void shouldShowResultOfProcessingLogInCommandWhenUserTriedToLogIn() {
//        String expectedOutput = "You have successfully logged in\n";
//
//        activity.beginReceivingAndProcessingCommands();
//
//        assertEquals(expectedOutput, output.toString());
//    }

    @After
    public void tearDown() {
        System.setOut(oldOutput);
    }
}