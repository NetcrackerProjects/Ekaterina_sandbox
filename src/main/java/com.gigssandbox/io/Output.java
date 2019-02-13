package com.gigssandbox.io;

import com.gigssandbox.response.Response;

public interface Output {
    void send(Response response);
}