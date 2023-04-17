package com.challenge.studytime.domain.payment.controller;

import com.challenge.studytime.domain.payment.dto.requestdto.PaymentRequestDto;
import com.challenge.studytime.domain.payment.dto.responsedto.PaymentResponseDto;
import com.challenge.studytime.domain.payment.service.PaymentService;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{reservationId}/coupon/{couponId}")
    public PaymentResponseDto roomPayment(@IfLogin LoginUserDto userDto,
                                          @PathVariable Long reservationId,
                                          @PathVariable Long couponId,
                                          @RequestBody PaymentRequestDto requestDto){
        return paymentService.roomPayment(userDto.getMemberId(), reservationId,couponId, requestDto);
    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/cancel/{reservationId}")
    void cancelPayment(@IfLogin LoginUserDto userDto,
                       @PathVariable Long reservationId){
        paymentService.cancelPayment(userDto.getMemberId(), reservationId);
    }
}