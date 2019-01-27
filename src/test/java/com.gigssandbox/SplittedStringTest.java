package com.gigssandbox;

import com.gigssandbox.IO.SplittedString;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class SplittedStringTest {
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Test
    public void shouldGetArrayOfStringsFromSolidStringWhenElementSeparatorIsSemicolon() {
        String entityFieldsInSolidString = "text0 text1 text2 text3";
        String[] expectedArrayOfStrings = new String[]{"text0", "text1", "text2", "text3"};

        String[] actualArrayOfStrings = new SplittedString(entityFieldsInSolidString).value();

        assertArrayEquals(expectedArrayOfStrings, actualArrayOfStrings);
    }
}
