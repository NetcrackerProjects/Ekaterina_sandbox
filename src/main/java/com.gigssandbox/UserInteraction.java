package com.gigssandbox;

import com.gigssandbox.entities.Band;
import com.gigssandbox.entities.Gig;
import com.gigssandbox.exceptions.AddingToDatabaseException;
import com.gigssandbox.exceptions.CheckingIfLocationIsBusyException;

import java.util.Collection;

class UserInteraction {

    private GigsRepository gigsRepository;
    private UserMessenger userMessenger;
    private DataProcessor dataProcessor;

    UserInteraction() {
        this.gigsRepository = new GigsRepository();
        this.userMessenger = new UserMessenger();
        this.dataProcessor = new DataProcessor();
    }

    void startInteraction() {
        userMessenger.sendMessage("hello");

        Command currentCommand = userMessenger.nextCommand();
        while (!currentCommand.equals(Command.EXIT)) {
            switch (currentCommand) {
                case PRINT_GIGS:
                    printGigsCollection(1);
                    break;
                case PRINT_GIGS_BY_BAND:
                    printGigsCollection(2);
                    break;
                case ADD_GIG:
                    insertGig();
                    break;
                case ADD_BAND:
                    insertBand();
                    break;
                case UNSUPPORTED:
                    userMessenger.sendMessage("unsuported");
            }
            userMessenger.sendMessage("choose_action");
            currentCommand = userMessenger.nextCommand();
        }
        userMessenger.sayGoodbye();
    }

    private void insertGig() {
        userMessenger.sendMessage("add_gig_info");
        Gig gig = dataProcessor.convertStringArrayToGig(userMessenger.getEntityFieldsArray());
        try {
            if (gigsRepository.checkIfPlaceIsAlreadyBusy(gig)) {
                gigsRepository.addNewGig(gig);
                userMessenger.sendMessage("done");
            } else {
                userMessenger.sendMessage("location_is_busy");
            }
        } catch (CheckingIfLocationIsBusyException e) {
            userMessenger.sendMessage("checking_location_exc");
        } catch(AddingToDatabaseException e){
            userMessenger.sendMessage("adding_to_db_exc");
        }
    }

    private void printGigsCollection(int actionCode) {
        Collection<Gig> gigs;
        if (actionCode == 1) {
            gigs = gigsRepository.getGigsCollection();
        } else {
            userMessenger.sendMessage("write_band_name");
            String bandName = "";
            while ((bandName.isEmpty())) {
                bandName = userMessenger.readString();
            }
            gigs = gigsRepository.getGigsCollection(bandName);
        }
        userMessenger.writeCollectionToConsole(gigs);
    }

    private void insertBand() {
        userMessenger.sendMessage("add_band_info");
        Band band = dataProcessor.convertArrayToBand(userMessenger.getEntityFieldsArray());
        try {
            gigsRepository.addNewBand(band);
            userMessenger.sendMessage("done");
        } catch (AddingToDatabaseException e) {
            userMessenger.sendMessage("adding_to_db_exc");
        }
    }

}
