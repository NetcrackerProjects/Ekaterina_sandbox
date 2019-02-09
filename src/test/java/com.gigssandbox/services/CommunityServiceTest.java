package com.gigssandbox.services;

import com.gigssandbox.entities.Community;
import com.gigssandbox.entities.User;
import com.gigssandbox.exceptions.NoAppropriateCommunityException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommunityServiceTest {
    private CommunityService communityService;
    private String communityName;
    private String defaultCommunityName;
    private Community community;
    private User user;
    private Map<String, Community> communities;

    @Before
    public void setUp() {
        String username = "Panic!";
        this.communityName = "At the disco";
        this.defaultCommunityName = "default_community";

        this.community = Community
                .builder()
                .name(communityName)
                .members(new ArrayList<>())
                .build();

        Community defaultCommunity = Community
                .builder()
                .name(defaultCommunityName)
                .members(new ArrayList<>())
                .build();

        this.user = User
                .builder()
                .username(username)
                .build();

        this.communities = new HashMap<>();
        this.communities.put(defaultCommunityName, defaultCommunity);

        this.communityService = new CommunityService(communities);
    }

    @Test
    public void shouldAddUserToCommunityWhenUserEnteredExistingCommunityName() throws Exception {
        communities.put(communityName, community);

        communityService.addUserToCommunity(user, communityName);

        assertTrue(communityService.communityContainsUser(communityName, user));
    }

    @Test
    public void shouldAddUserToDefaultCommunityWhenUserHasRegistered() {
        communityService.addUserToDefaultCommunity(user);

        assertTrue(communityService.communityContainsUser(defaultCommunityName, user));
    }

    @Test
    public void shouldRemoveUserFromCommunityWhenUserWantsToLeaveTheCommunity() {
        community.add(user);
        communities.put(communityName, community);

        communityService.removeUserFromCommunity(user);

        assertFalse(communityService.communityContainsUser(communityName, user));
    }

    @Test(expected = NoAppropriateCommunityException.class)
    public void shouldThrowWhenTHereIsNoCommunitiesIdenticalToPassedOne() throws Exception {
        communityService.addUserToCommunity(user, communityName);
    }
}
