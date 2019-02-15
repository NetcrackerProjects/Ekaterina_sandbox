package com.gigssandbox.io.sockets;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.Socket;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class SocketInputTest {
    private SocketInput socketInput;

    @Before
    public void setUp() throws Exception {
        String textForInputStream = "I am a banana";
        Socket clientSocket = Mockito.mock(Socket.class);

        InputStream in = new ByteArrayInputStream(textForInputStream.getBytes());
        Mockito.when(clientSocket.getInputStream()).thenReturn(in);

        SocketConnection socketConnection = new SocketConnection(clientSocket);
        this.socketInput = socketConnection.getInput();
    }

    @Test
    public void shouldReceiveStringMessageWhenClientHasSentText() {
        String expectedText = "I am a banana";

        String actualText = socketInput.receive();

        assertEquals(expectedText, actualText);
    }
}
