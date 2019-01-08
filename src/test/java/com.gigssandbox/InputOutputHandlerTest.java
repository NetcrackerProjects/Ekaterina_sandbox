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

public class InputOutputHandlerTest {
    private InputOutputHandler inputOutputHandler;
    private ByteArrayOutputStream output;
    private PrintStream oldOut;

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Before
    public void setUp() {
        this.oldOut = System.out;
        this.output = new ByteArrayOutputStream();
        this.inputOutputHandler = new InputOutputHandler();
        System.setOut(new PrintStream(output));
    }

    @Test
    public void shouldWriteTestStringFromPropertiesWhenTestPropertyIsPassed() {
        String testStringKey = "test";
        String testStringValue = "This is test\n";

        inputOutputHandler.writePreloadedString(testStringKey);

        assertEquals(testStringValue, output.toString());
    }

    @Test
    public void shouldWriteElementsToConsoleWhenCollectionIsNotEmpty() {
        Collection<Band> fakeBands = new ArrayList<>();
        fakeBands.add(Mockito.mock(Band.class));
        fakeBands.add(Mockito.mock(Band.class));
        String expectedStringWithElementsOfTheGivenCollection = fakeBands.stream().map(s -> s.toString().concat("\n")).collect(Collectors.joining());

        inputOutputHandler.writeCollection(fakeBands);

        assertEquals(expectedStringWithElementsOfTheGivenCollection, output.toString());
    }

    @Test
    public void shouldWriteThatResultIsEmptyWhenCollectionIsEmpty() {
        Collection<Band> fakeBands = new ArrayList<>();
        String resultIsEmptyString = "Result is empty\n";

        inputOutputHandler.writeCollection(fakeBands);

        assertEquals(resultIsEmptyString, output.toString());
    }

    @Test
    public void shouldReadOneFromConsoleWhenOneIsEntered() {
        systemInMock.provideLines("1");
        int oneInteger = 1;

        int actualInt = inputOutputHandler.readInt();

        assertEquals(oneInteger, actualInt);
    }

    @Test
    public void shouldReadTestStringFromConsoleWhenTestStringIsEntered() {
        systemInMock.provideLines("test");
        String testString = "test";

        String actualString = inputOutputHandler.readString();

        assertEquals(testString, actualString);
    }

    @Test
    public void shouldGetArrayOfStringsFromSolidStringWhenElementSeparatorIsSemicolon() {
        systemInMock.provideLines("text0;text1;text2;text3");
        String[] expectedArrayOfStrings = new String[]{"text0", "text1", "text2", "text3"};

        String[] actualArrayOfStrings = inputOutputHandler.getEntityFieldsArray();

        assertArrayEquals(expectedArrayOfStrings, actualArrayOfStrings);
    }

    @After
    public void tearDown() {
        System.setOut(oldOut);
    }
}