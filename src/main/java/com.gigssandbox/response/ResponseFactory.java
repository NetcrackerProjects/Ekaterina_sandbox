package com.gigssandbox.response;

import com.gigssandbox.Result;

public class ResponseFactory {
    private static final ResponseStore responseStore = new ResponseStore();

    private ResponseFactory() {

    }

    public static Response create(Result result) {
        return new Response(responseStore.loadText(result.name().toLowerCase()));
    }
}
