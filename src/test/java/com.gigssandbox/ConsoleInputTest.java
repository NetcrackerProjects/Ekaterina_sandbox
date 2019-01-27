package com.gigssandbox;

import com.gigssandbox.IO.console.ConsoleInput;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class ConsoleInputTest {
    private ConsoleInput consoleInput;

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Before
    public void setUp() {
        this.consoleInput = new ConsoleInput();
    }

    @Test
    public void shouldReadTestStringAndSplitIntoStringArrayWhenStringHasDelimiters() {
        systemInMock.provideLines("Aladdin Abu Genie");
        String[] expectedArray = new String[]{"Aladdin", "Abu", "Genie"};

        String[] actualArray = consoleInput.nextStringSplitted();

        assertArrayEquals(expectedArray, actualArray);
    }
}