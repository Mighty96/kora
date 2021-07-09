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

    public static Board of(String value) {
        for (Board bd : Board.values()) {
            if (bd.getValue().equals(value)) {
                return bd;
            }
        }
        return null;
    }

}
