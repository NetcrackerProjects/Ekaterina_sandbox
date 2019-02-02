package com.gigssandbox;

import com.gigssandbox.entities.Community;
import com.gigssandbox.entities.User;
import java.util.Collection;
import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommunityTest {
    private Community community;

    @Before
    public void setUp() {
        this.community = new Community();
    }

    @Test
    public void shouldAddUserToChosenCommunityWhenUserWantsToJoinSpecificCommunity() {
        User user = User.builder().username("hologram").build();

        community.add(user);

        assertTrue(community.getMembers().contains(user));
    }

    @Test
    public void shouldRemoveUserFromCommunityWhenUserWantsToLeaveCommunity() {
        User user = User.builder().username("leech").build();
        Collection<User> members = new HashSet<>();
        members.add(user);
        Whitebox.setInternalState(community, "members", members);

        community.remove(user);

        assertFalse(community.getMembers().contains(user));
    }
}
