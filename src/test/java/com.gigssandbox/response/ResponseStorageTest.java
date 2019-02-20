package com.gigssandbox.response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseStorageTest {
    @Test
    public void shouldGetTestPropertyWhenUserAskedForTestValue() {
        String expectedText = "This is test";

        String actualText = new ResponseStorage().loadText("test");

        assertEquals(expectedText, actualText);
    }
}
