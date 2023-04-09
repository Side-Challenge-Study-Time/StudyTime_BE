package com.challenge.studytime.domain.couponhistory.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class CouponHistoryResponseDto {
    private Long id;
    private UUID couponId;
    private Long userId;
    private LocalDate endAt;
}
