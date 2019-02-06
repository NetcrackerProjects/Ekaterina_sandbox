package com.gigssandbox;

import com.gigssandbox.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private User user;

    @Before
    public void setUp() {
        this.user = User.builder().username("Hedgehog").build();
    }

    @Test
    public void shouldReturnTrueWhenUserIsAlreadyLoggedIn() {
        boolean expectedValue = true;
        user.setLoggedIn(expectedValue);

        boolean actualValue = user.isLoggedIn();

        Assert.assertEquals(expectedValue, actualValue);
    }
}