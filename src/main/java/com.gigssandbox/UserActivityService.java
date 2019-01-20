package com.gigssandbox;

import com.gigssandbox.entities.Community;
import com.gigssandbox.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
class UserActivityService {
    private static final String REGISTRATION_SUCCESS = "registration_success";
    private static final String LOGIN_SUCCESS = "log_in_success";
    private static final String INCORRECT_PASSWORD = "incorrect_password";

    private Map<String, User> users;
    private Map<String, Community> communities;

    UserActivityService() {
        this.users = new HashMap<>();
        this.communities = new HashMap<>();
    }

    String logUserIn(Map<String, String> parameters) {
        String username = parameters.get("username");
        int passwordHash = Integer.parseInt(parameters.get("password_hash"));

        if (!users.containsKey(username)) {
            users.put(parameters.get("username"), User.builder().username(parameters.get("username"))
                    .passwordHash(Integer.parseInt(parameters.get("password_hash"))).build());
            return REGISTRATION_SUCCESS;

        } else if (users.get(username).getPasswordHash() != passwordHash) {
            return INCORRECT_PASSWORD;

        } else {
            return LOGIN_SUCCESS;
        }
    }

    void logUserOut(String username) {
        users.get(username).setUsername(null);
        users.get(username).setPasswordHash(0);
    }

    void addUserToCommunity(String username, Map<String, String> parameters) {
        Community currentCommunity = communities.get(parameters.get("band_name"));
        users.get(username).joinCommunity(currentCommunity);
    }

    void removeUserFromCommunity(String username) {
        users.get(username).leaveCommunity();
    }

    Collection<String> getUpcomingGigs() {
        Collection<String> gigs = new ArrayList<>();
        gigs.add("fake gigs");
        gigs.add("it is a stub");
        return gigs;
    }

    Collection<String> getUpcomingGigsByBand(String bandName) {
        Collection<String> gigs = new ArrayList<>();
        gigs.add("fake gigs");
        gigs.add("it is a stub");
        gigs.add(bandName);
        return gigs;
    }
}