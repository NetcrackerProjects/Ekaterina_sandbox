package com.gigssandbox.IO;

import com.gigssandbox.Response;
import java.util.Collection;

public interface Output {
    void write(String text);

    void write(Collection<?> collection);

    void write(Response response);

    void writeStored(String propertyName);

    default String stored(String propertyName) {
        return new ResponseStore().stored(propertyName);
    }
}