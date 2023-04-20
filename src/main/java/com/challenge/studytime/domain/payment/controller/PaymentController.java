package com.challenge.studytime.domain.payment.controller;

import com.challenge.studytime.domain.coupon.dto.response.CouponResponseDto;
import com.challenge.studytime.domain.payment.dto.requestdto.PaymentRequestDto;
import com.challenge.studytime.domain.payment.dto.responsedto.PaymentResponseDto;
import com.challenge.studytime.domain.payment.service.PaymentService;
import com.challenge.studytime.global.exception.ErrorResponse;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "결제", description = "예약을 결제해주는 기능")
@Slf4j
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "결제 기능", description = "예약 내역을 통해서 결제를 할 수 있다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공", content = @Content(schema = @Schema(implementation = PaymentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "생성 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("reservation/{reservationId}")
    public PaymentResponseDto roomPayment(
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto,
            @Parameter(name = "결제 할 예약 아이디", description = "특정 예약 아이디를 통해 접근 가능하다.", in = ParameterIn.PATH)
            @PathVariable Long reservationId,
            @Parameter(name = "결제 시 사용할 쿠폰 아이디", description = "특정 쿠폰 아이디를 통해 접근 가능하다.", in = ParameterIn.PATH)
            @RequestParam(required = false) Long couponId,
            @Parameter(name = "생성할 결제의 내용", description = "결제 방법", in = ParameterIn.PATH)
            @RequestBody PaymentRequestDto requestDto){
        Optional<Long> optionalCouponId = Optional.ofNullable(couponId);

        if(optionalCouponId.isPresent()) {
            return paymentService.roomPayment(userDto.getMemberId(), reservationId, optionalCouponId.get(), requestDto);
        }
        else {
            return paymentService.roomPayment(userDto.getMemberId(), reservationId, null, requestDto);
        }
    }
    @Operation(summary = "결제 취소 기능", description = "결제 내역을 통해서 결제를 취소 할 수 있다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "취소 성공"),
            @ApiResponse(responseCode = "400", description = "취소 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{paymentId}")
    public void cancelPayment(
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto,
            @Parameter(name = "결제 내역이 담긴 아이디", description = "특정 결제 아이디를 통해 접근 가능하다.", in = ParameterIn.PATH)
            @PathVariable Long paymentId,
            @Parameter(name = "결제 시 사용한 쿠폰 아이디", description = "특정 쿠폰 아이디를 통해 접근 가능하다.", in = ParameterIn.PATH)
            @RequestParam(required = false) Long couponId){
        paymentService.cancelPayment(userDto.getMemberId(), paymentId, couponId);
    }
}