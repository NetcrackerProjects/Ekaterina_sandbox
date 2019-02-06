package com.gigssandbox;

import com.gigssandbox.response.Response;
import com.gigssandbox.response.ResponseFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseFactoryTest {
    @Test
    public void shouldCreateRegistrationResponseWhenRegistrationResultIsPassed() {
        Result givenResult = Result.REGISTRATION_SUCCESS;
        Response expectedResponse = new Response("You have successfully registered");

        Response actualResponse = ResponseFactory.create(givenResult);

        assertEquals(expectedResponse, actualResponse);
    }

}
