package com.gigssandbox;

class UserInteraction {
    private InputOutputHandler inputOutputHandler;

    UserInteraction() {
        this.inputOutputHandler = new InputOutputHandler();
    }

    String readString() {
        return inputOutputHandler.readString();
    }

    int readInt() {
        return inputOutputHandler.readInt();
    }

    void write(String text) {
        inputOutputHandler.writeString(text);
    }

    void writePreloaded(String propertyName) {
        inputOutputHandler.writePreloadedString(propertyName);
    }

    String unite(String... strings) {
        return inputOutputHandler.createSolidString(strings);
    }

    String loadString(String propertyName) {
        return inputOutputHandler.getPreloadedString(propertyName);
    }
}
