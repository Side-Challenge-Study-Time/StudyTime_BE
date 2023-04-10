package com.challenge.studytime.domain.coupon.repository;

import com.challenge.studytime.domain.coupon.entity.Coupon;
import com.challenge.studytime.domain.coupon.entity.CouponHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponHistoryRepository extends JpaRepository<CouponHistory, Long> {
    boolean existsByCoupon_IdAndMember_Id(Long coupon, Long member);
    List<CouponHistory> findAllByCoupon(Coupon coupon);
}
