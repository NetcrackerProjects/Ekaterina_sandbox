package com.gigssandbox.services;

import com.gigssandbox.entities.Gig;
import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.NoAppropriateGigsException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GigService {
    private Map<Gig.Credentials, Gig> gigs;

    public GigService() {
        this.gigs = new HashMap<>();
    }

    public void addUserToGig(User user, String headliner, Calendar gigDate) throws NoAppropriateGigsException {
        try {
        gigs.get(new Gig.Credentials(headliner, gigDate)).add(user);

        } catch (NullPointerException e) {
            throw new NoAppropriateGigsException();
        }
    }

    public void removeUserFromGig(User attendee, String headliner, Calendar gigDate) throws NoAppropriateGigsException {
        try {
            gigs.get(new Gig.Credentials(headliner, gigDate)).remove(attendee);
        } catch (NullPointerException e) {
            throw new NoAppropriateGigsException();
        }
    }

    public boolean gigContainsUser(String headliner, Calendar gigDate, User user) {
        return gigs.get(new Gig.Credentials(headliner, gigDate)).contains(user);
    }
}
