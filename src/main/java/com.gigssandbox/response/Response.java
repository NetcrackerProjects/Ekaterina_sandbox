package com.gigssandbox.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Response {
    private String property;

    public Response(String property) {
        this.property = property;
    }
}