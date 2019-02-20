package com.gigssandbox.response;

import com.gigssandbox.Result;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseFactoryTest {
    private ResponseFactory responseFactory;

    @Before
    public void setUp() {
        this.responseFactory = new ResponseFactory();
    }

    @Test
    public void shouldCreateRegistrationResponseWhenRegistrationResultIsPassed() {
        Result givenResult = Result.REGISTRATION_SUCCESS;
        Response expectedResponse = new Response("You have successfully registered\n");

        Response actualResponse = responseFactory.create(givenResult);

        assertEquals(expectedResponse, actualResponse);
    }

}
