package com.challenge.studytime.domain.payment.controller;

import com.challenge.studytime.domain.payment.dto.requestdto.PaymentRequestDto;
import com.challenge.studytime.domain.payment.dto.responsedto.PaymentResponseDto;
import com.challenge.studytime.domain.payment.service.PaymentService;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("reservation/{reservationId}")
    public PaymentResponseDto roomPayment(@IfLogin LoginUserDto userDto,
                                          @PathVariable Long reservationId,
                                          @RequestParam(required = false) Long couponId,
                                          @RequestBody PaymentRequestDto requestDto){
        Optional<Long> optionalCouponId = Optional.ofNullable(couponId);

        if(optionalCouponId.isPresent()) {
            return paymentService.roomPayment(userDto.getMemberId(), reservationId, optionalCouponId.get(), requestDto);
        }
        else {
            return paymentService.roomPayment(userDto.getMemberId(), reservationId, null, requestDto);
        }
    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{paymentId}")
    public void cancelPayment(@IfLogin LoginUserDto userDto,
                       @PathVariable Long paymentId,
                       @RequestParam(required = false) Long couponId){
        paymentService.cancelPayment(userDto.getMemberId(), paymentId, couponId);
    }
}