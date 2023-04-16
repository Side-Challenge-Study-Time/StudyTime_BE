package com.challenge.studytime.global.exception.reservation;

public class DifferentDateException extends RuntimeException {
    public DifferentDateException(){
        super("Different Date");
    }
}
