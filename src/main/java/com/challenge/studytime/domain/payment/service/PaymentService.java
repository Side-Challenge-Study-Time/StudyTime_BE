package com.challenge.studytime.domain.payment.service;

import com.challenge.studytime.domain.coupon.entity.Coupon;
import com.challenge.studytime.domain.coupon.entity.CouponHistory;
import com.challenge.studytime.domain.coupon.repository.CouponHistoryRepository;
import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepository;
import com.challenge.studytime.domain.payment.dto.requestdto.PaymentRequestDto;
import com.challenge.studytime.domain.payment.dto.responsedto.PaymentResponseDto;
import com.challenge.studytime.domain.payment.entity.Payment;
import com.challenge.studytime.domain.payment.repository.PaymentRepository;
import com.challenge.studytime.domain.reservation.entity.Reservation;
import com.challenge.studytime.domain.reservation.repository.ReservationRepository;
import com.challenge.studytime.global.exception.coupon.NotFoundCoupon;
import com.challenge.studytime.global.exception.member.NotFoundMemberid;
import com.challenge.studytime.global.exception.payment.NotAllowPayment;
import com.challenge.studytime.global.exception.payment.NotEnoughPoint;
import com.challenge.studytime.global.exception.reservation.NotFoundReservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final CouponHistoryRepository couponHistoryRepository;

    @Transactional
    public PaymentResponseDto roomPayment(Long memberId, Long reservationId, Long couponId, PaymentRequestDto requestDto){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberid(memberId));
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundReservation());
        CouponHistory couponHistory = couponHistoryRepository.findByCouponIdAndMemberId(couponId, memberId)
                .orElseThrow(() -> new NotFoundCoupon("쿠폰" + couponId));
        int point = 0;
        int value = reservation.getStudyRoom().getPrice() *
                (reservation.getEndDate().getHour() - reservation.getStartDate().getHour());
        int coupon = 0;
        if (couponId != 0){
            coupon = couponHistory.getCoupon().getDiscountValue();
            if (value <= coupon){
                point = 0;
                couponHistory.couponUsed(true);
            }else {
                couponHistory.couponUsed(true);
                member.getPoint().usePoint(value - coupon);
                point = value - coupon;
            }
        }else {
            if (value > member.getPoint().getWallet()){
                new NotEnoughPoint();
            }else {
                member.getPoint().usePoint(value);
                point = value;
            }
        }
        Payment payment = paymentRepository.save(
                Payment.builder()
                        .method(requestDto.getMethod())
                        .paymentDate(LocalDateTime.now())
                        .price(value)
                        .amount(point)
                        .coupon_amount(coupon)
                        .build()
        );
        payment.setReservationFromPayment(reservation);
        return PaymentResponseDto.toDto(payment);
    }
    @Transactional
    public void cancelPayment(Long memberId,Long paymentId, Long couponId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberid(memberId));
        Payment payment = paymentRepository.findByReservationId(paymentId);
        member.getPoint().chargePoint(payment.getAmount());
        CouponHistory couponHistory = couponHistoryRepository.findByCouponIdAndMemberId(couponId, memberId)
                .orElseThrow(() -> new NotFoundCoupon("쿠폰" + couponId));
        couponHistory.couponUsed(false);
        paymentRepository.deleteById(payment.getId());
    }
}