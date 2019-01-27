package com.gigssandbox;

import com.gigssandbox.entities.Community;
import com.gigssandbox.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class CommunityService {
    private final Map<String, Community> communities;
    private static final String DEFAULT_COMMUNITY = "default_community";

    CommunityService() {
        this.communities = new HashMap<>();
        communities.put(DEFAULT_COMMUNITY, Community.builder()
                .id(0)
                .name(DEFAULT_COMMUNITY)
                .members(new ArrayList<>())
                .build());
    }

    void addUserToDefaultCommunity(User user) {
        addUserToCommunity(user, DEFAULT_COMMUNITY);
    }

    void addUserToCommunity(User user, String communityName) {
        communities.get(communityName).getMembers().add(user);
    }

    void removeUserFromCommunity(User user) {
        String communityName = communities.entrySet().stream()
                .filter(e -> e.getValue().getMembers().contains(user)).map(Map.Entry::getKey)
                .findFirst()
                .orElse(DEFAULT_COMMUNITY);

        communities.get(communityName).getMembers().remove(user);
    }

    boolean communityContainsUser(String communityName, User user) {
        return communities.get(communityName).getMembers().contains(user);
    }
}
