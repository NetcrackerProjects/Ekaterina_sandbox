package com.gigssandbox;

import com.gigssandbox.IO.StringTransformer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class StringTransformerTest {
    private StringTransformer stringTransformer;
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Before
    public void setUp() {
        stringTransformer = new StringTransformer();
    }

    @Test
    public void shouldCreateSolidStringWhenTwoStringsArePassed() {
        String solidString = "aaa ,bbb";

        String actualString = stringTransformer.createSolidString("aaa", ",bbb");

        assertEquals(solidString, actualString);
    }

    @Test
    public void shouldReturnSameStringWhenOneStringIsPassed() {
        String sameString = "lorem ipsum";

        String actualString = stringTransformer.createSolidString("lorem ipsum");

        assertEquals(sameString, actualString);
    }

    @Test
    public void shouldGetArrayOfStringsFromSolidStringWhenElementSeparatorIsSemicolon() {
        String entityFieldsInSolidString = "text0;text1;text2;text3";
        String[] expectedArrayOfStrings = new String[]{"text0", "text1", "text2", "text3"};

        String[] actualArrayOfStrings = stringTransformer.split(entityFieldsInSolidString);

        assertArrayEquals(expectedArrayOfStrings, actualArrayOfStrings);
    }
}
