package com.gigssandbox;

import com.gigssandbox.IO.Input;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class InputTest {
    @Test
    public void shouldGetArrayOfStringsFromSolidStringWhenElementSeparatorIsSemicolon() {
        String parametersInSolidString = "text0 text1 text2 text3";
        String[] expectedArrayOfStrings = new String[]{"text0", "text1", "text2", "text3"};

        Input input = new Input() {
            @Override
            public String[] receive() {
                return splitted(parametersInSolidString);
            }
        };
        String[] actualArrayOfStrings = input.receive();

        assertArrayEquals(expectedArrayOfStrings, actualArrayOfStrings);
    }
}
