package com.challenge.studytime.global.exception.coupon;

public class CouponNameDuplicationException extends RuntimeException{
    public CouponNameDuplicationException(String codeName){
        super("CouponName is already existed: " + codeName);
    }
}
