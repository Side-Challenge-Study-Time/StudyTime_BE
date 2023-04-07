package com.challenge.studytime.global.exception.member;

public class NotFoundMemberEmail extends RuntimeException{
    public NotFoundMemberEmail(String email) {
        super("USER NOT Found: " + email);
    }
}
