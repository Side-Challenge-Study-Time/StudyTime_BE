package com.challenge.studytime.global.exception.member;

public class NotFoundMemberid extends RuntimeException{
    public NotFoundMemberid(Long userId) {
        super("Not Found Member Id:  " + userId);
    }
}
