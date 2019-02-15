package com.gigssandbox.io.sockets;

import com.gigssandbox.exceptions.ConnectionEstablishingException;
import com.gigssandbox.exceptions.ConnectionReceivingException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ConnectionReceiver extends Thread {
    private static final int PORT = 1234;
    private final ServerSocket serverSocket;

    ConnectionReceiver() {
        try {
            this.serverSocket = new ServerSocket(PORT);

        } catch (IOException e) {
            throw new ConnectionEstablishingException();
        }
    }

    Socket nextClientSocket() {
        try {
            return serverSocket.accept();

        } catch (IOException e) {
            throw new ConnectionReceivingException();
        }
    }
}