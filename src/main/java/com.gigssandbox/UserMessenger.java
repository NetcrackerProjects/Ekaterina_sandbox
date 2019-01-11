package com.gigssandbox;


class UserMessenger {
    private ConsoleIOHandler consoleIOHandler;

    UserMessenger() {
        this.consoleIOHandler = new ConsoleIOHandler();
    }

    void sendMessage(String messageName) {
        consoleIOHandler.writeString(messageName);
    }

    Command getNextCommand() {
        sendMessage("choose_action");
        int actionCode = consoleIOHandler.readInt();
        Command currentCommand;
        try {
            currentCommand = Command.values()[actionCode - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            currentCommand = Command.UNSUPPORTED;
        }
        return currentCommand;
    }
}