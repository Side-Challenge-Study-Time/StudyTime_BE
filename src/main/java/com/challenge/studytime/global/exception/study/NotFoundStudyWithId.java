package com.challenge.studytime.global.exception.study;

public class NotFoundStudyWithId extends RuntimeException{
    public NotFoundStudyWithId(Long id) {
        super("Not Found Study With id: " + id);
    }
}
