package com.gigssandbox;

import com.gigssandbox.entities.Community;
import com.gigssandbox.entities.Gig;
import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.ConnectionServiceStoppedException;
import com.gigssandbox.io.sockets.ConnectionService;
import com.gigssandbox.io.sockets.SocketConnection;
import com.gigssandbox.services.CommunityService;
import com.gigssandbox.services.GigService;
import com.gigssandbox.services.UserService;
import java.util.HashMap;
import java.util.Map;

public class MainClass {
    public static void main(String[] args) {
        Map<String, User> users = new HashMap<>();
        Map<String, Community> communities = new HashMap<>();
        Map<String, Gig> gigs = new HashMap<>();

        ConnectionService connectionService = new ConnectionService();
        connectionService.start();

        while (connectionService.isAlive()) {
            try {
            SocketConnection connection = connectionService.nextClient();

            new UserActivity(new UserService(users), new CommunityService(communities), new GigService(gigs), connection).start();

            } catch (ConnectionServiceStoppedException e) {
                break;
            }
        }
    }
}