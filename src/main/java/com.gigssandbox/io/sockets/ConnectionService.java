package com.gigssandbox.io.sockets;

import com.gigssandbox.exceptions.ConnectionServiceStoppedException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionService {
    private ConnectionReceiver receiver;
    private BlockingQueue<Socket> clientSockets;
    private ConnectionThread connectionThread;
    private volatile boolean stopped;

    public ConnectionService() {
        this.receiver = new ConnectionReceiver();
        this.clientSockets = new LinkedBlockingQueue<>();
        this.connectionThread = new ConnectionThread();
        this.stopped = true;
    }

    public void start() {
        connectionThread.start();
        this.stopped = false;
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

    private class ConnectionThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                clientSockets.add(receiver.nextClientSocket());
            }
        }
    }
}
