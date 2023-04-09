package com.challenge.studytime.global.exception.study;

public class MembersWhoInvalidJoinCount extends RuntimeException{
    public MembersWhoInvalidJoinCount() {
        super("The number of members who can join has been exceeded.");
    }
}
