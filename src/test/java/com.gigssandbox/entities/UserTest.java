package com.gigssandbox.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {
    private User user;

    @Before
    public void setUp() {
        this.user = User
                .builder()
                .username("Hedgehog")
                .build();
    }

    @Test
    public void shouldReturnTrueWhenUserIsAlreadyLoggedIn() {
        boolean expectedValue = true;
        user.setLoggedIn(expectedValue);

        boolean actualValue = user.isLoggedIn();

        assertEquals(expectedValue, actualValue);
    }
}
