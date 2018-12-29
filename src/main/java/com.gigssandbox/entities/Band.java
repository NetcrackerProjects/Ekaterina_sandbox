package com.gigssandbox.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;


@EqualsAndHashCode
@Builder(toBuilder = true)
@Getter
@Setter
public class Band {
    private int id;
    private String name;
    private Collection<String> members;
    private String city;
    private Short creationYear;
    private String genre;

    @Override
    public String toString() {
        return name + "\n" +
                "\tmembers: " + String.join(", ", members) +
                "\n\tcity: " + city +
                "\n\tdate of creation: " + creationYear +
                "\n\tgenre: " + genre;
    }
}