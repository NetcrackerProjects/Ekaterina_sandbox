package com.gigssandbox;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.Scanner;


class ConsoleIOHandler {
    private final Scanner in;
    private Properties properties;

    ConsoleIOHandler() {
        this.in = new Scanner(System.in);
        this.properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/strings.properties"));
        } catch (IOException e) {
            writeString("properties_error");
            System.exit(1);
        }
    }

    int readInt() {
        return in.nextInt();
    }

    String readString() {
        String input = "";
        while (input.isEmpty()) {
            input = in.nextLine();
        }
        return input;
    }

    void writeString(String propertyName) {
        System.out.println(properties.getProperty(propertyName));
    }

    void writeCollection(Collection<?> coll) {
        if (coll.isEmpty()) {
            writeString("empty_result");
        } else {
            coll.forEach(System.out::println);
        }
    }

    String[] getEntityFieldsArray() {
        return readString().trim().split(";");
    }
}