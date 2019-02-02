package com.gigssandbox;

class ResponseFactory {
    private ResponseFactory() {

    }

    static Response createResponseFrom(Result result) {
        return new Response(result.name().toLowerCase());
    }
}
