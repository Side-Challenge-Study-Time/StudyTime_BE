package com.challenge.studytime.domain.coupon.repository;

import com.challenge.studytime.domain.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    boolean existsByCouponName(String couponName);
    List<Coupon> findCouponsByCouponName(String couponName);
    Coupon findByCouponName(String couponName);
    void deleteCouponByCouponName(String couponName);
}
