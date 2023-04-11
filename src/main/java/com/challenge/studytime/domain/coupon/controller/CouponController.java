package com.challenge.studytime.domain.coupon.controller;

import com.challenge.studytime.domain.coupon.dto.request.CouponModifyRequestDto;
import com.challenge.studytime.domain.coupon.dto.request.CouponRequestDto;
import com.challenge.studytime.domain.coupon.dto.response.CouponResponseDto;
import com.challenge.studytime.domain.coupon.entity.Coupon;
import com.challenge.studytime.domain.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;
    @PostMapping("/api/coupons")
    public CouponResponseDto createCoupon(@RequestBody @Valid CouponRequestDto requestDto){
        return couponService.CreateCoupon(requestDto);
    }
    @GetMapping("/search-coupon")
    public List<CouponResponseDto> searchCoupon(@RequestBody @Valid CouponRequestDto requestDto){
        return couponService.searchCoupon(requestDto.getCouponName());
    }
    @GetMapping("/fullsearch-coupon")
    public List<CouponResponseDto> fullSearchCoupon(){
        return couponService.fullSearchCoupon();
    }
    @PatchMapping("/modify-coupon")
    public String modifyCoupon(@RequestBody @Valid CouponModifyRequestDto couponModifyRequestDto){
       return couponService.modifyCoupon(couponModifyRequestDto);
    }
    @DeleteMapping("/delete")
    public void deleteByCoupon(@RequestBody @Valid CouponModifyRequestDto couponModifyRequestDto){
        couponService.deleteByCoupon(couponModifyRequestDto.getCouponName());
    }
}
