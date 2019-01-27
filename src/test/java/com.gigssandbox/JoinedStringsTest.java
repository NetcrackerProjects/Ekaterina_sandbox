package com.gigssandbox;

import com.gigssandbox.IO.JoinedStrings;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class JoinedStringsTest {

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Test
    public void shouldCreateSolidStringWhenTwoStringsArePassed() {
        String solidString = "aaa ,bbb";

        String actualString = new JoinedStrings("aaa", ",bbb").value();

        assertEquals(solidString, actualString);
    }

    @Test
    public void shouldReturnSameStringWhenOneStringIsPassed() {
        String sameString = "lorem ipsum";

        String actualString = new JoinedStrings("lorem ipsum").value();

        assertEquals(sameString, actualString);
    }

}
