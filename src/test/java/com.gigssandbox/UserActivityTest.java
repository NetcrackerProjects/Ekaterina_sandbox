package com.gigssandbox;

import com.gigssandbox.io.sockets.ConnectionService;
import com.gigssandbox.io.sockets.SocketConnection;
import com.gigssandbox.services.CommunityService;
import com.gigssandbox.services.GigService;
import com.gigssandbox.services.UserService;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserActivityTest {
    private UserActivity userActivity;
    private Socket clientSocket;
    private ConnectionService connectionService;
    private DataOutputStream out;
    private BufferedReader in;

    @Before
    public void setUp() throws Exception {
        UserService userService = new UserService(new HashMap<>());
        GigService gigService = new GigService(new HashMap<>());
        CommunityService communityService = new CommunityService(new HashMap<>());

        this.connectionService = new ConnectionService();
        connectionService.start();

        this.clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress("localhost", 1234));
        this.out = new DataOutputStream(clientSocket.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        SocketConnection socketConnection = connectionService.nextClient();

        this.userActivity = new UserActivity(userService, communityService, gigService, socketConnection);
    }

    @Test
    public void shouldWriteResponseToClientWhenTheOneEnteredRegisterCommand() throws IOException {
        String expectedResponse = "You have successfully registered";

        out.write("register raccoon 1011\n".getBytes(StandardCharsets.UTF_8));
        userActivity.start();
        String actualResponse = in.readLine();

        assertEquals(expectedResponse, actualResponse);
    }

    @After
    public void tearDown() throws IOException {
        clientSocket.close();
        connectionService.close();
        out.close();
        in.close();
    }
}

