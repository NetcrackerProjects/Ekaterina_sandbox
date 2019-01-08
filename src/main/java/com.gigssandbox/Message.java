package com.gigssandbox;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder(toBuilder = true)
class Message implements Serializable {
    private MessageType type;
    private String content;
}
