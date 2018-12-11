package com.gigssandbox;

import com.gigssandbox.entities.Band;
import com.gigssandbox.entities.Gig;
import com.gigssandbox.exceptions.AddingToDatabaseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

class UserInteraction {

    private GigsRepository repository = new GigsRepository();
    private ConsoleHelper helper;
    private Properties properties;

    UserInteraction(Properties properties, ConsoleHelper helper) {
        this.properties = properties;
        this.helper = helper;
    }

    void processAction(int actionCode) {
        switch (actionCode) {
            case 1:
                printGigsCollection(1);
                break;
            case 2:
                printGigsCollection(2);
                break;
            case 3:
                prepareGigForInsertion();
                break;
            case 4:
                prepareBandForInsertion();
                break;
            case 5:
                helper.writeStringToConsole(properties.getProperty("say_goodbye"));
                System.exit(0);
            default:
                helper.writeStringToConsole(properties.getProperty("unsupported"));
                break;
        }
    }

    private void printGigsCollection(int actionCode) {
        Collection<Gig> gigs;
        if (actionCode == 1) {
            gigs = repository.getGigsCollection();
        } else {
            helper.writeStringToConsole(properties.getProperty("write_band_name"));
            String bandName = "";
            while ((bandName.isEmpty())) {
                bandName = helper.readString();
            }
            gigs = repository.getGigsCollection(bandName);
        }
        helper.writeCollectionToConsole(gigs);
    }

    private void prepareGigForInsertion() {
        String[] gigFields = helper.getEntityFieldsArray("add_gig_info");
        Gig gig = Gig.builder()
                .headliner(gigFields[0])
                .support(gigFields[1])
                .location(gigFields[2])
                .date(LocalDateTime.parse(gigFields[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
        try {
            repository.addNewGig(gig);
            helper.writeStringToConsole(properties.getProperty("done"));
        } catch (AddingToDatabaseException e) {
            helper.writeStringToConsole(properties.getProperty("adding_to_db_exc"));
        }
    }

    private void prepareBandForInsertion() {
        String[] bandFields = helper.getEntityFieldsArray("add_band_info");
        String[] membersArray = Arrays.stream(bandFields[1].split(",")).map(String::trim).toArray(String[]::new);
        Band band = Band.builder()
                .name(bandFields[0])
                .members(new ArrayList<>(Arrays.asList(membersArray)))
                .creationYear(Short.parseShort(bandFields[2]))
                .city(bandFields[3])
                .genre(bandFields[4])
                .build();
        try {
            repository.addNewBand(band);
            helper.writeStringToConsole(properties.getProperty("done"));
        } catch (AddingToDatabaseException e) {
            helper.writeStringToConsole(properties.getProperty("adding_to_db_exc"));
        }
    }

}
