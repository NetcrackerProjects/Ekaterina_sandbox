package com.gigssandbox;

import com.gigssandbox.IO.Output;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OutputTest {
    private Output output;
    private String outputContent;

    @Before
    public void setUp() {
        this.output = new Output() {
            @Override
            public void write(String text) {
                outputContent = text;
            }

            @Override
            public void write(Collection<?> collection) {

            }
        };

        this.outputContent = "";
    }

    @Test
    public void shouldWriteTestStringFromPropertiesWhenTestPropertyIsPassed() {
        String testStringKey = "test";
        String testStringValue = "This is test";

        output.writeStored(testStringKey);

        assertEquals(testStringValue, outputContent);
    }

    @Test
    public void shouldWriteRegistrationSuccessResponseValueWhenRegistrationCommandIsPassed() {
        String expectedOutput = "You have successfully registered";
        Response response = Response.REGISTRATION_SUCCESS;

        output.write(response);

        assertEquals(expectedOutput, outputContent);
    }
}
