package com.gigssandbox.response;

import com.gigssandbox.Result;

public class ResponseFactory {
    private final ResponseStorage responseStorage;

    public ResponseFactory() {
        this.responseStorage = new ResponseStorage();
    }

    public Response create(Result result) {
        return new Response(responseStorage.loadText(result.name().toLowerCase()));
    }
}
