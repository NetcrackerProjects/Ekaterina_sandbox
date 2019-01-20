package com.gigssandbox.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@EqualsAndHashCode
@Builder(toBuilder = true)
@Getter
public class Gig {
    private int id;
    private Band headlinerBand;
    private Band supportBand;
    private LocalDateTime date;
    private String location;
    private Collection<User> attendedUsers;
    private Collection<Community> attendedCommunities;

    void addAttendee(User user) {
        attendedUsers.add(user);
    }

    void removeAttendee(User user) {
        attendedUsers.remove(user);
    }

    @Override
    public String toString() {
        return " headlinerBandId: " + headlinerBand.getName() +
                " \n\tsupportBandId: " + supportBand.getName() +
                " \n\tdate: " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " \n\tlocation: " + location;
    }
}