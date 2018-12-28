package com.gigssandbox;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.Scanner;

class UserMessenger {

    private static final Scanner in = new Scanner(System.in);
    private Properties properties = new Properties();

    UserMessenger() {
        try {
            properties.load(new FileInputStream("src/main/resources/strings.properties"));
        } catch (IOException e) {
            sendMessage("properties_error");
            System.exit(1);
        }
    }

    String readString() {
        String input = "";
        while (input.isEmpty()) {
            input = in.nextLine();
        }
        return input;
    }

    int readInt() {
        return in.nextInt();
    }

    void writeStringToConsole(String s) {
        System.out.println(s);
    }

    void sendMessage(String propertyName) {
        System.out.println(getStringFromProperties(propertyName));
    }

    void sayGoodbye() {
        sendMessage("say_goodbye");
        System.exit(0);
    }

    Command nextCommand() {
        int actionCode = readInt();
        Command currentCommand;
        try {
            currentCommand = Command.values()[actionCode - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
           currentCommand = Command.UNSUPPORTED;
        }
        return currentCommand;
    }

    void writeCollectionToConsole(Collection<?> coll) {
        if (coll.isEmpty()) {
            System.out.println(getStringFromProperties("empty_result"));
        } else {
            coll.forEach(System.out::println);
        }
    }

    String[] getEntityFieldsArray() {
        String input = "";
        while (input.isEmpty()) {
            input = readString();
        }
        input = input.trim();
        return input.split(";");
    }

    private String getStringFromProperties(String propertyName) {
        return properties.getProperty(propertyName);
    }
}