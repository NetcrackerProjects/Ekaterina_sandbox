package com.gigssandbox.IO;

import java.util.Collection;

public interface OutputHandler {
    void write(String text);

    void writePreloaded(String propretyName);

    void write(Collection<?> collection);

    default String getStored(String propertyName) {
        return StoredValues.get(propertyName);
    }
}