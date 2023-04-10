package com.challenge.studytime.global.exception.coupon;

public class NotFoundCoupon extends RuntimeException{
    public NotFoundCoupon(String couponName) {
        super("Not Found Coupon : " + couponName);
    }
}
