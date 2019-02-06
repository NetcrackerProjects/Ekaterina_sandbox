package com.gigssandbox.services;

import com.gigssandbox.entities.Community;
import com.gigssandbox.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommunityService {
    private final Map<String, Community> communities;
    private static final String DEFAULT_COMMUNITY = "default_community";

    public CommunityService() {
        this.communities = new HashMap<>();
        communities.put(DEFAULT_COMMUNITY, Community.builder()
                .id(0)
                .name(DEFAULT_COMMUNITY)
                .members(new ArrayList<>())
                .build());
    }

    public void addUserToDefaultCommunity(User user) {
        addUserToCommunity(user, DEFAULT_COMMUNITY);
    }

    public void addUserToCommunity(User user, String communityName) {
        communities.get(communityName).add(user);
    }

    public void removeUserFromCommunity(User user) {
        String communityName = communities.entrySet().stream()
                .filter(e -> e.getValue().getMembers().contains(user)).map(Map.Entry::getKey)
                .findFirst()
                .orElse(DEFAULT_COMMUNITY);

        communities.get(communityName).remove(user);
    }

    public boolean communityContainsUser(String communityName, User user) {
        return communities.get(communityName).getMembers().contains(user);
    }
}
