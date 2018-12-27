package com.gigssandbox;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.Scanner;

public class ConsoleHelper {

    private static final Scanner in = new Scanner(System.in);
    private Properties properties = new Properties();

    public ConsoleHelper() {
        try {
            properties.load(new FileInputStream("src/main/resources/strings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void writeStringFromPropertiesToConsole(String propertyName) {
        System.out.println(properties.getProperty(propertyName));
    }

    public void writeCollectionToConsole(Collection<?> coll) {
        if (coll.isEmpty()) {
            System.out.println(getStringFromProperties("empty_result"));
        } else {
            coll.forEach(System.out::println);
        }
    }

    public String[] getEntityFieldsArray() {
        String input = "";
        while (input.isEmpty()) {
            input = readString();
        }
        input = input.trim();
        return input.split(";");
    }

    String getStringFromProperties(String propertyName) {
        return properties.getProperty(propertyName);
    }
}