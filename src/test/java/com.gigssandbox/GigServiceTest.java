package com.gigssandbox;

import com.gigssandbox.entities.Gig;
import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.NoAppropriateGigsException;
import com.gigssandbox.services.GigService;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GigServiceTest {
    private GigService gigService;
    private  Map<Gig.Credentials, Gig> gigs;
    private Calendar gigDate;
    private String username;
    private User user;
    private String headliner;
    private Gig.Credentials credentials;

    @Before
    public void setUp() {
        this.gigService = new GigService();
        this.gigs = new HashMap<>();
        this.gigDate = new GregorianCalendar(2017, Calendar.SEPTEMBER, 10);
        this.username = "argalis";
        this.user = User.builder().username(username).build();
        this.headliner = "annisokay";
        this.credentials = new Gig.Credentials(headliner, gigDate);
    }

    @Test
    public void shouldAddUserToAttendeesWhenUserWantsToJoinGig() {
        Gig gig = Gig.builder().credentials(credentials).attendees(new HashSet<>()).build();
        gigs.put(credentials, gig);
        Whitebox.setInternalState(gigService, "gigs", gigs);

        assertDoesNotThrow(() -> {
            gigService.addUserToGig(user, headliner, gigDate);

            assertTrue(gigService.gigContainsUser(headliner, gigDate, user));
        });
    }

    @Test
    public void shouldRemoveUserFromAttendeesWhenUserWantsToLeaveGig() {
        Gig gig = Gig.builder().credentials(credentials).attendees(new HashSet<>()).build();
        gigs.put(credentials, gig);
        Whitebox.setInternalState(gigService, "gigs", gigs);

        assertDoesNotThrow(() -> {
                    gigService.removeUserFromGig(user, headliner, gigDate);

                    assertFalse(gigService.gigContainsUser(headliner, gigDate, user));
                }
        );
    }

    @Test
    public void shouldReturnTrueWhenUserAlreadyPresentAmongAttendees() {
        Collection<User> attendees = new HashSet<>();
        attendees.add(user);
        Gig gig = Gig.builder().credentials(credentials).attendees(attendees).build();
        gigs.put(credentials, gig);
        Whitebox.setInternalState(gigService, "gigs", gigs);

        boolean userPresentAmongAttendees = gigService.gigContainsUser(headliner, gigDate, user);

        assertTrue(userPresentAmongAttendees);
    }

    @Test
    public void shouldThrowNoAppropriateGigExceptionWhenThereAreNoGigsIdenticalToEnteredGigForJoining() {
        Whitebox.setInternalState(gigService, "gigs", gigs);

        assertThrows(NoAppropriateGigsException.class, () -> gigService.addUserToGig(user, headliner, gigDate));
    }

    @Test
    public void shouldThrowNoAppropriateGigExceptionWhenThereAreNoGigsIdenticalToEnteredGigForLeaving() {
        Whitebox.setInternalState(gigService, "gigs", gigs);

        assertThrows(NoAppropriateGigsException.class, () -> gigService.removeUserFromGig(user, headliner, gigDate));
    }
}
