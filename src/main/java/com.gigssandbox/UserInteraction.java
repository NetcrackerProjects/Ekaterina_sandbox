package com.gigssandbox;

import com.gigssandbox.entities.Band;
import com.gigssandbox.entities.Gig;
import com.gigssandbox.exceptions.AddingToDatabaseException;
import com.gigssandbox.exceptions.CheckingIfLocationIsBusyException;

import java.util.Collection;

public class UserInteraction {

    private GigsRepository repository;
    private ConsoleHelper helper;
    private DataProcessor processor;

    public UserInteraction() {
        repository = new GigsRepository();
        helper = new ConsoleHelper();
        processor = new DataProcessor();
    }

    void interact() {
        int actionCode;

        helper.writeStringFromPropertiesToConsole("hello");
        helper.writeStringFromPropertiesToConsole("choose_action");

        while ((actionCode = helper.readInt()) > 0) {
            switch (actionCode) {
                case 1:
                    printGigsCollection(1);
                    break;
                case 2:
                    printGigsCollection(2);
                    break;
                case 3:
                    insertGig();
                    break;
                case 4:
                    insertBand();
                    break;
                case 5:
                    helper.writeStringFromPropertiesToConsole("say_goodbye");
                    System.exit(0);
                default:
                    helper.writeStringFromPropertiesToConsole("unsupported");
                    break;
            }
            helper.writeStringFromPropertiesToConsole("choose_action");
        }
    }

    void insertGig() {
        helper.writeStringFromPropertiesToConsole("add_gig_info");
        Gig gig = processor.convertStringArrayToGig(helper.getEntityFieldsArray());
        try {
            if (repository.checkIfPlaceIsAlreadyBusy(gig)) {
                repository.addNewGig(gig);
                helper.writeStringFromPropertiesToConsole("done");
            } else {
                helper.writeStringFromPropertiesToConsole("location_is_busy");
            }
        } catch (CheckingIfLocationIsBusyException e) {
            helper.writeStringFromPropertiesToConsole("checking_location_exc");
        } catch(AddingToDatabaseException e){
            helper.writeStringFromPropertiesToConsole("adding_to_db_exc");
        }
    }

    private void printGigsCollection(int actionCode) {
        Collection<Gig> gigs;
        if (actionCode == 1) {
            gigs = repository.getGigsCollection();
        } else {
            helper.writeStringFromPropertiesToConsole("write_band_name");
            String bandName = "";
            while ((bandName.isEmpty())) {
                bandName = helper.readString();
            }
            gigs = repository.getGigsCollection(bandName);
        }
        helper.writeCollectionToConsole(gigs);
    }

    private void insertBand() {
        helper.writeStringFromPropertiesToConsole("add_band_info");
        Band band = processor.convertArrayToBand(helper.getEntityFieldsArray());
        try {
            repository.addNewBand(band);
            helper.writeStringFromPropertiesToConsole("done");
        } catch (AddingToDatabaseException e) {
            helper.writeStringFromPropertiesToConsole("adding_to_db_exc");
        }
    }

}
