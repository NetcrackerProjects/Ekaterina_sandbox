package com.gigssandbox;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.Scanner;

class InputOutputHandler {
    private final Scanner in;
    private final Properties properties;

    InputOutputHandler() {
        this.in = new Scanner(System.in);
        this.properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/strings.properties"));
        } catch (IOException e) {
            writePreloadedString("properties_error");
            System.exit(1);
        }
    }

    int readInt() {
        int input = 0;
        while (input <= 0) {
            input = in.nextInt();
        }
        return input;
    }

    String readString() {
        String text;
        while ((text = in.nextLine()).equals("")) ;
        return text;
    }

    void writePreloadedString(String propertyName) {
        System.out.println(getPreloadedString(propertyName));
    }

    void writeString(String string) {
        System.out.println(string);
    }

    void writeCollection(Collection<?> collection) {
        if (collection.isEmpty()) {
            writePreloadedString("empty_result");
        } else {
            collection.forEach(System.out::println);
        }
    }

    String createSolidString(String... strings) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String string : strings) {
            stringBuffer.append(string).append(" ");
        }
        return String.valueOf(stringBuffer);
    }

    String getPreloadedString(String propertyName) {
        return properties.getProperty(propertyName);
    }

    String[] getEntityFieldsArray() {
        return readString().trim().split(";");
    }
}