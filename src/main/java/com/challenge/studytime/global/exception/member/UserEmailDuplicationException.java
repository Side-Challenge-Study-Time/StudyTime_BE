package com.challenge.studytime.global.exception.member;

public class UserEmailDuplicationException extends RuntimeException{
    public UserEmailDuplicationException(String email) {
        super("User email is already existed: " + email);
    }
}
