package com.gigssandbox.IO;

import com.gigssandbox.Response;
import java.util.Collection;

public interface Output {
    void write(String text);

    void write(Collection<?> collection);

    default void write(Response response) {
        write(stored(from(response)));
    }

    default void writeStored(String propertyName) {
        write(stored(propertyName));
    }

    default String from(Response response) {
        return response.name().toLowerCase();
    }

    default String stored(String propertyName) {
        return new ValuesStore().stored(propertyName);
    }
}