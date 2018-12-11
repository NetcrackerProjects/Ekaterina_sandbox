package com.gigssandbox;

import java.util.Collection;
import java.util.Properties;
import java.util.Scanner;

class ConsoleHelper {

    private static final Scanner in = new Scanner(System.in);
    private Properties properties;

    ConsoleHelper(Properties properties) {
        this.properties = properties;
    }

    String readString() {
        return in.nextLine();
    }

    int readInt() {
        return in.nextInt();
    }

    void writeStringToConsole(String s) {
        System.out.println(s);
    }

    void writeCollectionToConsole(Collection<?> coll) {
        if (coll.isEmpty())
            System.out.println(properties.getProperty("empty_result"));
        else {
            coll.forEach(System.out::println);
        }
    }

    String[] getEntityFieldsArray(String actionPropertyName) {
        String input = "";
        System.out.println(properties.getProperty(actionPropertyName));
        while (input.isEmpty()) {
            input = readString();
        }
        input = input.trim();
        return input.split(";");
    }
}
