package com.gigssandbox.io.sockets;

import com.gigssandbox.Result;
import com.gigssandbox.response.ResponseFactory;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class SocketOutputTest {
    private SocketOutput socketOutput;
    private OutputStream out;

    @Before
    public void setUp() throws Exception {
        Socket clientSocket = Mockito.mock(Socket.class);

        this.out = new ByteArrayOutputStream();
        Mockito.when(clientSocket.getOutputStream()).thenReturn(out);

        SocketConnection socketConnection = new SocketConnection(clientSocket);
        this.socketOutput = socketConnection.getOutput();
    }

    @Test
    public void shouldSendTextToClientWhenAppTriesToSendAnswer() {
        String expectedOutput = "You have successfully joined the gig";

        socketOutput.send(new ResponseFactory().create(Result.JOIN_GIG_SUCCESS));

        assertEquals(expectedOutput, out.toString());
    }
}
