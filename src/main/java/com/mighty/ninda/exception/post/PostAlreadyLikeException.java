package com.mighty.ninda.exception.post;

import com.mighty.ninda.exception.ErrorCode;
import com.mighty.ninda.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class PostAlreadyLikeException extends InvalidValueException {

    public PostAlreadyLikeException(String value) {
        super(value, ErrorCode.ONE_LINE_COMMENT_ALREADY_LIKE);
    }

    public PostAlreadyLikeException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
