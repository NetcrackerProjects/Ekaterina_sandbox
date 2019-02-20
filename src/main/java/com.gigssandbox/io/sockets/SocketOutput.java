package com.gigssandbox.io.sockets;

import com.gigssandbox.io.Output;
import com.gigssandbox.response.Response;
import java.io.PrintWriter;

public class SocketOutput implements Output {
    private PrintWriter out;

    SocketOutput(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void send(Response response) {
        out.write(response.getText());
        out.flush();
    }
}
