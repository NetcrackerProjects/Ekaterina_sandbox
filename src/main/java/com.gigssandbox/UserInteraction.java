package com.gigssandbox;


import com.gigssandbox.entities.Band;
import com.gigssandbox.entities.Gig;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

public class UserInteraction {

    private GigsRepository repository = new GigsRepository();
    private ConsoleHelper helper = new ConsoleHelper();
    private Properties properties = new Properties();

    {
        try {
            properties.load(new FileInputStream("src/main/resources/strings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processAction(int actionCode) {
        switch (actionCode) {
            case 1: printGigsList(1); break;
            case 2: printGigsList(2); break;
            case 3: prepareGigForInsertion(); break;
            case 4: prepareBandForInsertion(); break;
            case 5: helper.writeStringToConsole(properties.getProperty("say_goodbye"));
                    System.exit(0);
                    break;
            default:
                helper.writeStringToConsole(properties.getProperty("unsupported"));
        }
    }

    private void printGigsList(int actionCode) {
        List<Gig> gigs;
        if (actionCode == 1) {
            gigs = repository.getGigsList();
        }
        else {
            helper.writeStringToConsole(properties.getProperty("write_band_name"));
            String bandName = "";
            while ((bandName.isEmpty())) {
                bandName = helper.readString();
            }
            gigs = repository.getGigsList(bandName);
        }
        helper.writeListToConsole(gigs);
    }

    private void prepareGigForInsertion() {
        String[] gigFields = getEntityFieldsArray("add_gig_info");
        Gig gig = Gig.builder()
                .headliner(gigFields[0])
                .support(gigFields[1])
                .location(gigFields[2])
                .date(LocalDateTime.parse(gigFields[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
        try {
            repository.addNewGig(gig);
            helper.writeStringToConsole(properties.getProperty("done"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void prepareBandForInsertion() {
        String[] bandFields = getEntityFieldsArray("add_band_info");
        String[] membersArray = Arrays.stream(bandFields[1].split(",")).map(String::trim).toArray(String[]::new);
        Band band = Band.builder()
                .name(bandFields[0])
                .members(new ArrayList<>(Arrays.asList(membersArray)))
                .createYear(Short.parseShort(bandFields[2]))
                .city(bandFields[3])
                .genre(bandFields[4])
                .build();
        try {
            repository.addNewBand(band);
            helper.writeStringToConsole(properties.getProperty("done"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String[] getEntityFieldsArray(String actionPropertyName) {
        String input = "";
        helper.writeStringToConsole(properties.getProperty(actionPropertyName));
        while (input.isEmpty()) {
            input = helper.readString();
        }
        input = input.trim();
        return input.split(";");
    }
}
