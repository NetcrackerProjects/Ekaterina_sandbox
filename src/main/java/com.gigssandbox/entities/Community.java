package com.gigssandbox.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;

@EqualsAndHashCode
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Community {
    private int id;
    private String name;
    private LocalDateTime creationDate;
    private Collection<Gig> vizitedGigs;
    private Collection<User> members;
    private String chatroomName;

    void addUser(User user) {
        members.add(user);
    }

    void removeUser(User user) {
        members.remove(user);
    }
}