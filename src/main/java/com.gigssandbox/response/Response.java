package com.gigssandbox.response;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Response {
    private String property;

    Response(String property) {
        this.property = property;
    }

    public String getText() {
        return property;
    }
}