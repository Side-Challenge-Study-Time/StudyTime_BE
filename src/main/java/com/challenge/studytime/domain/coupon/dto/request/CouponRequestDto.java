package com.challenge.studytime.domain.coupon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponRequestDto {
    @NotNull
    private String couponName;
    @NotNull
    private int discountValue;
    @NotNull
    private int maxissuedCount;
    @NotNull
    private LocalDateTime endAt;
}
