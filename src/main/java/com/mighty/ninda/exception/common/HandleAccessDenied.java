package com.mighty.ninda.exception.common;

import com.mighty.ninda.exception.ErrorCode;
import com.mighty.ninda.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class HandleAccessDenied extends InvalidValueException {

    public HandleAccessDenied(String value) {
        super(value, ErrorCode.HANDLE_ACCESS_DENIED);
    }

    public HandleAccessDenied(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
