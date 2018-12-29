package com.gigssandbox;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class UserMessengerTest {
    private ByteArrayOutputStream output;
    private PrintStream oldOut;
    private UserMessenger userMessenger;

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Before
    public void setUp() {
        oldOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        userMessenger = new UserMessenger();
    }

    @Test
    public void shouldWriteTestTextFromPropertiesWhenTestPropertyIsMessaged() {
        String textToBeSent = "This is test\n";

        userMessenger.sendMessage("test");

        assertEquals(textToBeSent, output.toString());
    }

    @Test
    public void shouldGetAddGigCommandWhenEnteredIntegerIsThree() {
        systemInMock.provideLines("3");
        Command addGigCommand = Command.ADD_GIG;

        Command fetchedCommand = userMessenger.getNextCommand();

        assertEquals(addGigCommand, fetchedCommand);
    }

    @Test
    public void shouldGetUnsupportedCommandWhenUnknownIntegerIsEntered() {
        systemInMock.provideLines("28");
        Command unsupportedCommand = Command.UNSUPPORTED;

        Command fetchedCommand = userMessenger.getNextCommand();

        assertEquals(unsupportedCommand, fetchedCommand);
    }

    @After
    public void tearDown() {
        System.setOut(oldOut);
    }
}