package com.gigssandbox.services;

import com.gigssandbox.entities.Gig;
import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.NoAppropriateGigException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GigServiceTest {
    private GigService gigService;
    private  Map<String, Gig> gigs;
    private User user;
    private Gig gig;
    private String gigCredentials;

    @Before
    public void setUp() {
        this.gigs = new HashMap<>();
        String username = "argalis";
        this.gigCredentials = "annisokay:2017-04-10";

        this.user = User
                .builder()
                .username(username)
                .build();

        this.gig = Gig
                .builder()
                .credentials(gigCredentials)
                .attendees(new HashSet<>())
                .build();

        this.gigs = new HashMap<>();
        this.gigService = new GigService(gigs);
    }

    @Test
    public void shouldAddUserToAttendeesWhenUserWantsToJoinGig() throws Exception {
        gigs.put(gigCredentials, gig);

        gigService.addUserToGig(user, gigCredentials);

        assertTrue(gigService.gigContainsUser(gigCredentials, user));
    }

    @Test
    public void shouldRemoveUserFromAttendeesWhenUserWantsToLeaveGig() throws Exception {
        gigs.put(gigCredentials, gig);

        gigService.removeUserFromGig(user, gigCredentials);

        assertFalse(gigService.gigContainsUser(gigCredentials, user));
    }

    @Test
    public void shouldReturnTrueWhenUserAlreadyPresentAmongAttendees() {
        Collection<User> attendees = new HashSet<>();
        attendees.add(user);
        //todo attendees DI
        Gig gig = Gig.builder().credentials(gigCredentials).attendees(attendees).build();
        gigs.put(gigCredentials, gig);

        boolean userPresentAmongAttendees = gigService.gigContainsUser(gigCredentials, user);

        assertTrue(userPresentAmongAttendees);
    }

    @Test(expected = NoAppropriateGigException.class)
    public void shouldThrowWhenThereAreNoGigsIdenticalToEnteredGigForJoining() throws Exception {

        gigService.addUserToGig(user, gigCredentials);
    }

    @Test(expected = NoAppropriateGigException.class)
    public void shouldThrowWhenThereAreNoGigsIdenticalToEnteredGigForLeaving() throws Exception {
        gigService.removeUserFromGig(user, gigCredentials);
    }
}
