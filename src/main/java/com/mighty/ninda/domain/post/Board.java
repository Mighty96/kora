package com.mighty.ninda.domain.post;

import lombok.Getter;

@Getter
public enum Board {

    FREE("free"),
    MULTI("multi"),
    FRIEND("friend");

    public final String value;

    Board(String value) {
        this.value = value;
    }


}
