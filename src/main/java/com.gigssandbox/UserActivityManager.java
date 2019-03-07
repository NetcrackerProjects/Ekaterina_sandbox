package com.gigssandbox;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class UserActivityManager {
    private final ExecutorService executorService;

    UserActivityManager() {
        this.executorService = Executors.newFixedThreadPool(100);
    }

    void execute(UserActivity userActivity) {
        executorService.execute(userActivity::start);
    }

    void stop() {
        executorService.shutdown();
    }
}

