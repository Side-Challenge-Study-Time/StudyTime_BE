package com.challenge.studytime.domain.coupon.controller;

import com.challenge.studytime.domain.coupon.dto.CouponRequestDto;
import com.challenge.studytime.domain.coupon.entity.Coupon;
import com.challenge.studytime.domain.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;
    @PostMapping("/api/coupons")
    public Coupon createCoupon(CouponRequestDto requestDto){
        return couponService.CreateCoupon(requestDto);
    }
}
