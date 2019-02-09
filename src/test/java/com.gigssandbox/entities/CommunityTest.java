package com.gigssandbox.entities;

import java.util.Collection;
import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommunityTest {
    private Community community;
    private User user;

    @Before
    public void setUp() {
        this.community = new Community();
        this.user = User
                .builder()
                .username("hologram")
                .build();
    }

    @Test
    public void shouldAddUserToChosenCommunityWhenUserWantsToJoinSpecificCommunity() {
        community.add(user);

        assertTrue(community.getMembers().contains(user));
    }

    @Test
    public void shouldRemoveUserFromCommunityWhenUserWantsToLeaveCommunity() {
        Collection<User> members = new HashSet<>();
        members.add(user);
        Whitebox.setInternalState(community, "members", members);

        community.remove(user);

        assertFalse(community.getMembers().contains(user));
    }
}
