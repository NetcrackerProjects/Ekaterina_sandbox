package com.gigssandbox.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private int passwordHash;
    private boolean loggedIn;
    private Collection<Gig> vizitedGigs;
    private int rating;
    private LocalDateTime registrationDate;
    private boolean isAdmin;
    private boolean isCommunityAdmin;
}
