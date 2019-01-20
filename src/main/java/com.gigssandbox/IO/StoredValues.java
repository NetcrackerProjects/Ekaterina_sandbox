package com.gigssandbox.IO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class StoredValues {
    private static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/strings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static String get(String propertyName) {
        return properties.getProperty(propertyName);
    }
}

