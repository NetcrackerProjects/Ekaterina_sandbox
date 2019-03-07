package com.gigssandbox.io.sockets;

import com.gigssandbox.exceptions.ConnectionServiceStoppedException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionService {
    private ConnectionReceiver receiver;
    private BlockingQueue<Socket> clientSockets;
    private volatile boolean stopped;

    public ConnectionService() {
        this.receiver = new ConnectionReceiver();
        this.clientSockets = new LinkedBlockingQueue<>();
        this.stopped = true;
    }

    public void start() {
        this.stopped = false;

        new Thread(() -> {
            while (!stopped) {
                clientSockets.add(receiver.nextClientSocket());
            }
        }).start();
    }

    public boolean isStopped() {
        return stopped;
    }

    public SocketConnection nextClient() throws ConnectionServiceStoppedException {
        try {
            return new SocketConnection(clientSockets.take());

        } catch (InterruptedException e) {
            this.stopped = true;
            throw new ConnectionServiceStoppedException();
        }
    }

    public void stop() {
        this.stopped = true;
    }

    public void close() throws IOException {
        receiver.close();
        this.stopped = true;
    }
}
