package com.mighty.ninda.exception.comment;

import com.mighty.ninda.exception.ErrorCode;
import com.mighty.ninda.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class CommentAlreadyLikeException extends InvalidValueException {

    public CommentAlreadyLikeException(String value) {
        super(value, ErrorCode.COMMENT_ALREADY_LIKE);
    }

    public CommentAlreadyLikeException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
