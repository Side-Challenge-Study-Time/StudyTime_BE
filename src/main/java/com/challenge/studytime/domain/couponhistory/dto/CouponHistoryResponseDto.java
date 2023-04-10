package com.challenge.studytime.domain.couponhistory.dto;

import com.challenge.studytime.domain.coupon.entity.Coupon;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponHistoryResponseDto {
    private Long couponId;
    private Long userId;
    private LocalDate endAt;
    public static CouponHistoryResponseDto doDto(Coupon coupon, @IfLogin LoginUserDto userDto){
        return CouponHistoryResponseDto.builder()
                .couponId(coupon.getId())
                .userId(userDto.getMemberId())
                .endAt(coupon.getEndAt())
                .build();
    };
}
