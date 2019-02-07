package com.gigssandbox;

import com.gigssandbox.entities.Gig;
import com.gigssandbox.entities.User;
import java.util.Collection;
import java.util.HashSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

public class GigTest {
    private Gig gig;

    @Before
    public void setUp() {
        this.gig = new Gig();
    }

    @Test
    public void shouldAddUserToGigWhenUserWantsToAttendGig() {
        User user = User.builder().username("windvent").build();

        gig.add(user);

        Assert.assertTrue(gig.getAttendees().contains(user));
    }

    @Test
    public void shouldRemoveUserFromGigWhenUserWantsToLeaveGig() {
        User user = User.builder().username("immortalis").build();
        Collection<User> attendees = new HashSet<>();
        attendees.add(user);
        Whitebox.setInternalState(gig, "attendees", attendees);

        gig.remove(user);

        Assert.assertFalse(gig.getAttendees().contains(user));
    }
}
