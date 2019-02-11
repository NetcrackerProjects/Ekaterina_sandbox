package com.gigssandbox.entities;

import java.util.Collection;
import java.util.HashSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class Gig {
    private int id;
    private String credentials;
    private String support;
    private String location;
    private Collection<User> attendees;

    Gig() {
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
}