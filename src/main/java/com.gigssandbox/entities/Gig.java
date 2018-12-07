package com.gigssandbox.entities;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@EqualsAndHashCode
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Gig {
    private int id;
    private String headliner;
    private String support;
    private LocalDateTime date;
    private String location;

    @Override
    public String toString() {
        return " headliner: " + headliner +
                " \n\tsupport: " + support +
                " \n\tdate: " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " \n\tlocation: " + location;
    }

}
