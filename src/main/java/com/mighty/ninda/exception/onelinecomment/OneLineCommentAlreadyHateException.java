package com.mighty.ninda.exception.onelinecomment;

import com.mighty.ninda.exception.ErrorCode;
import com.mighty.ninda.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class OneLineCommentAlreadyHateException extends InvalidValueException {

    public OneLineCommentAlreadyHateException(String value) {
        super(value, ErrorCode.ONE_LINE_COMMENT_ALREADY_HATE);
    }

    public OneLineCommentAlreadyHateException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
