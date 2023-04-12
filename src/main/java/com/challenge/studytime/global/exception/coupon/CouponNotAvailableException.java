package com.challenge.studytime.global.exception.coupon;

public class CouponNotAvailableException extends RuntimeException{

    public CouponNotAvailableException(){
        super("Not available coupon");
    }
}
