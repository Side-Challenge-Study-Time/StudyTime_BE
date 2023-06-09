package com.challenge.studytime.domain.coupon.controller;

import com.challenge.studytime.domain.coupon.dto.request.CouponModifyRequestDto;
import com.challenge.studytime.domain.coupon.dto.request.CouponRequestDto;
import com.challenge.studytime.domain.coupon.dto.response.CouponResponseDto;
import com.challenge.studytime.domain.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public CouponResponseDto registerCoupon(@RequestBody @Valid CouponRequestDto requestDto){
        return couponService.registerCoupon(requestDto);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public List<CouponResponseDto> detailCoupon(@RequestBody @Valid CouponRequestDto requestDto){
        return couponService.detailCoupon(requestDto.getCouponName());
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public List<CouponResponseDto> fullSearchCoupon(){
        return couponService.fullSearchCoupon();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping()
    public String modifyCoupon(@RequestBody @Valid CouponModifyRequestDto couponModifyRequestDto){
       return couponService.modifyCoupon(couponModifyRequestDto);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping()
    public void deleteByCoupon(@RequestBody @Valid CouponModifyRequestDto couponModifyRequestDto){
        couponService.deleteByCoupon(couponModifyRequestDto.getCouponName());
    }
}
