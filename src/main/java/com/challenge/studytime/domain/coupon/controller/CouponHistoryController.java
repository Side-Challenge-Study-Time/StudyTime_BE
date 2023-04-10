package com.challenge.studytime.domain.coupon.controller;

import com.challenge.studytime.domain.coupon.dto.response.CouponHistoryResponseDto;
import com.challenge.studytime.domain.coupon.service.CouponHistoryService;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/coupon-history")
@RequiredArgsConstructor
public class CouponHistoryController {
    private final CouponHistoryService couponHistoryService;
    @PostMapping("/{couponId}")
    public ResponseEntity<CouponHistoryResponseDto> createCouponHistory(@PathVariable("couponId") Long couponId, @IfLogin LoginUserDto userDto) {
        CouponHistoryResponseDto couponHistoryResponseDto = couponHistoryService.createCouponHistory(couponId, userDto);
        return ResponseEntity.ok(couponHistoryResponseDto);
    }
    @GetMapping("/search")
    public ResponseEntity fullSearchCoupon(@IfLogin LoginUserDto userDto){
        return ResponseEntity.ok(couponHistoryService.fullSearchCoupon(userDto));
    }
}
