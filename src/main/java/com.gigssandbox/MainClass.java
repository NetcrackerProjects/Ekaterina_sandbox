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
    public static void main(String[] args) throws InterruptedException {
        Map<String, User> users = new HashMap<>();
        Map<String, Community> communities = new HashMap<>();
        Map<String, Gig> gigs = new HashMap<>();

        ConnectionService connectionService = new ConnectionService();
        connectionService.start();

        UserActivityManager userActivityManager = new UserActivityManager();

        Thread connectionServiceThread = new Thread(() -> {
            while (!connectionService.isStopped()) {
                try {
                    SocketConnection connection = connectionService.nextClient();

                    userActivityManager.execute(new UserActivity(new UserService(users), new CommunityService(communities), new GigService(gigs), connection));

                } catch (ConnectionServiceStoppedException e) {
                    break;
                }
            }
        });

        connectionServiceThread.start();

        java.lang.Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            connectionService.stop();
            userActivityManager.stop();
        }));

        connectionServiceThread.join();
    }
}