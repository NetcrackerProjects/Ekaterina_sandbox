package com.gigssandbox;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Response {
    private String property;

    Response(String property) {
        this.property = property;
    }
}
