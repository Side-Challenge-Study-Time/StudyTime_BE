package com.challenge.studytime.domain.coupon.dto.response;

import com.challenge.studytime.domain.coupon.entity.Coupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponseDto {
    private Long id;
    private String couponName;
    private int discountValue;
    private int maxissuedCount;
    private LocalDateTime assignedAt;
    private LocalDateTime endAt;
    public static CouponResponseDto toDto(Coupon coupon){
        return CouponResponseDto.builder()
                .id(coupon.getId())
                .couponName(coupon.getCouponName())
                .discountValue(coupon.getDiscountValue())
                .assignedAt(LocalDateTime.now())
                .endAt(coupon.getEndAt())
                .maxissuedCount(coupon.getMaxissuedCount())
                .build();
    }
}
