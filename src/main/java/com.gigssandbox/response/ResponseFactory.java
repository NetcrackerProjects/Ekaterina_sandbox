package com.gigssandbox.response;

import com.gigssandbox.Result;

public class ResponseFactory {
    private ResponseFactory() {

    }

    public static Response create(Result result) {
        return new Response(new ResponseStore().getStored(result.name().toLowerCase()));
    }
}
