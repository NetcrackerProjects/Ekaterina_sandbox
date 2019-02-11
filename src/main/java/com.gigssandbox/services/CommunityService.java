package com.gigssandbox.services;

import com.gigssandbox.entities.Community;
import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.NoSuchCommunityException;
import java.util.ArrayList;
import java.util.Map;

public class CommunityService {
    private static final String DEFAULT_COMMUNITY = "default_community";
    private final Map<String, Community> communities;

    public CommunityService(Map<String, Community> communities) {
        this.communities = communities;
        communities.put(DEFAULT_COMMUNITY, Community.builder()
                .id(0)
                .name(DEFAULT_COMMUNITY)
                .members(new ArrayList<>())
                .build());
    }

    public void addUserToDefaultCommunity(User user) {
        communities.get(DEFAULT_COMMUNITY).add(user);
    }

    public void addUserToCommunity(User user, String communityName) throws NoSuchCommunityException {
        if (!communities.containsKey(communityName)) {
            throw new NoSuchCommunityException();
        }

        communities.get(communityName).add(user);
    }

    public void removeUserFromCommunity(User user) {
        String communityName = communities.entrySet().stream()
                .filter(e -> e.getValue().getMembers().contains(user)).map(Map.Entry::getKey)
                .findFirst()
                .orElse(DEFAULT_COMMUNITY);

        communities.get(communityName).remove(user);
    }

    boolean communityContainsUser(String communityName, User user) {
        return communities.get(communityName).getMembers().contains(user);
    }
}
