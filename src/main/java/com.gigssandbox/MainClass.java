package com.gigssandbox;

import com.gigssandbox.entities.Community;
import com.gigssandbox.entities.Gig;
import com.gigssandbox.entities.User;
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

        new UserActivity(new UserService(users), new CommunityService(communities), new GigService(gigs)).start();
    }
}