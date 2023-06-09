package com.challenge.studytime.domain.coupon.dto.response;

import com.challenge.studytime.domain.coupon.entity.Coupon;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CouponHistoryResponseDto {
    private int discountValue;
    private String couponName;
    private Long couponId;
    private Long userId;
    private LocalDateTime endAt;
    public static CouponHistoryResponseDto doDto(Coupon coupon, LoginUserDto userDto){
        return CouponHistoryResponseDto.builder()
                .discountValue(coupon.getDiscountValue())
                .couponName(coupon.getCouponName())
                .couponId(coupon.getId())
                .userId(userDto.getMemberId())
                .endAt(coupon.getEndAt())
                .build();
    };
}
