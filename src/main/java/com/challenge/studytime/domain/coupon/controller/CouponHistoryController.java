package com.challenge.studytime.domain.coupon.controller;

import com.challenge.studytime.domain.coupon.dto.response.CouponHistoryResponseDto;
import com.challenge.studytime.domain.coupon.dto.response.CouponResponseDto;
import com.challenge.studytime.domain.coupon.service.CouponHistoryService;
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

import java.util.List;


@Tag(name = "쿠폰 발급", description = "생성된 쿠폰을 발급해주는 기능")
@Slf4j
@RestController
@RequestMapping("/api/coupon/history")
@RequiredArgsConstructor
public class CouponHistoryController {
    private final CouponHistoryService couponHistoryService;
    @Operation(summary = "유저 쿠폰 발급 기능", description = "어떤 쿠폰을 발급 받을지 정한 후 발급 받는다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "발급 성공", content = @Content(schema = @Schema(implementation = CouponHistoryResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "발급 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{couponId}")
    public CouponHistoryResponseDto registerCouponHistory(
            @Parameter(name = "발급 받을 쿠폰 아이디", description = "특정 쿠폰 아이디를 통해 접근 가능하다.", in = ParameterIn.PATH)
            @PathVariable("couponId") Long couponId,
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto) {
        CouponHistoryResponseDto couponHistoryResponseDto = couponHistoryService.registerCouponHistory(couponId, userDto);
        return couponHistoryResponseDto;
    }
    @Operation(summary = "사용자가 가진 쿠폰 조회 기능", description = "사용자가 가진 쿠폰을 모두 조회할 수 있다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "생성 성공", content = @Content(schema = @Schema(implementation = CouponHistoryResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "생성 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public List<CouponHistoryResponseDto> SearchCoupon(
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto){
        return couponHistoryService.detailUserCoupon(userDto);
    }
    @Operation(summary = "쿠폰 사용 기능", description = "쿠폰을 사용할 수 있다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "사용 성공"),
            @ApiResponse(responseCode = "400", description = "사용 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("/{couponId}/use")
    public void useCoupon(
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto,
            @Parameter(name = "발급 받을 쿠폰 아이디", description = "특정 쿠폰 아이디를 통해 접근 가능하다.", in = ParameterIn.PATH)
            @PathVariable("couponId") Long couponId){
        couponHistoryService.useCoupon(userDto.getMemberId(), couponId);
    }
    @Operation(summary = "쿠폰 취소 기능", description = "사용된 쿠폰의 취소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "취소 성공"),
            @ApiResponse(responseCode = "400", description = "취소 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("/{couponId}/cancel")
    public void cancelCouponUsage(
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto,
            @Parameter(name = "발급 받을 쿠폰 아이디", description = "특정 쿠폰 아이디를 통해 접근 가능하다.", in = ParameterIn.PATH)
            @PathVariable("couponId") Long couponId){
        couponHistoryService.cancelCouponUsage(userDto.getMemberId(), couponId);
    }
}
