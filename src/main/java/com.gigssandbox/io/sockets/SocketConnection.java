package com.gigssandbox.io.sockets;

import com.gigssandbox.io.sockets.exception.SocketOutputException;
import com.gigssandbox.io.Connection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketConnection implements Connection {
    private Socket clientSocket;

    SocketConnection(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public SocketInput getInput() {
        try {
            return new SocketInput(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));

        } catch (IOException e) {
            throw new SocketOutputException();
        }
    }

    @Override
    public SocketOutput getOutput() {
        try {
            return new SocketOutput(new PrintWriter(clientSocket.getOutputStream()));

        } catch (IOException e) {
            throw new SocketOutputException();
        }
    }
}
