package com.challenge.studytime.domain.point.controller;

import com.challenge.studytime.domain.coupon.dto.response.CouponHistoryResponseDto;
import com.challenge.studytime.domain.point.dto.requestdto.PointRequestDto;
import com.challenge.studytime.domain.point.dto.responsedto.PointResponseDto;
import com.challenge.studytime.domain.point.service.PointService;
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

@Tag(name = "포인트", description = "포인트를 충전 사용하게 해주는 기능")
@Slf4j
@RestController
@RequestMapping("/api/point")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @Operation(summary = "포인트 생성 기능", description = "초기 포인트를 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공", content = @Content(schema = @Schema(implementation = PointResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "생성 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public PointResponseDto registerPoint(
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto,
            @Parameter(name = "생성할 포인트의 내용", description = "생성할 포인트의 양", in = ParameterIn.PATH)
            @RequestBody PointRequestDto requestDto){
        return pointService.registerPoint(userDto.getMemberId(), requestDto);
    }
    @Operation(summary = "포인트 충전 기능", description = "포인트를 충전한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "충전 성공"),
            @ApiResponse(responseCode = "400", description = "충전 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("/charge")
    public void chargePoint(
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto,
            @Parameter(name = "충전할 포인트의 내용", description = "충전할 포인트의 양", in = ParameterIn.PATH)
            @RequestBody PointRequestDto requestDto){
        pointService.chargePoint(userDto.getMemberId(), requestDto);
    }
}