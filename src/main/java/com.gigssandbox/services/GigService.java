package com.gigssandbox.services;

import com.gigssandbox.entities.Gig;
import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.NoAppropriateGigException;
import java.util.Map;

public class GigService {
    private Map<String, Gig> gigs;

    public GigService(Map<String, Gig> gigs) {
        this.gigs = gigs;
    }

    public void addUserToGig(User user, String gigCredentials) throws NoAppropriateGigException {
        if (!gigs.containsKey(gigCredentials)) {
            throw new NoAppropriateGigException();
        }

        gigs.get(gigCredentials).add(user);
    }

    public void removeUserFromGig(User attendee, String gigCredentials) throws NoAppropriateGigException {
        if (!gigs.containsKey(gigCredentials)) {
            throw new NoAppropriateGigException();
        }

        gigs.get(gigCredentials).remove(attendee);
    }

    boolean gigContainsUser(String gigCredentials, User user) {
        return gigs.get(gigCredentials).contains(user);
    }
}
