package com.challenge.studytime.domain.coupon.controller;

import com.challenge.studytime.domain.coupon.dto.request.CouponModifyRequestDto;
import com.challenge.studytime.domain.coupon.dto.request.CouponRequestDto;
import com.challenge.studytime.domain.coupon.dto.response.CouponResponseDto;
import com.challenge.studytime.domain.coupon.service.CouponService;
import com.challenge.studytime.global.exception.ErrorResponse;
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

import javax.validation.Valid;
import java.util.List;

@Tag(name = "쿠폰", description = "쿠폰 생성 기능")
@Slf4j
@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @Operation(summary = "초기 쿠폰 생성 기능", description = "초기 쿠폰 생성시 최대 발급 갯수와 할인 값이 설정된다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공", content = @Content(schema = @Schema(implementation = CouponResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "생성 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public CouponResponseDto registerCoupon(
            @Parameter(name = "생성할 쿠폰의 내용", description = "쿠폰의 이름, 할인 값, 최대 발급 갯수, 만료 기한", in = ParameterIn.PATH)
            @RequestBody @Valid CouponRequestDto requestDto){
        return couponService.registerCoupon(requestDto);
    }
    @Operation(summary = "특정 쿠폰 조회 기능", description = "생성된 쿠폰중 쿠폰 이름에 맞는 쿠폰을 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CouponResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public List<CouponResponseDto> detailCoupon(
            @Parameter(name = "생성할 쿠폰의 내용", description = "쿠폰의 이름, 할인 값, 최대 발급 갯수, 만료 기한", in = ParameterIn.PATH)
            @RequestBody @Valid CouponRequestDto requestDto){
        return couponService.detailCoupon(requestDto.getCouponName());
    }
    @Operation(summary = "전체 쿠폰 조회 기능", description = "생성된 쿠폰 전체를 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CouponResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/Search")
    public List<CouponResponseDto> fullSearchCoupon(){
        return couponService.fullSearchCoupon();
    }
    @Operation(summary = "특정 쿠폰 수정 기능", description = "생성된 쿠폰중 하나의 쿠폰을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "수정 성공", content = @Content(schema = @Schema(implementation = CouponResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "수정 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping()
    public String modifyCoupon(
            @Parameter(name = "수정할 쿠폰의 내용", description = "수정 할 쿠폰의 이름, 만료 기한을 몇일만큼 늘릴지", in = ParameterIn.PATH)
            @RequestBody @Valid CouponModifyRequestDto couponModifyRequestDto){
       return couponService.modifyCoupon(couponModifyRequestDto);
    }
    @Operation(summary = "특정 쿠폰을 삭제 기능", description = "생성된 쿠폰중 하나의 쿠폰을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "삭제 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping()
    public void deleteByCoupon(
            @Parameter(name = "삭제할 쿠폰의 내용", description = "삭제 할 쿠폰의 이름", in = ParameterIn.PATH)
            @RequestBody @Valid CouponModifyRequestDto couponModifyRequestDto){
        couponService.deleteByCoupon(couponModifyRequestDto.getCouponName());
    }
}
