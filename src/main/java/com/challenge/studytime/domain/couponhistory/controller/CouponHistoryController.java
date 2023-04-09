package com.challenge.studytime.domain.couponhistory.controller;

import com.challenge.studytime.domain.couponhistory.dto.CouponHistoryRequestDto;
import com.challenge.studytime.domain.couponhistory.dto.CouponHistoryResponseDto;
import com.challenge.studytime.domain.couponhistory.service.CouponHistoryService;
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
    public ResponseEntity<CouponHistoryResponseDto> createCouponHistory(@RequestBody CouponHistoryRequestDto couponHistoryRequestDto, @PathVariable("couponId") Long couponId) {
        CouponHistoryResponseDto couponHistoryResponseDto = couponHistoryService.createCouponHistory(couponHistoryRequestDto, couponId);
        return ResponseEntity.ok(couponHistoryResponseDto);
    }
}
