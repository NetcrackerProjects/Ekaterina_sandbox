package com.gigssandbox;

import com.gigssandbox.IO.console.ConsoleOutput;
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

    @Before
    public void setUp() {
        this.oldOutput = System.out;
        this.output = new ByteArrayOutputStream();
        this.consoleOutput = new ConsoleOutput();
        System.setOut(new PrintStream(output));
    }

    @Test
    public void shouldWriteRegistrationSuccessResponseValueWhenRegistrationCommandIsPassed() {
        String expectedOutput = "You have successfully registered\n";
        Response response = ResponseFactory.create(Result.REGISTRATION_SUCCESS);

        consoleOutput.send(response);

        assertEquals(expectedOutput, output.toString());
    }

    @After
    public void tearDown() {
        System.setOut(oldOutput);
    }
}
