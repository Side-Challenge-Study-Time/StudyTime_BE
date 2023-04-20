package com.challenge.studytime.domain.refreshToken.controller;

import com.challenge.studytime.domain.member.dto.response.MemberLoginResponseDto;
import com.challenge.studytime.domain.member.dto.response.MemberSignupResponseDto;
import com.challenge.studytime.domain.refreshToken.dto.request.RefreshTokenDto;
import com.challenge.studytime.domain.refreshToken.service.RefreshTokenService;
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

@Tag(name = "Refresh Token을 이용한 로그아웃, 재발급", description = "로그아웃, 재발급")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "로그아웃", description = "로그아웃을 통한 Refresh Token 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "400", description = "로그아웃 실패")
    })
    @DeleteMapping("logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(
            @Parameter(name = "Refresh Token Dto",description = "refreshToken", in = ParameterIn.PATH)
            @RequestBody RefreshTokenDto refreshTokenDto
    ) {
        String refreshToken = refreshTokenDto.getRefreshToken();
        log.info("Refresh_Token:{}", refreshToken);
        refreshTokenService.deleteRefreshToken(refreshToken);
    }

    @Operation(summary = "AccessToken 재발급", description = "Access Token 만료가 되면 Refresh Token을 확인하여 Access Token 재발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재발급 성공",content = @Content(schema = @Schema(implementation = MemberLoginResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "재발급 실패")
    })
    @PostMapping("refreshToken")
    @ResponseStatus(HttpStatus.OK)
    public MemberLoginResponseDto refreshToken(
            @Parameter(name = "Refresh Token Dto",description = "refreshToken", in = ParameterIn.PATH)
            @RequestBody RefreshTokenDto refreshTokenDto
    ) {
        log.info("RefreshToken_Dto:{}",refreshTokenDto);
        return refreshTokenService.findRefreshToken(refreshTokenDto);
    }
}
