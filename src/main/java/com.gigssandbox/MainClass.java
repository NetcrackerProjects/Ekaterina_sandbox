package com.gigssandbox;

public class MainClass {
    public static void main(String[] args) {
        new UserActivity(new UserService(), new CommunityService()).start();
    }
}