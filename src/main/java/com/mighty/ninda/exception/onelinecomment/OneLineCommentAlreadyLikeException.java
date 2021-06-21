package com.mighty.ninda.exception.onelinecomment;

import com.mighty.ninda.exception.ErrorCode;
import com.mighty.ninda.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class OneLineCommentAlreadyLikeException extends InvalidValueException {

    public OneLineCommentAlreadyLikeException(String value) {
        super(value, ErrorCode.ONE_LINE_COMMENT_ALREADY_LIKE);
    }

    public OneLineCommentAlreadyLikeException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
