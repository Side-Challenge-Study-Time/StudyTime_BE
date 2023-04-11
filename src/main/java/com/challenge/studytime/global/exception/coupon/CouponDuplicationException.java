package com.challenge.studytime.global.exception.coupon;

public class CouponDuplicationException extends RuntimeException{
    public CouponDuplicationException(){
        super("Cannot issue coupon");
    }
}
