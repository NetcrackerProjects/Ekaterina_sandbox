package com.gigssandbox;

import org.junit.Assert;
import org.junit.Test;

public class ResponseFactoryTest {
    @Test
    public void shouldCreateHelpResponseWhenHelpResultIsPassed() {
        Result givenResult = Result.HELP;
        Response expectedResponse = new Response("help");

        Response actualResponse = ResponseFactory.createResponseFrom(givenResult);

        Assert.assertEquals(expectedResponse, actualResponse);
    }

}
