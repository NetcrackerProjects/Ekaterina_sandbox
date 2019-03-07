package com.gigssandbox.io.sockets;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ConnectionReceiverTest {
    private ConnectionReceiver receiver;

    @Before
    public void setUp() {
        this.receiver = new ConnectionReceiver();
    }

    @Test
    public void shouldReturnClientSocketWhenClientHasConnected() throws IOException {
        new Socket().connect(new InetSocketAddress("localhost", 1234));

        Socket fetchedClientSocket = receiver.nextClientSocket();

        assertNotNull(fetchedClientSocket);
    }

    @After
    public void tearDown() throws IOException {
        receiver.close();
    }
}
