package com.gigssandbox;

import com.gigssandbox.IO.console.ConsoleOutput;
import com.gigssandbox.entities.Band;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class ConsoleOutputTest {
    private ConsoleOutput consoleOutput;
    private ByteArrayOutputStream output;
    private PrintStream oldOutput;

    @Before
    public void setUp() {
        this.oldOutput = System.out;
        this.output = new ByteArrayOutputStream();
        this.consoleOutput = new ConsoleOutput();
        System.setOut(new PrintStream(output));
    }

    @Test
    public void shouldWriteTestStringToConsoleWhenStringIsPassed() {
        String testString = "test\n";
        String stringToBeWritten = "test";

        consoleOutput.write(stringToBeWritten);

        assertEquals(testString, output.toString());
    }

    @Test
    public void shouldWriteElementsToConsoleWhenCollectionIsNotEmpty() {
        Collection<Band> fakeBands = new ArrayList<>();
        fakeBands.add(Mockito.mock(Band.class));
        fakeBands.add(Mockito.mock(Band.class));
        String expectedStringWithElementsOfTheGivenCollection = fakeBands.stream().map(s -> s.toString().concat("\n")).collect(Collectors.joining());

        consoleOutput.write(fakeBands);

        assertEquals(expectedStringWithElementsOfTheGivenCollection, output.toString());
    }

    @Test
    public void shouldWriteThatResultIsEmptyWhenCollectionIsEmpty() {
        Collection<Band> fakeBands = Collections.emptyList();
        String expectedOutput = "Result is empty\n";

        consoleOutput.write(fakeBands);

        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void shouldWriteKeysAndValuesWhenPassedMapIsNotEmpty() {
        Map<String, String> fakeParameters = new HashMap<>();
        fakeParameters.put("0", "Bulbasaur");
        fakeParameters.put("1", "Pikachu");
        fakeParameters.put("2", "Clefairy");
        String expectedOutput = "Bulbasaur\nPikachu\nClefairy\n";

        consoleOutput.write(fakeParameters);

        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void shouldWriteThatResultIsEmptyWhenPassedMapIsEmpty() {
        Map<String, String> emptyMap = Collections.emptyMap();
        String expectedOutput = "Result is empty\n";

        consoleOutput.write(emptyMap);

        assertEquals(expectedOutput, output.toString());
    }

    @After
    public void tearDown() {
        System.setOut(oldOutput);
    }
}
