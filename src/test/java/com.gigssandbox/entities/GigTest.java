package com.gigssandbox.entities;

import java.util.Collection;
import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GigTest {
    private Gig gig;
    private User user;

    @Before
    public void setUp() {
        this.gig = new Gig();
        this.user = User
                .builder()
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
        Collection<User> attendees = new HashSet<>();
        attendees.add(user);
        Whitebox.setInternalState(gig, "attendees", attendees);

        gig.remove(user);

        assertFalse(gig.getAttendees().contains(user));
    }
}
