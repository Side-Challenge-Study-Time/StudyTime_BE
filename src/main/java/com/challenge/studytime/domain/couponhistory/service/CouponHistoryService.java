package com.challenge.studytime.domain.couponhistory.service;

import com.challenge.studytime.domain.coupon.entity.Coupon;
import com.challenge.studytime.domain.coupon.repository.CouponRepository;
import com.challenge.studytime.domain.couponhistory.dto.CouponHistoryResponseDto;
import com.challenge.studytime.domain.couponhistory.entity.CouponHistory;
import com.challenge.studytime.domain.couponhistory.repository.CouponHistoryRepository;
import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepositry;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponHistoryService {
    private final CouponHistoryRepository couponHistoryRepository;
    private final CouponRepository couponRepository;
    private final MemberRepositry memberRepositry;

    @Transactional
    public CouponHistoryResponseDto createCouponHistory(Long couponId, @IfLogin LoginUserDto userDto) {
//orElseThrow 수정 ->로 보이게 그리고 exception 하나 만들어서
        // valid 유효성 Integer -> int LocalDate -> LocalDateTime Column 길이 제한 Repository어노테이션 노 필요
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException());
        Member member = memberRepositry.findById(userDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException());

        if (coupon.getMaxissuedCount() <= coupon.getIssuedCount()) {
            throw new RuntimeException("더 이상 발급 할 수 없습니다.");
        }
//        if (couponHistoryRepository.existsByCoupon_IdAndMember_Id(couponId, userDto.getMemberId())){
//            throw new RuntimeException("이미 발급한 쿠폰입니다.");
//        }
        CouponHistory couponHistory = new CouponHistory();
        couponHistory.setCoupon(member, coupon);

        coupon.getCouponHistories().add(couponHistory);
        coupon.setIssuedCount(coupon.getIssuedCount()); // 발급된 쿠폰 개수 증가
        couponRepository.save(coupon);
        memberRepositry.save(member);

        return CouponHistoryResponseDto.doDto(coupon, userDto);
    }
    @Transactional(readOnly = true)
    public List<CouponHistoryResponseDto> fullSearchCoupon(@IfLogin LoginUserDto userDto) {
        Coupon coupon = couponRepository.findById(userDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("Coupon not found with ID: " + userDto.getMemberId()));

        List<CouponHistory> couponHistories = couponHistoryRepository.findAllByCoupon(coupon);

        return couponHistories.stream()
                .map(couponHistory -> CouponHistoryResponseDto.doDto(couponHistory.getCoupon(), userDto))
                .collect(Collectors.toList());
    }
}