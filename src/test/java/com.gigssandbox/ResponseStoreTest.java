package com.gigssandbox;

import com.gigssandbox.response.ResponseStore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseStoreTest {
    @Test
    public void shouldGetTestPropertyWhenUserAskedForTestValue() {
        String expectedPropertyContent = "This is test";

        String actualPropertyContent = new ResponseStore().loadText("test");

        assertEquals(expectedPropertyContent, actualPropertyContent);
    }
}
