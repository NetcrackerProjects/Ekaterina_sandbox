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
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;

public class SocketConnectionTest {
    private  SocketConnection socketConnection;

    @Before
    public void setUp() throws IOException {
        String textForInputStream = "some text";
        Socket clientSocket = Mockito.mock(Socket.class);

        InputStream in = new ByteArrayInputStream(textForInputStream.getBytes());
        Mockito.when(clientSocket.getInputStream()).thenReturn(in);

        OutputStream out = new ByteArrayOutputStream();
        Mockito.when(clientSocket.getOutputStream()).thenReturn(out);

        this.socketConnection = new SocketConnection(clientSocket);
    }

    @Test
    public void shouldReturnSocketInputWhenInputWasAsked() {
        Input fetchedInput = socketConnection.getInput();

        assertNotNull(fetchedInput);
    }

    @Test
    public void shouldReturnSocketOutputWhenOutputWasAsked() {
        Output fetchedOutput = socketConnection.getOutput();

        assertNotNull(fetchedOutput);
    }
}
