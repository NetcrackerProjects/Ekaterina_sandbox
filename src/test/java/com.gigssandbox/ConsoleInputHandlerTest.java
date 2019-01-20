package com.gigssandbox;

import com.gigssandbox.IO.console.ConsoleInputHandler;
import com.gigssandbox.entities.Band;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class ConsoleInputHandlerTest {
    private ConsoleInputHandler consoleHandler;

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Before
    public void setUp() {
        this.consoleHandler = new ConsoleInputHandler();
    }

    @Test
    public void shouldReadOneFromConsoleWhenOneIsEntered() {
        systemInMock.provideLines("1");
        int oneInteger = 1;

        int actualInt = consoleHandler.readInt();

        assertEquals(oneInteger, actualInt);
    }

    @Test
    public void shouldReadTestStringFromConsoleWhenTestStringIsEntered() {
        systemInMock.provideLines("test");
        String testString = "test";

        String actualString = consoleHandler.readString();

        assertEquals(testString, actualString);
    }

    @Test
    public void shouldReadTestStringAndSplitIntoStringArrayWhenStringHasDelimiters() {
        systemInMock.provideLines("Aladdin;Abu;Genie");
        String[] expectedArray = new String[]{"Aladdin", "Abu", "Genie"};

        String[] actualArray = consoleHandler.readStringAndSplit();

        assertArrayEquals(expectedArray, actualArray);
    }
}