package com.gigssandbox;

import com.gigssandbox.IO.console.ConsoleOutputHandler;
import com.gigssandbox.entities.Band;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class ConsoleOutputHandlerTest {
    private ConsoleOutputHandler consoleOutputHandler;
    private ByteArrayOutputStream output;
    private PrintStream oldOutput;

    @Before
    public void setUp() {
        this.oldOutput = System.out;
        this.output = new ByteArrayOutputStream();
        this.consoleOutputHandler = new ConsoleOutputHandler();
        System.setOut(new PrintStream(output));
    }

    @Test
    public void shouldWriteTestStringFromPropertiesWhenTestPropertyIsPassed() {
        String testStringKey = "test";
        String testStringValue = "This is test\n";

        consoleOutputHandler.writePreloaded(testStringKey);

        assertEquals(testStringValue, output.toString());
    }

    @Test
    public void shouldWriteTestStringToConsoleWhenStringIsPassed() {
        String testString = "test\n";
        String stringToBeWritten = "test";

        consoleOutputHandler.write(stringToBeWritten);

        assertEquals(testString, output.toString());
    }

    @Test
    public void shouldWriteElementsToConsoleWhenCollectionIsNotEmpty() {
        Collection<Band> fakeBands = new ArrayList<>();
        fakeBands.add(Mockito.mock(Band.class));
        fakeBands.add(Mockito.mock(Band.class));
        String expectedStringWithElementsOfTheGivenCollection = fakeBands.stream().map(s -> s.toString().concat("\n")).collect(Collectors.joining());

        consoleOutputHandler.write(fakeBands);

        assertEquals(expectedStringWithElementsOfTheGivenCollection, output.toString());
    }

    @Test
    public void shouldWriteThatResultIsEmptyWhenCollectionIsEmpty() {
        Collection<Band> fakeBands = new ArrayList<>();
        String resultIsEmptyString = "Result is empty\n";

        consoleOutputHandler.write(fakeBands);

        assertEquals(resultIsEmptyString, output.toString());
    }

    @Test
    public void shouldWriteKeysAndValuesWhenRespondMessageContentIsNotEmpty() {
        Map<String, String> fakeParameters = new HashMap<>();
        fakeParameters.put("0", "Bulbasaur");
        fakeParameters.put("1", "Pikachu");
        fakeParameters.put("2", "Clefairy");
        String expectedOutput = "Bulbasaur\nPikachu\nClefairy\n";

        consoleOutputHandler.write(fakeParameters);

        assertEquals(expectedOutput, output.toString());
    }

    @After
    public void tearDown() {
        System.setOut(oldOutput);
    }
}
