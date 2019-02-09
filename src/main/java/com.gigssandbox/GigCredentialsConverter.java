package com.gigssandbox;

class GigCredentialsConverter {
    String create(String headliner, String gigDate) {
        return headliner.concat(":").concat(gigDate);
    }
}
