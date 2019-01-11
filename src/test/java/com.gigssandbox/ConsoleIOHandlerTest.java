package com.gigssandbox;

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
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;


public class ConsoleIOHandlerTest {
    private ConsoleIOHandler consoleIOHandler;
    private ByteArrayOutputStream output;
    private PrintStream oldOut;

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Before
    public void setUp() {
        this.oldOut = System.out;
        this.output = new ByteArrayOutputStream();
        this.consoleIOHandler = new ConsoleIOHandler();
        System.setOut(new PrintStream(output));
    }

    @Test
    public void shouldWriteTestStringFromPropertiesWhenTestPropertyIsPassed() {
        String testStringKey = "test";
        String testStringValue = "This is test\n";

        consoleIOHandler.writeString(testStringKey);

        assertEquals(testStringValue, output.toString());
    }

    @Test
    public void shouldWriteElementsToConsoleWhenCollectionIsNotEmpty() {
        Collection<Band> fakeBands = new ArrayList<>();
        fakeBands.add(Mockito.mock(Band.class));
        fakeBands.add(Mockito.mock(Band.class));
        String expectedStringWithElementsOfTheGivenCollection = fakeBands.stream().map(s -> s.toString().concat("\n")).collect(Collectors.joining());

        consoleIOHandler.writeCollection(fakeBands);

        assertEquals(expectedStringWithElementsOfTheGivenCollection, output.toString());
    }

    @Test
    public void shouldWriteThatResultIsEmptyWhenCollectionIsEmpty() {
        Collection<Band> fakeBands = new ArrayList<>();
        String resultIsEmptyString = "Result is empty\n";

        consoleIOHandler.writeCollection(fakeBands);

        assertEquals(resultIsEmptyString, output.toString());
    }

    @Test
    public void shouldReadOneFromConsoleWhenOneIsEntered() {
        systemInMock.provideLines("1");
        int oneInteger = 1;

        int actualInt = consoleIOHandler.readInt();

        assertEquals(oneInteger, actualInt);
    }

    @Test
    public void shouldReadTestStringFromConsoleWhenTestStringIsEntered() {
        systemInMock.provideLines("test");
        String testString = "test";

        String actualString = consoleIOHandler.readString();

        assertEquals(testString, actualString);
    }

    @Test
    public void shouldGetArrayOfStringsFromSolidStringWhenElementSeparatorIsSemicolon() {
        systemInMock.provideLines("text0;text1;text2;text3");
        String[] expectedArrayOfStrings = new String[]{"text0", "text1", "text2", "text3"};

        String[] actualArrayOfStrings = consoleIOHandler.getEntityFieldsArray();

        assertArrayEquals(expectedArrayOfStrings, actualArrayOfStrings);
    }

    @After
    public void tearDown() {
        System.setOut(oldOut);
    }
}