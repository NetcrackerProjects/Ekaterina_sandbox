package com.gigssandbox;

import com.gigssandbox.entities.Gig;
import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.NoAppropriateGigsException;
import com.gigssandbox.services.GigService;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
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

    @Before
    public void setUp() {
        this.gigService = new GigService();
    }

    @Test
    public void shouldAddUserToAttendeesWhenUserWantsToJoinGig() {
        User user = User.builder().username("slaves").build();
        String headliner = "Attila";
        Calendar gigDate = new GregorianCalendar(2018, Calendar.OCTOBER, 14);

        Gig.Credentials credentials = new Gig.Credentials(headliner, gigDate);
        Gig gig = Gig.builder().credentials(credentials).attendees(new HashSet<>()).build();
        Map<Gig.Credentials, Gig> gigs = new HashMap<>();
        gigs.put(credentials, gig);
        Whitebox.setInternalState(gigService, "gigs", gigs);

        assertDoesNotThrow(() -> {

            gigService.addUserToGig(user, headliner, gigDate);

            assertTrue(gigService.gigContainsUser(headliner, gigDate, user));
        });
    }

    @Test
    public void shouldRemoveUserFromAttendeesWhenUserWantsToLeaveGig() {
        User user = User.builder().username("nothing").build();
        String headliner = "but thieves";
        Calendar gigDate = new GregorianCalendar(2017, Calendar.SEPTEMBER, 10);

        Gig.Credentials credentials = new Gig.Credentials(headliner, gigDate);
        Gig gig = Gig.builder().credentials(credentials).attendees(new HashSet<>()).build();
        Map<Gig.Credentials, Gig> gigs = new HashMap<>();
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
        String username = "animal";
        User user = User.builder().username(username).build();
        String headliner = "the_fever_333";
        Calendar gigDate = new GregorianCalendar(2015, Calendar.JANUARY, 1);

        Collection<User> attendees = new HashSet<>();
        attendees.add(user);

        Gig.Credentials credentials = new Gig.Credentials(headliner, gigDate);
        Gig gig = Gig.builder().credentials(credentials).attendees(attendees).build();
        Map<Gig.Credentials, Gig> gigs = new HashMap<>();
        gigs.put(credentials, gig);
        Whitebox.setInternalState(gigService, "gigs", gigs);

        boolean userPresentAmongAttendees = gigService.gigContainsUser(headliner, gigDate, user);

        assertTrue(userPresentAmongAttendees);
    }


    @Test
    public void shouldThrowNoAppropriateGigExceptionWhenThereAreNoGigsIdenticalToEnteredGigForJoining() {
        String username = "argalis";
        User user = User.builder().username(username).build();
        String headliner = "annisokay";
        Calendar gigDate = new GregorianCalendar(2019, Calendar.APRIL, 16);
        Whitebox.setInternalState(gigService, "gigs", Collections.emptyMap());

        assertThrows(NoAppropriateGigsException.class, () -> gigService.addUserToGig(user, headliner, gigDate));
    }

    @Test
    public void shouldThrowNoAppropriateGigExceptionWhenThereAreNoGigsIdenticalToEnteredGigForLeaving() {
        String username = "openbill";
        User user = User.builder().username(username).build();
        String headliner = "dance gavin dance";
        Calendar gigDate = new GregorianCalendar(2017, Calendar.APRIL, 10);
        Whitebox.setInternalState(gigService, "gigs", Collections.emptyMap());

        assertThrows(NoAppropriateGigsException.class, () -> gigService.removeUserFromGig(user, headliner, gigDate));
    }
}
