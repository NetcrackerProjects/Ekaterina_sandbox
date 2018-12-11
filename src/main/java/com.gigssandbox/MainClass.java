package com.gigssandbox;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class MainClass {

    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/strings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ConsoleHelper helper = new ConsoleHelper(properties);
        UserInteraction interaction = new UserInteraction(properties, helper);

        helper.writeStringToConsole(properties.getProperty("hello"));
        helper.writeStringToConsole(properties.getProperty("choose_action"));
        int input;
        while ((input = helper.readInt()) > 0) {
            interaction.processAction(input);
            helper.writeStringToConsole(properties.getProperty("choose_action"));
        }
    }
}
