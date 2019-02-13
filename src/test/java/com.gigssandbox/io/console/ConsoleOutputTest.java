package com.gigssandbox.io.console;

import com.gigssandbox.Result;
import com.gigssandbox.response.Response;
import com.gigssandbox.response.ResponseFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class ConsoleOutputTest {
    private ConsoleOutput consoleOutput;
    private ByteArrayOutputStream output;
    private PrintStream oldOutput;
    private ResponseFactory responseFactory;

    @Before
    public void setUp() {
        this.oldOutput = System.out;
        this.output = new ByteArrayOutputStream();
        this.consoleOutput = new ConsoleOutput();
        this.responseFactory = new ResponseFactory();
        System.setOut(new PrintStream(output));
    }

    @Test
    public void shouldWriteRegistrationSuccessTextWhenRegistrationCommandIsPassed() {
        String expectedOutput = "You have successfully registered\n";
        Response response = responseFactory.create(Result.REGISTRATION_SUCCESS);

        consoleOutput.send(response);

        assertEquals(expectedOutput, output.toString());
    }

    @After
    public void tearDown() {
        System.setOut(oldOutput);
    }
}
