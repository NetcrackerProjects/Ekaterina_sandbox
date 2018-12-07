package com.gigssandbox.entities;

import lombok.*;

import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Band {
    private int id;
    private String name;
    private List<String> members;
    private String city;
    private Short createYear;
    private String genre;

    @Override
    public String toString() {
        return name + "\n" +
                "\tmembers: " + rearrangeMembersToString() +
                "\n\tcity: " + city +
                "\n\tdate of creation: " + createYear +
                "\n\tgenre: " + genre;
    }

    public String rearrangeMembersToString() {
        StringBuilder builder = new StringBuilder();
        members.forEach(x -> builder.append(x.concat(", ")));
        System.out.println(builder.substring(0, builder.lastIndexOf(",")));
        return builder.substring(0, builder.lastIndexOf(","));
    }
}
