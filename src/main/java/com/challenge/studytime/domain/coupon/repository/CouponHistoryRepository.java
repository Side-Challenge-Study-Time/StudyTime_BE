package com.challenge.studytime.domain.coupon.repository;

import com.challenge.studytime.domain.coupon.entity.CouponHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface CouponHistoryRepository extends JpaRepository<CouponHistory, Long> , QuerydslPredicateExecutor<CouponHistory> {
    boolean existsByCouponIdAndMemberId(Long coupon, Long member);
    List<CouponHistory> findAllByMember_Id(Long memberId);
    @Query("select count(c) from CouponHistory c where c.coupon.id = ?1")
    long countByCouponId(Long couponId);
    Optional<CouponHistory> findByCouponIdAndMemberId(Long couponId, Long memberId);
}
