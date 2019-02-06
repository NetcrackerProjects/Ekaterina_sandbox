package com.gigssandbox.IO.console;

import com.gigssandbox.IO.Output;

import com.gigssandbox.response.Response;

public class ConsoleOutput implements Output {
    public void send(Response response) {
        System.out.println(response.getProperty());
    }
}
