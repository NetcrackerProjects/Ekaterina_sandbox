package com.gigssandbox.IO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ValuesStore {
    private final Properties properties;

    public ValuesStore() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/strings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String stored(String propertyName) {
        return properties.getProperty(propertyName);
    }
}