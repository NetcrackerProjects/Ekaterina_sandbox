package com.gigssandbox.entities;

import java.time.LocalDateTime;
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
public class Community {
    private int id;
    private String name;
    private LocalDateTime creationDate;
    private Collection<Gig> vizitedGigs;
    private Collection<User> members;
    private String chatroomName;

    Community() {
        this.vizitedGigs = new HashSet<>();
        this.members = new HashSet<>();
    }

    public void add(User user) {
        members.add(user);
    }

    public void remove(User user) {
        members.remove(user);
    }
}