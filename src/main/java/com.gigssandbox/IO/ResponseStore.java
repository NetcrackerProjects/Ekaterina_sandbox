package com.gigssandbox.IO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ResponseStore {
    private final Properties properties;

    public ResponseStore() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/response.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String stored(String propertyName) {
        return properties.getProperty(propertyName);
    }
}