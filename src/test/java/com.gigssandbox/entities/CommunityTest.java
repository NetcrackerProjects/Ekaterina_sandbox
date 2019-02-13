package com.gigssandbox.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommunityTest {
    private Community community;
    private User user;

    @Before
    public void setUp() {
        this.community = new Community();
        this.user = User.builder()
                .username("hologram")
                .build();
    }

    @Test
    public void shouldAddUserToChosenCommunityWhenUserWantsToJoinSpecificCommunity() {
        this.community.add(user);

        assertTrue(community.getMembers().contains(user));
    }

    @Test
    public void shouldRemoveUserFromCommunityWhenUserWantsToLeaveCommunity() {
        this.community.add(user);

        this.community.remove(user);

        assertFalse(community.getMembers().contains(user));
    }
}
