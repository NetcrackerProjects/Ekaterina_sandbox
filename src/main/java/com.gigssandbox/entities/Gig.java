package com.gigssandbox.entities;

import java.util.Calendar;
import java.util.HashSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collection;

@EqualsAndHashCode
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class Gig {
    private int id;
    private Credentials credentials;
    private Band supportBand;
    private String location;
    private Collection<User> attendees;

    public Gig() {
        this.attendees = new HashSet<>();
    }

    public void add(User attendee) {
        attendees.add(attendee);
    }

    public void remove(User attendee) {
        attendees.remove(attendee);
    }

    public boolean contains(User attendee) {
        return attendees.contains(attendee);
    }

    @Override
    public String toString() {
        return " headlinerBandId: " + credentials.getHeadliner() +
                " \n\tsupportBandId: " + supportBand.getName() +
                " \n\tdate: " + credentials.getGigDate() +
                " \n\tlocation: " + location;
    }

    @EqualsAndHashCode
    @Getter
    public static class Credentials {
        private String headliner;
        private Calendar gigDate;

        public Credentials(String headliner, Calendar gigDate) {
            this.headliner = headliner;
            this.gigDate = gigDate;
        }
    }
}