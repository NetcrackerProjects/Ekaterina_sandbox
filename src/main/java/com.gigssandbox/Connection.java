package com.gigssandbox;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class Connection implements Closeable {
    private Socket socket;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    void sendMessage(Message message) throws IOException {
        synchronized (objectOutputStream) {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        }
    }

    Message receiveMessage() throws IOException, ClassNotFoundException {
        synchronized (objectInputStream) {
            return (Message) objectInputStream.readObject();
        }
    }

    boolean isBound() {
        return socket.isBound();
    }

    @Override
    public void close() throws IOException {
        objectInputStream.close();
        objectOutputStream.close();
    }
}
