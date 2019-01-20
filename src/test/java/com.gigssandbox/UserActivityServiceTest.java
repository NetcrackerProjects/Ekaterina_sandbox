package com.gigssandbox;

import com.gigssandbox.entities.Community;
import com.gigssandbox.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class UserActivityServiceTest {
    private UserActivityService userActivityService;

    @Before
    public void setUp() {
        this.userActivityService = new UserActivityService();
    }

//    @Test
//    public void shouldSetUserLoginAndPasswordWhenAppAskedForLoginAndPassword() {
//        User user = new User();
//        User userMock = Mockito.mock(User.class);
//        when(userMock.getUsername()).thenReturn("ben_bruice");
//        when(userMock.getPasswordHash()).thenReturn(("everyday00playing00zeros").hashCode());
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("login", "ben_bruice");
//        parameters.put("password_hash", String.valueOf(("everyday00playing00zeros").hashCode()));
//
//        userActivityService.logUserIn(user, parameters);
//
//        assertEquals(userMock.getUsername(), user.getUsername());
//        assertEquals(userMock.getPasswordHash(), user.getPasswordHash());
//    }
//
//    @Test
//    public void shouldAddUserToCommunityWhenUserHasChosenCommunity() {
//        Map<String, Community> fakeCommunities = new HashMap<>();
//        fakeCommunities.put("BGPE", Community.builder().name("BGPE").build());
//        fakeCommunities.get("BGPE").setMembersIds(new HashSet<>());
//        userActivityService.setCommunities(fakeCommunities);
//        userActivityService.setUser(User.builder().id(0).build());
//        Map<String, Community> parameters = new HashMap<>();
//        parameters.put("commuity_name", Community.builder().name("BGPE").build());
//
//        userActivityService.addUserToCommunity();
//
//        assertTrue(userActivityService.getCommunities().get("BGPE").getMembersIds().contains(0));
//    }
}
