package com.gigssandbox.io.sockets;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ConnectionServiceTest {
    private ConnectionService connectionService;

    @Before
    public void setUp() {
        this.connectionService = new ConnectionService();
        connectionService.start();
    }

    @Test
    public void shouldFetchWhenNewClientHasConnected() throws Exception {
        new Socket().connect(new InetSocketAddress("localhost", 1234));

        assertNotNull(connectionService.nextClient());
    }


    @After
    public void tearDown() throws IOException {
        connectionService.close();
    }
}
