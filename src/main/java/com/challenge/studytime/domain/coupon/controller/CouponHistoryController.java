package com.challenge.studytime.domain.coupon.controller;

import com.challenge.studytime.domain.coupon.dto.response.CouponHistoryResponseDto;
import com.challenge.studytime.domain.coupon.service.CouponHistoryService;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/coupon-history")
@RequiredArgsConstructor
public class CouponHistoryController {
    private final CouponHistoryService couponHistoryService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{couponId}")
    public CouponHistoryResponseDto createCouponHistory(@PathVariable("couponId") Long couponId, @IfLogin LoginUserDto userDto) {
        CouponHistoryResponseDto couponHistoryResponseDto = couponHistoryService.createCouponHistory(couponId, userDto);
        return couponHistoryResponseDto;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/user-search")
    public List<CouponHistoryResponseDto> SearchCoupon(@IfLogin LoginUserDto userDto){
        return couponHistoryService.SearchUserCoupon(userDto);
    }
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/use/{couponId}")
    public void useCoupon(@IfLogin LoginUserDto userDto, @PathVariable("couponId") Long couponId){
        couponHistoryService.useCoupon(userDto.getMemberId(), couponId);
    }
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/cancel/{couponId}")
    public void cancelCouponUsage(@IfLogin LoginUserDto userDto, @PathVariable("couponId") Long couponId){
        couponHistoryService.cancelCouponUsage(userDto.getMemberId(), couponId);
    }
}
