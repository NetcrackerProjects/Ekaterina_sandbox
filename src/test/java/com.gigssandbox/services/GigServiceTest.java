package com.gigssandbox.services;

import com.gigssandbox.entities.Gig;
import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.NoSuchGigException;
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
    private String headliner;
    private String gigDate;
    private String gigCredentials;

    @Before
    public void setUp() {
        this.gigs = new HashMap<>();
        String username = "argalis";
        this.headliner = "annisokay";
        this.gigDate = "2017-04-10";
        this.gigCredentials = headliner.concat(":").concat(gigDate);

        this.user = User.builder()
                .username(username)
                .build();

        this.gig = Gig.builder()
                .headliner(headliner)
                .gigDate(gigDate)
                .attendees(new HashSet<>())
                .build();

        this.gigs = new HashMap<>();
        this.gigService = new GigService(gigs);
    }

    @Test
    public void shouldAddUserToAttendeesWhenUserWantsToJoinGig() throws Exception {
        this.gigs.put(gigCredentials, gig);

        gigService.addUserToGig(user, headliner, gigDate);

        assertTrue(gigService.gigContainsUser(gigCredentials, user));
    }

    @Test
    public void shouldRemoveUserFromAttendeesWhenUserWantsToLeaveGig() throws Exception {
        this.gigs.put(gigCredentials, gig);

        gigService.removeUserFromGig(user, headliner, gigDate);

        assertFalse(gigService.gigContainsUser(gigCredentials, user));
    }

    @Test
    public void shouldReturnTrueWhenUserAlreadyPresentAmongAttendees() {
        this.gig.add(user);
        this.gigs.put(gigCredentials, gig);

        boolean userPresentAmongAttendees = gigService.gigContainsUser(gigCredentials, user);

        assertTrue(userPresentAmongAttendees);
    }

    @Test(expected = NoSuchGigException.class)
    public void shouldThrowWhenThereAreNoGigsIdenticalToEnteredGigForJoining() throws Exception {
        gigService.addUserToGig(user, headliner, gigDate);
    }

    @Test(expected = NoSuchGigException.class)
    public void shouldThrowWhenThereAreNoGigsIdenticalToEnteredGigForLeaving() throws Exception {
        gigService.removeUserFromGig(user, headliner, gigDate);
    }
}
