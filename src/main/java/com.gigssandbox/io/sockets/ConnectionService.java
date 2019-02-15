package com.gigssandbox.io.sockets;

import com.gigssandbox.exceptions.ConnectionServiceStoppedException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionService extends Thread {
    private ConnectionReceiver receiver;
    private BlockingQueue<Socket> clientSockets;

    public ConnectionService() {
        this.receiver = new ConnectionReceiver();
        this.clientSockets = new LinkedBlockingQueue<>();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            clientSockets.add(receiver.nextClientSocket());
        }
    }

    public SocketConnection nextClient() throws ConnectionServiceStoppedException {
        try {
            return new SocketConnection(clientSockets.take());
        } catch (InterruptedException e) {
            throw new ConnectionServiceStoppedException();
        }
    }
}
