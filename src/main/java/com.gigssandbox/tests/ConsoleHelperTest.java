package com.gigssandbox.tests;

import com.gigssandbox.ConsoleHelper;
import com.gigssandbox.GigsRepository;
import com.gigssandbox.UserInteraction;
import com.gigssandbox.entities.Band;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;


@RunWith(MockitoJUnitRunner.class)
public class ConsoleHelperTest {
    private ByteArrayOutputStream output;
    private PrintStream oldOut;
    private ConsoleHelper helper;
    private UserInteraction interaction;

    @Before
    public void setUp() {
        System.setIn(new ByteArrayInputStream("1 lorem ipsum is not actually a fish".getBytes()));
        oldOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/strings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper = new ConsoleHelper(properties);
        interaction = new UserInteraction(properties, helper, Mockito.mock(GigsRepository.class));
    }

    @After
    public void cleanUpStreams() {
        System.setIn(System.in);
        System.setOut(oldOut);
    }

    @Test
    public void shouldWriteStringToConsoleWhenMethodIsCalled() {
        String testString = "lorem ipsum is not actually a fish";
        helper.writeStringToConsole(testString);
        Assert.assertEquals(testString, output.toString().replaceAll("[\n\r]", ""));
    }

    @Test
    public void shouldWriteElementsToConsoleWhenCollectionIsNotEmpty() {
        Collection<Band> fakeBands = new ArrayList<>();
        fakeBands.add(Mockito.mock(Band.class));
        fakeBands.add(Mockito.mock(Band.class));
        String expected = fakeBands.stream().map(Band::toString).collect(Collectors.joining());
        helper.writeCollectionToConsole(fakeBands);
        Assert.assertEquals(expected, output.toString().replaceAll("[\n\r]", ""));
    }

    @Test
    public void shouldWriteThatResultIsEmptyWhenCollectionIsEmpty() {
        Collection<Band> fakeBands = new ArrayList<>();
        String expected = "Result is empty";
        helper.writeCollectionToConsole(fakeBands);
        Assert.assertEquals(expected, output.toString().replaceAll("[\n\r]", ""));
    }

    @Test
    public void shouldReadIntegerFromConsoleWhenMethodIsCalled() {
        int expectedInt = 1;
        int given = helper.readInt();
        Assert.assertEquals(expectedInt, given);
    }

    @Test
    public void shouldReadStringFromConsoleWhenMethodIsCalled() {
        String expectedString = "lorem ipsum is not actually a fish";
        String given = helper.readString();
        Assert.assertEquals(expectedString, given.replaceFirst("\\s+", ""));
    }


}
