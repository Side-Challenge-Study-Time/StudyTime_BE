package com.challenge.studytime.domain.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponRequestDto {
    private Integer discountValue;
    //최대 가능한 발급 갯수
    private Integer maxissuedCount;
    //만료 시간
    private LocalDate endAt;
}
