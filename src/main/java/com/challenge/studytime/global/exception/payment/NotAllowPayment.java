package com.challenge.studytime.global.exception.payment;

public class NotAllowPayment extends RuntimeException{
    public NotAllowPayment(){
        super("Not Allow Payment");
    }
}
