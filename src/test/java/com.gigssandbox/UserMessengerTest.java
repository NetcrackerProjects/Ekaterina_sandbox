package com.gigssandbox;

import com.gigssandbox.entities.Band;
import org.junit.After;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;


@RunWith(MockitoJUnitRunner.class)
public class UserMessengerTest {
    private ByteArrayOutputStream output;
    private PrintStream oldOut;
    private UserMessenger userMessenger;

    @Before
    public void setUp() {
        oldOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        userMessenger = new UserMessenger();
    }

    @Test
    public void shouldWriteStringToConsoleWhenMethodIsCalled() {
        String testString = "lorem ipsum is not actually a fish";

        userMessenger.writeStringToConsole(testString);

        assertEquals(testString, output.toString().replaceAll("[\n\r]", ""));
    }

    @Test
    public void shouldWriteElementsToConsoleWhenCollectionIsNotEmpty() {
        Collection<Band> fakeBands = new ArrayList<>();
        fakeBands.add(Mockito.mock(Band.class));
        fakeBands.add(Mockito.mock(Band.class));
        String expected = fakeBands.stream().map(Band::toString).collect(Collectors.joining());

        userMessenger.writeCollectionToConsole(fakeBands);

        assertEquals(expected, output.toString().replaceAll("[\n\r]", ""));
    }

    @Test
    public void shouldWriteThatResultIsEmptyWhenCollectionIsEmpty() {
        Collection<Band> fakeBands = new ArrayList<>();
        String expected = "Result is empty";

        userMessenger.writeCollectionToConsole(fakeBands);

        assertEquals(expected, output.toString().replaceAll("[\n\r]", ""));
    }

    @Test
    public void shouldReadIntegerFromConsoleWhenMethodIsCalled() {
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        int expectedInt = 1;

        int actual = userMessenger.readInt();

        assertEquals(expectedInt, actual);
    }

    @Test
    public void shouldReadStringFromConsoleWhenMethodIsCalled() {
        System.setIn(new ByteArrayInputStream("lorem ipsum is not actually a fish".getBytes()));
        String expectedString = "lorem ipsum is not actually a fish";

        String actual = userMessenger.readString();

        assertEquals(expectedString, actual);
    }

    @Test
    public void shouldWriteStringFromPropertiesWhenPropertyNameGiven() {
        String expectedString = "This is test";

        userMessenger.sendMessage("test");

        assertEquals(expectedString, output.toString().replaceAll("[\n\r]", ""));
    }

    @Test
    public void shouldWriteCorrectCommandWhenInputIsCorrect() {
        System.setIn(new ByteArrayInputStream("3".getBytes()));
        Command expectedCommand = Command.ADD_GIG;

        Command actualCommand = userMessenger.nextCommand();

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void shouldWriteUnsupportedWhenInputIsInorrect() {
        System.setIn(new ByteArrayInputStream("6".getBytes()));
        Command expectedCommand = Command.UNSUPPORTED;

        Command actualCommand = userMessenger.nextCommand();

        assertEquals(expectedCommand, actualCommand);
    }

    @After
    public void cleanUpStreams() {
        System.setIn(System.in);
        System.setOut(oldOut);
    }

}
