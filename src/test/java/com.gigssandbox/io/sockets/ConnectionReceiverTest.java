package com.gigssandbox.io.sockets;

import java.io.IOException;
import java.net.Socket;
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
        Socket socketOfRemoteClient = new Socket("127.0.0.1", 1234);

        Socket fetchedClientSocket = receiver.nextClientSocket();

        assertNotNull(fetchedClientSocket);
    }
}
