package com.gigssandbox.response;

import com.gigssandbox.Result;

public class ResponseFactory {
    private final ResponseStore responseStore;

    public ResponseFactory() {
        this.responseStore = new ResponseStore();
    }

    public Response create(Result result) {
        return new Response(responseStore.loadText(result.name().toLowerCase()));
    }
}
