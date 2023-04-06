package com.challenge.studytime.global.exception;

public class ErrorResponse {
    private  String code;
    private String message;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
