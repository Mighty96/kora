package com.mighty.ninda.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    // Post
    POST_ALREADY_LIKE(400, "P01", "Post is already liked"),
    POST_ALREADY_HATE(400, "P02", "Post is already hated"),

    // OneLineComment
    ONE_LINE_COMMENT_ALREADY_LIKE(400, "O01", "OneLineComment is already liked"),
    ONE_LINE_COMMENT_ALREADY_HATE(400, "O02", "OneLineComment is already hated"),

    // Comment
    COMMENT_ALREADY_LIKE(400, "O01", "Comment is already liked"),
    COMMENT_ALREADY_HATE(400, "O02", "Comment is already hated"),;

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
