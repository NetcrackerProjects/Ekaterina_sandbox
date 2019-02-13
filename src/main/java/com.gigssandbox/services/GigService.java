package com.gigssandbox.services;

import com.gigssandbox.entities.Gig;
import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.IncorrectDateException;
import com.gigssandbox.exceptions.NoSuchGigException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

public class GigService {
    private Map<String, Gig> gigs;

    public GigService(Map<String, Gig> gigs) {
        this.gigs = gigs;
    }

    public void addUserToGig(User user, String headliner, String gigDate) throws NoSuchGigException, IncorrectDateException {
        String gigCredentials = createGigCredentials(headliner, gigDate);

        validate(gigDate);

        if (!gigs.containsKey(gigCredentials)) {
            throw new NoSuchGigException();
        }

        gigs.get(gigCredentials).add(user);
    }

    public void removeUserFromGig(User attendee, String headliner, String gigDate) throws NoSuchGigException, IncorrectDateException {
        String gigCredentials = createGigCredentials(headliner, gigDate);

        validate(gigDate);

        if (!gigs.containsKey(gigCredentials)) {
            throw new NoSuchGigException();
        }

        gigs.get(gigCredentials).remove(attendee);
    }

    private void validate(String date) throws IncorrectDateException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        format.setLenient(false);

        try {
            format.parse(date);

        } catch (ParseException e) {
            throw new IncorrectDateException();
        }
    }

    boolean gigContainsUser(String gigCredentials, User user) {
        return gigs.get(gigCredentials).contains(user);
    }

    private String createGigCredentials(String headliner, String date) {
        return headliner.concat(":").concat(date);
    }
}
