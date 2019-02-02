package com.gigssandbox;

import com.gigssandbox.IO.ResponseStore;
import org.junit.Assert;
import org.junit.Test;

public class ResponseStoreTest {
    @Test
    public void shouldGetTestPropertyWhenUserAskedForTestValue() {
        String expectedPropertyContent = "This is test";

        String actualPropertyContent = new ResponseStore().stored("test");

        Assert.assertEquals(expectedPropertyContent, actualPropertyContent);
    }
}
