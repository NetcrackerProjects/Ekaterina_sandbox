package com.gigssandbox;

import com.gigssandbox.entities.Community;
import com.gigssandbox.entities.User;
import com.gigssandbox.services.CommunityService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommunityServiceTest {
    private CommunityService communityService;

    @Before
    public void setUp() {
        this.communityService = new CommunityService();
    }

    @Test
    public void shouldAddUserToCommunityWhenUserEnteredExistingCommunityName() {
        String username = "Panic!";
        String communityName = "At the disco";
        Community community = Community.builder().name(communityName).members(new ArrayList<>()).build();
        Whitebox.setInternalState(communityService, "communities", Collections.singletonMap(communityName, community));

        User user = User.builder().username(username).build();
        communityService.addUserToCommunity(user, communityName);

        assertTrue(communityService.communityContainsUser(communityName, user));
    }

    @Test
    public void shouldAddUserToDefaultCommunityWhenUserHasRegistered() {
        String username = "Retrograde";
        String defaultCommunityName = "default_community";
        Community community = Community.builder().name(defaultCommunityName).members(new ArrayList<>()).build();
        Whitebox.setInternalState(communityService, "communities", Collections.singletonMap(defaultCommunityName, community));

        User user = User.builder().username(username).build();
        communityService.addUserToDefaultCommunity(user);

        assertTrue(communityService.communityContainsUser(defaultCommunityName, user));
    }

    @Test
    public void shouldRemoveUserFromCommunityWhenUserWantsToLeaveTheCommunity() {
        String username = "crystal_lake";
        String communityName = "The sign";
        User user = User.builder().username(username).build();
        Collection<User> members = new ArrayList<>();
        members.add(user);
        Community community = Community.builder().name(communityName).members(members).build();
        Whitebox.setInternalState(communityService, "communities", Collections.singletonMap(communityName, community));
        
        communityService.removeUserFromCommunity(user);

        assertFalse(communityService.communityContainsUser(communityName, user));
    }
}
