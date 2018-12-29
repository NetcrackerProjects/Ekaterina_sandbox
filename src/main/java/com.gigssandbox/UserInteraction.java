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
    private ConsoleIOHandler consoleIOHandler;

    UserInteraction() {
        this.gigsRepository = new GigsRepository();
        this.userMessenger = new UserMessenger();
        this.dataProcessor = new DataProcessor();
        this.consoleIOHandler = new ConsoleIOHandler();
    }

    void startInteraction() {
        Command currentCommand;

        userMessenger.sendMessage("hello");
        while ((currentCommand = userMessenger.getNextCommand()) != Command.EXIT) {
            switch (currentCommand) {
                case PRINT_GIGS:
                    printGigsCollection(GigsListOption.ALL_GROUPS);
                    break;
                case PRINT_GIGS_BY_BAND:
                    printGigsCollection(GigsListOption.CERTAIN_GROUP);
                    break;
                case ADD_GIG:
                    insertGig();
                    break;
                case ADD_BAND:
                    insertBand();
                    break;
                case UNSUPPORTED:
                    userMessenger.sendMessage("unsupported");
                    break;
            }
        }
        exitProgram();
    }

    private void exitProgram() {
        userMessenger.sendMessage("say_goodbye");
        System.exit(0);
    }

    private void insertGig() {
        userMessenger.sendMessage("add_gig_info");
        Gig gig = dataProcessor.convertStringArrayToGig(consoleIOHandler.getEntityFieldsArray());
        try {
            if (gigsRepository.checkIfPlaceIsAlreadyBusy(gig)) {
                gigsRepository.addNewGig(gig);
                userMessenger.sendMessage("done");
            } else {
                userMessenger.sendMessage("location_is_busy");
            }
        } catch (CheckingIfLocationIsBusyException e) {
            userMessenger.sendMessage("checking_location_exc");
        } catch (AddingToDatabaseException e) {
            userMessenger.sendMessage("adding_to_db_exc");
        }
    }

    private void printGigsCollection(GigsListOption gigsListOption) {
        Collection<Gig> gigs;

        if (gigsListOption == GigsListOption.ALL_GROUPS) {
            gigs = gigsRepository.getGigsCollection();
        } else {
            userMessenger.sendMessage("write_band_name");
            gigs = gigsRepository.getGigsCollection(consoleIOHandler.readString());
        }
        consoleIOHandler.writeCollection(gigs);
    }

    private void insertBand() {
        userMessenger.sendMessage("add_band_info");
        Band band = dataProcessor.convertArrayToBand(consoleIOHandler.getEntityFieldsArray());
        try {
            gigsRepository.addNewBand(band);
            userMessenger.sendMessage("done");
        } catch (AddingToDatabaseException e) {
            userMessenger.sendMessage("adding_to_db_exc");
        }
    }

    private enum GigsListOption {
        ALL_GROUPS,
        CERTAIN_GROUP
    }
}