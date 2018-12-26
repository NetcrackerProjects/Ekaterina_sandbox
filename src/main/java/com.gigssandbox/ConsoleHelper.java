package com.gigssandbox;

import java.util.Collection;
import java.util.Properties;
import java.util.Scanner;

public class ConsoleHelper {


    private static final Scanner in = new Scanner(System.in);
    private Properties properties;


    public ConsoleHelper(Properties properties) {
        this.properties = properties;
    }

    public String readString() {
        return in.nextLine();
    }

    public int readInt() {
        return in.nextInt();
    }

    public void writeStringToConsole(String s) {
        System.out.println(s);
    }

    public void writeCollectionToConsole(Collection<?> coll) {
        if (coll.isEmpty()) {
            System.out.println(properties.getProperty("empty_result"));
        } else {}}


    public String[] getEntityFieldsArray(String actionPropertyName) {
        String input = "";
        System.out.println(properties.getProperty(actionPropertyName));
        while (input.isEmpty()) {
            input = readString();
        }
        input = input.trim();
        return input.split(";");
    }
}
