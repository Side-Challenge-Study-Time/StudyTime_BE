package com.challenge.studytime.domain.coupon.service;

import com.challenge.studytime.domain.coupon.dto.CouponRequestDto;
import com.challenge.studytime.domain.coupon.entity.Coupon;
import com.challenge.studytime.domain.coupon.repository.CouponRepository;
import com.challenge.studytime.domain.couponhistory.entity.CouponHistory;
import com.challenge.studytime.domain.couponhistory.repository.CouponHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponHistoryRepository couponHistoryRepository;
    public CouponRequestDto issueCoupon(Long userId, String couponCode) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

        Coupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() -> new RuntimeException("Coupon not found."));

        if (coupon.getIssuedCount() >= coupon.getMaxIssuanceCount()) {
            throw new RuntimeException("Coupon issuance limit exceeded.");
        }

        CouponHistory couponHistory = CouponHistory.builder()
                .coupon(coupon)
                .user(user)
                .used(false)
                .build();

        coupon.getCouponHistories().add(couponHistory);
        coupon.updateIssuedCount();

        return CouponDto.from(coupon);
    }
}
