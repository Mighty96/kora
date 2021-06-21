package com.mighty.ninda.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private int status;
    private String message;
    private String code;
    private String outputMessage;

    public ErrorResponse(ErrorCode errorCode, String outputMessage){
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
        this.outputMessage = outputMessage;
    }

}
