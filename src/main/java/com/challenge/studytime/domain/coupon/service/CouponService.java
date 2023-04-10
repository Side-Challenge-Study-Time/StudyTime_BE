package com.challenge.studytime.domain.coupon.service;

import com.challenge.studytime.domain.coupon.dto.request.CouponModifyRequestDto;
import com.challenge.studytime.domain.coupon.dto.request.CouponRequestDto;
import com.challenge.studytime.domain.coupon.dto.response.CouponResponseDto;
import com.challenge.studytime.domain.coupon.entity.Coupon;
import com.challenge.studytime.domain.coupon.repository.CouponHistoryRepository;
import com.challenge.studytime.domain.coupon.repository.CouponRepository;
import com.challenge.studytime.global.exception.coupon.CouponNameDuplicationException;
import com.challenge.studytime.global.exception.coupon.NotFoundCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponHistoryRepository couponHistoryRepository;

    @Transactional
    public CouponResponseDto CreateCoupon(CouponRequestDto requestDto){
        if (couponRepository.existsByCouponName(requestDto.getCouponName())) {
            throw new CouponNameDuplicationException(requestDto.getCouponName());
        }
        Coupon coupon = couponRepository.save(Coupon.builder()
                        .couponName(requestDto.getCouponName())
                        .discountValue(requestDto.getDiscountValue())
                        .maxissuedCount(requestDto.getMaxissuedCount())
                        .endAt(requestDto.getEndAt())
                .build());
        return CouponResponseDto.toDto(coupon);
    }
    public List<CouponResponseDto> convertToDtoList(List<Coupon> coupons) {
        List<CouponResponseDto> couponResponseDtos = new ArrayList<>();
        for (Coupon coupon : coupons) {
            CouponResponseDto couponResponseDto = CouponResponseDto.builder()
                    .id(coupon.getId())
                    .couponName(coupon.getCouponName())
                    .assignedAt(coupon.getAssignedAt())
                    .discountValue(coupon.getDiscountValue())
                    .maxissuedCount(coupon.getMaxissuedCount())
                    .endAt(coupon.getEndAt())
                    .build();
            couponResponseDtos.add(couponResponseDto);
        }
        return couponResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<CouponResponseDto> searchCoupon(String couponName){
        if (!couponRepository.existsByCouponName(couponName)){
            throw new NotFoundCoupon("Not Found Coupon");
        }
        List<Coupon> coupons = couponRepository.findCouponsByCouponName(couponName);
        return convertToDtoList(coupons);
    }
    @Transactional(readOnly = true)
    public List<CouponResponseDto> fullSearchCoupon(){
        List<Coupon> coupons = couponRepository.findAll();
        return convertToDtoList(coupons);
    }
    @Transactional
    public String modifyCoupon(CouponModifyRequestDto couponModifyRequestDto){
        if (!couponRepository.existsByCouponName(couponModifyRequestDto.getCouponName())){
            throw new NotFoundCoupon("Not Found Coupon");
        }
        Coupon coupon = couponRepository.findByCouponName(couponModifyRequestDto.getCouponName());
        coupon.modifyTime(couponModifyRequestDto.getTime());
        /**
         * couponRepository.save(coupon);
         * update coupon set start_time=?, coupon_name=?, discount_value=?, end_time=?, max_value=? where coupon_id=? 쿼리 발생
         */
        return couponModifyRequestDto.getCouponName();
    }
    @Transactional
    public void deleteByCoupon(String couponName){
        if (!couponRepository.existsByCouponName(couponName)) {
            throw new NotFoundCoupon("Not Found Coupon");
        }
        couponRepository.deleteCouponByCouponName(couponName);
    }
}
