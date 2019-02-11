package com.gigssandbox.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GigTest {
    private Gig gig;
    private User user;

    @Before
    public void setUp() {
        this.gig = new Gig();
        this.user = User.builder()
                .username("windvent")
                .build();
    }

    @Test
    public void shouldAddUserToGigWhenUserWantsToAttendGig() {
        gig.add(user);

        assertTrue(gig.getAttendees().contains(user));
    }

    @Test
    public void shouldRemoveUserFromGigWhenUserWantsToLeaveGig() {
        gig.add(user);

        gig.remove(user);

        assertFalse(gig.getAttendees().contains(user));
    }
}
