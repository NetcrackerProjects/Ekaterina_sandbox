package com.gigssandbox;

import com.gigssandbox.IO.ValuesStore;
import org.junit.Assert;
import org.junit.Test;

public class ValuesStoreTest {

    @Test
    public void shouldGetTestPropertyWhenUserAskedForTestValue() {
        String expectedPropertyContent = "This is test";

        String actualPropertyContent = new ValuesStore().stored("test");

        Assert.assertEquals(expectedPropertyContent, actualPropertyContent);
    }
}
