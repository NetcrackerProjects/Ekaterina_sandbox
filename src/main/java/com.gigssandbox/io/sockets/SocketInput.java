package com.gigssandbox.io.sockets;

import com.gigssandbox.io.sockets.exception.SocketInputException;
import com.gigssandbox.io.Input;
import java.io.BufferedReader;
import java.io.IOException;

class SocketInput implements Input {
    private BufferedReader in;

    SocketInput(BufferedReader in) {
        this.in = in;
    }

    @Override
    public String receive() {
        try {
            return in.readLine();

        } catch (IOException e) {
            throw new SocketInputException();
        }
    }
}
