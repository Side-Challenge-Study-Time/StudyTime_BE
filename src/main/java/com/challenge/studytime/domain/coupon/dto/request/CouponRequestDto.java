package com.challenge.studytime.domain.coupon.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponRequestDto {
    @NotNull(message = "Coupon name is empty")
    private String couponName;
    @NotNull(message = "discountValue is empty")
    private int discountValue;
    @NotNull(message = "maxValue is empty")
    private int maxissuedCount;
    @NotNull(message = "endTime is empty")
    private LocalDateTime endAt;
}
