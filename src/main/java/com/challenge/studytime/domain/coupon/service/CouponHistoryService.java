package com.challenge.studytime.domain.coupon.service;

import com.challenge.studytime.domain.coupon.entity.Coupon;
import com.challenge.studytime.domain.coupon.repository.CouponRepository;
import com.challenge.studytime.domain.coupon.dto.response.CouponHistoryResponseDto;
import com.challenge.studytime.domain.coupon.entity.CouponHistory;
import com.challenge.studytime.domain.coupon.repository.CouponHistoryRepository;
import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepositry;
import com.challenge.studytime.global.exception.coupon.CouponDuplicationException;
import com.challenge.studytime.global.exception.coupon.CouponNotAvailableException;
import com.challenge.studytime.global.exception.coupon.NotFoundCoupon;
import com.challenge.studytime.global.exception.member.NotFoundMemberid;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponHistoryService {
    private final CouponHistoryRepository couponHistoryRepository;
    private final CouponRepository couponRepository;
    private final MemberRepositry memberRepositry;

    @Transactional
    public CouponHistoryResponseDto registerCouponHistory(Long couponId, LoginUserDto userDto) {
        UUID uuid = UUID.randomUUID();
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new NotFoundCoupon(couponId.toString()));
        Member member = memberRepositry.findById(userDto.getMemberId())
                .orElseThrow(() -> new NotFoundMemberid(userDto.getMemberId()));
        if (coupon.getMaxissuedCount() <= couponHistoryRepository.countByCouponId(userDto.getMemberId())) {
            throw new CouponDuplicationException();
        }
        if (couponHistoryRepository.existsByCouponIdAndMemberId(couponId, userDto.getMemberId())){
            throw new CouponDuplicationException();
        }
        CouponHistory couponHistory = couponHistoryRepository.save(
                CouponHistory.builder()
                        .uuid(uuid)
                        .build());

        couponHistory.setCouponFromCouponHistory(coupon);
        couponHistory.setMemberFromCouponHistory(member);

        return CouponHistoryResponseDto.doDto(coupon, userDto);
    }
    @Transactional(readOnly = true)
    public List<CouponHistoryResponseDto> detailUserCoupon(LoginUserDto userDto) {
        List<CouponHistory> couponHistories = couponHistoryRepository.findAllByMember_Id(userDto.getMemberId());

        List<CouponHistoryResponseDto> couponHistoryResponseDtos = new ArrayList<>();

        for (CouponHistory couponHistory : couponHistories){
            CouponHistoryResponseDto dto = CouponHistoryResponseDto.builder()
                    .userId(userDto.getMemberId())
                    .discountValue(couponHistory.getCoupon().getDiscountValue())
                    .couponName(couponHistory.getCoupon().getCouponName())
                    .couponId(couponHistory.getCoupon().getId())
                    .endAt(couponHistory.getCoupon().getEndAt())
                    .build();
            couponHistoryResponseDtos.add(dto);
        }
        return  couponHistoryResponseDtos;
    }
    @Transactional
    public void useCoupon(Long couponId, Long memberId) {
        LocalDateTime now = LocalDateTime.now();
        CouponHistory couponHistory = couponHistoryRepository.findByCouponIdAndMemberId(couponId, memberId)
                .orElseThrow(() -> new NotFoundCoupon(couponId.toString()));
        if (couponHistory.getCoupon().getEndAt().isBefore(now)){
            throw new CouponNotAvailableException();
        }if (couponHistory.getUsed()) {
            throw new CouponNotAvailableException();
        }
        couponHistory.couponUsed(true);
    }
    @Transactional
    public void cancelCouponUsage(Long couponId, Long memberId){
        LocalDateTime now = LocalDateTime.now();
        CouponHistory couponHistory = couponHistoryRepository.findByCouponIdAndMemberId(couponId, memberId)
                .orElseThrow(() -> new NotFoundCoupon(couponId.toString()));
        if (!couponHistory.getUsed()) {
            throw new CouponNotAvailableException();
        }
        couponHistory.couponUsed(false);
    }
}