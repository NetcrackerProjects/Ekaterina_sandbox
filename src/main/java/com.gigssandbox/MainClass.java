package com.gigssandbox;

import com.gigssandbox.services.CommunityService;
import com.gigssandbox.services.GigService;
import com.gigssandbox.services.UserService;

public class MainClass {
    public static void main(String[] args) {
        new UserActivity(new UserService(), new CommunityService(), new GigService()).start();
    }
}