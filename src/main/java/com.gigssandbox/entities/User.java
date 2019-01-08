package com.gigssandbox.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode
@Builder(toBuilder = true)
@Getter
@Setter
public class User {
    private int id;
    private String name;
    private String surname;
    private String username;
    private int passwordHash;
    private int age;
    private char gender;
    private String email;
    private Set<Integer> vizitedGigsIds;
    private int rating;
    private LocalDateTime registrationDate;
    private String community;
    private boolean isCommunityAdmin;

    @Override
    public String toString() {
        return "User: " +
                "\n\tname: " + name +
                "\n\tsurname: " + surname +
                "\n\tusername: " + username +
                "\n\tage: " + age +
                "\n\tgender: " + gender +
                "\n\trating: " + rating;
    }
}
