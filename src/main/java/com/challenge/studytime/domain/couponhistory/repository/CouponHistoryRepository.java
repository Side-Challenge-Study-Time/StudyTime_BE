package com.challenge.studytime.domain.couponhistory.repository;

import com.challenge.studytime.domain.couponhistory.entity.CouponHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponHistoryRepository extends JpaRepository<CouponHistory, Long> {
    boolean existsByCoupon_IdAndMember_Id(Long coupon, Long member);
}
