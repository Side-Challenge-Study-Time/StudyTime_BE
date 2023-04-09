package com.challenge.studytime.domain.couponhistory.service;

import com.challenge.studytime.domain.coupon.entity.Coupon;
import com.challenge.studytime.domain.coupon.repository.CouponRepository;
import com.challenge.studytime.domain.couponhistory.dto.CouponHistoryRequestDto;
import com.challenge.studytime.domain.couponhistory.dto.CouponHistoryResponseDto;
import com.challenge.studytime.domain.couponhistory.entity.CouponHistory;
import com.challenge.studytime.domain.couponhistory.repository.CouponHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponHistoryService {
    private final CouponHistoryRepository couponHistoryRepository;
    private final CouponRepository couponRepository;
//    private final UserRepository userRepository;

    public CouponHistoryResponseDto createCouponHistory(CouponHistoryRequestDto requestDto, Long couponId) {
//        Long userId = requestDto.getUserId();

        Coupon coupon = couponRepository.findById(couponId).orElseThrow(RuntimeException::new);
//        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (coupon.getMaxissuedCount() <= coupon.getIssuedCount()) {
            throw new RuntimeException("더 이상 발급 할 수 없습니다.");
        }
//        if (couponHistoryRepository.existsCouponHistoriesByCoupon_IdAndAndUserID(coupon, requestDto.getUserId())){
//            throw new RuntimeException("이미 발급한 쿠폰입니다.");
//        }
        CouponHistory couponHistory = new CouponHistory();
        couponHistory.setCoupon(coupon, requestDto);
//        couponHistory.setUser(user);

        coupon.getCouponHistories().add(couponHistory);
        coupon.setIssuedCount(coupon.getIssuedCount() + 1); // 발급된 쿠폰 개수 증가
        couponRepository.save(coupon);

        return new CouponHistoryResponseDto();
    }
}
