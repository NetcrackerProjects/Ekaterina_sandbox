package com.gigssandbox.io.sockets;

import com.gigssandbox.io.Input;
import com.gigssandbox.io.Output;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SocketConnectionTest {
    private SocketConnection socketConnection;
    private Socket clientSocket;

    @Before
    public void setUp() {
        this.clientSocket = mock(Socket.class);
        this.socketConnection = new SocketConnection(clientSocket);
    }

    @Test
    public void shouldReturnSocketInputWhenInputWasAsked() throws Exception {
        String textForInputStream = "some text";
        InputStream in = new ByteArrayInputStream(textForInputStream.getBytes());
        when(clientSocket.getInputStream()).thenReturn(in);

        Input fetchedInput = socketConnection.getInput();

        assertNotNull(fetchedInput);
    }

    @Test
    public void shouldReturnSocketOutputWhenOutputWasAsked() throws IOException {
        OutputStream out = new ByteArrayOutputStream();
        when(clientSocket.getOutputStream()).thenReturn(out);

        Output fetchedOutput = socketConnection.getOutput();

        assertNotNull(fetchedOutput);
    }
}
