package com.gigssandbox.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@EqualsAndHashCode
@Builder(toBuilder = true)
@Getter
@Setter
public class Community {
    private int id;
    private String name;
    private Collection<Integer> membersIds;
    private LocalDateTime creationDate;
    private Set<Integer> vizitedGigsIds;

}
