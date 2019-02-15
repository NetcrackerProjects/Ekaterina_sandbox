package com.gigssandbox.io.console;

import com.gigssandbox.io.Output;
import com.gigssandbox.response.Response;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ConsoleOutput implements Output {
    public void send(Response response) {
        System.out.println(response.getProperty());
    }
}
