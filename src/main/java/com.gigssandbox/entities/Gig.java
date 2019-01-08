package com.gigssandbox.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@EqualsAndHashCode
@Builder(toBuilder = true)
@Getter
public class Gig {
    private int id;
    private String headliner;
    private String support;
    private LocalDateTime date;
    private String location;
    private int views;

    @Override
    public String toString() {
        return " Headliner: " + headliner +
                " \n\tsupport: " + support +
                " \n\tdate: " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " \n\tlocation: " + location;
    }

}