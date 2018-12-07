package com.gigssandbox;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class MainClass {

    private static Properties properties = new Properties();
    private static ConsoleHelper helper = new ConsoleHelper();
    private static UserInteraction interaction = new UserInteraction();

    public static void main(String[] args) {
        {
            try {
                properties.load(new FileInputStream("src/main/resources/strings.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        helper.writeStringToConsole(properties.getProperty("hello"));
        helper.writeStringToConsole(properties.getProperty("choose_action"));
        while (true) {
             interaction.processAction(helper.readInt());
             helper.writeStringToConsole(properties.getProperty("choose_action"));
        }
    }
}
