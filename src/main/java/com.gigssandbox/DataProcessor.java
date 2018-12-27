package com.gigssandbox;

import com.gigssandbox.entities.Band;
import com.gigssandbox.entities.Gig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

class DataProcessor {

    Gig convertStringArrayToGig(String[] gigFields) {
        return Gig.builder()
                .headliner(gigFields[0])
                .support(gigFields[1])
                .location(gigFields[2])
                .date(LocalDateTime.parse(gigFields[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }

    Band convertArrayToBand(String[] bandFields) {
        String[] membersArray = Arrays.stream(bandFields[1].split(",")).map(String::trim).toArray(String[]::new);
        return Band.builder()
                .name(bandFields[0])
                .members(new ArrayList<>(Arrays.asList(membersArray)))
                .creationYear(Short.parseShort(bandFields[2]))
                .city(bandFields[3])
                .genre(bandFields[4])
                .build();

    }
}
