package com.challenge.studytime.domain.coupon.service;

import com.challenge.studytime.domain.coupon.dto.CouponRequestDto;
import com.challenge.studytime.domain.coupon.entity.Coupon;
import com.challenge.studytime.domain.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;

    public Coupon CreateCoupon(CouponRequestDto requestDto){
        Coupon coupon = new Coupon(requestDto);
        return couponRepository.save(coupon);
    }
}
