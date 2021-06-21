package com.mighty.ninda.exception.comment;

import com.mighty.ninda.exception.ErrorCode;
import com.mighty.ninda.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class CommentAlreadyHateException extends InvalidValueException {

    public CommentAlreadyHateException(String value) {
        super(value, ErrorCode.COMMENT_ALREADY_HATE);
    }

    public CommentAlreadyHateException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
