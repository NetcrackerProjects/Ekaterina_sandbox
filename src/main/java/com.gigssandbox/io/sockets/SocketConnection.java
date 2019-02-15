package com.gigssandbox.io.sockets;

import com.gigssandbox.exceptions.ConnectionEstablishingException;
import com.gigssandbox.io.Connection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
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
            throw new ConnectionEstablishingException();
        }
    }

    @Override
    public SocketOutput getOutput() {
        try {
            return new SocketOutput(new PrintWriter(clientSocket.getOutputStream()));

        } catch (IOException e) {
            throw new ConnectionEstablishingException();
        }
    }
}
