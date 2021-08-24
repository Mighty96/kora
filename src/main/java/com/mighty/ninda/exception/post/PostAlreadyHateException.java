package com.mighty.ninda.exception.post;

import com.mighty.ninda.exception.ErrorCode;
import com.mighty.ninda.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class PostAlreadyHateException extends InvalidValueException {

    public PostAlreadyHateException(String value) {
        super(value, ErrorCode.ONE_LINE_COMMENT_ALREADY_HATE);
    }

    public PostAlreadyHateException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
