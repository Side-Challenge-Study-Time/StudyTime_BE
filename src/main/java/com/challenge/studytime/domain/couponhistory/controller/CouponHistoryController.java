package com.challenge.studytime.domain.couponhistory.controller;

import com.challenge.studytime.domain.couponhistory.dto.CouponHistoryRequestDto;
import com.challenge.studytime.domain.couponhistory.dto.CouponHistoryResponseDto;
import com.challenge.studytime.domain.couponhistory.service.CouponHistoryService;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


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
}
