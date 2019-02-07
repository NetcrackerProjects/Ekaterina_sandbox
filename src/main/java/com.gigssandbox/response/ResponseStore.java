package com.gigssandbox.response;

import com.gigssandbox.exceptions.LoadingResponsePropertiesException;
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
            throw new LoadingResponsePropertiesException();
        }
    }

    public String loadText(String propertyName) {
        return properties.getProperty(propertyName);
    }
}