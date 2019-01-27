package com.gigssandbox.IO;

import java.util.Collection;

public interface Output {
    void write(String text);

    void writeStored(String propertyName);

    void write(Collection<?> collection);

    default String stored(String propertyName) {
        return new ValuesStore().stored(propertyName);
    }
}