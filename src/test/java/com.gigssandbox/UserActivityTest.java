package com.gigssandbox;

import com.gigssandbox.io.sockets.ConnectionService;
import com.gigssandbox.services.CommunityService;
import com.gigssandbox.services.GigService;
import com.gigssandbox.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class UserActivityTest {
    private Socket clientSocket;

    private DataOutputStream out;
    private BufferedReader in;

    private UserActivity userActivity;
    private ConnectionService connectionService;

    @Before
    public void setUp() throws Exception {
        this.connectionService = new ConnectionService();
        connectionService.start();

        this.clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress("localhost", 1234));

        this.out = new DataOutputStream(clientSocket.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        this.userActivity = new UserActivity(
                new UserService(new HashMap<>()),
                new CommunityService(new HashMap<>()),
                new GigService(new HashMap<>()),
                connectionService.nextClient()
        );
    }

    @Test
    public void shouldRespondWhenRegistrationCommandIsSent() throws IOException {
        new UserActivityManager().execute(userActivity);

        out.write("register raccoon 1011 \n".getBytes(StandardCharsets.UTF_8));

        String actualResponse = in.readLine();
        String expectedResponse = "You have successfully registered";
        assertEquals(expectedResponse, actualResponse);
    }

    @After
    public void tearDown() throws IOException {
        clientSocket.close();
        out.close();
        connectionService.close();
        in.close();
    }
}

