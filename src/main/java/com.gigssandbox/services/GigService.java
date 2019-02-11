package com.gigssandbox.services;

import com.gigssandbox.entities.Gig;
import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.NoSuchGigException;
import java.util.Map;

public class GigService {
    private Map<String, Gig> gigs;

    public GigService(Map<String, Gig> gigs) {
        this.gigs = gigs;
    }

    public void addUserToGig(User user, String gigCredentials) throws NoSuchGigException {
        if (!gigs.containsKey(gigCredentials)) {
            throw new NoSuchGigException();
        }

        gigs.get(gigCredentials).add(user);
    }

    public void removeUserFromGig(User attendee, String gigCredentials) throws NoSuchGigException {
        if (!gigs.containsKey(gigCredentials)) {
            throw new NoSuchGigException();
        }

        gigs.get(gigCredentials).remove(attendee);
    }

    boolean gigContainsUser(String gigCredentials, User user) {
        return gigs.get(gigCredentials).contains(user);
    }
}
