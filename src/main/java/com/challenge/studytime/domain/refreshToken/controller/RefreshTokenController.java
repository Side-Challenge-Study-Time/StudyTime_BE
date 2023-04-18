package com.challenge.studytime.domain.refreshToken.controller;

import com.challenge.studytime.domain.member.dto.response.MemberLoginResponseDto;
import com.challenge.studytime.domain.refreshToken.dto.request.RefreshTokenDto;
import com.challenge.studytime.domain.refreshToken.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @DeleteMapping("logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(
            @RequestBody RefreshTokenDto refreshTokenDto
    ) {
        String refreshToken = refreshTokenDto.getRefreshToken();
        log.info("Refresh_Token:{}", refreshToken);
        refreshTokenService.deleteRefreshToken(refreshToken);
    }

    @PostMapping("refreshToken")
    @ResponseStatus(HttpStatus.OK)
    public MemberLoginResponseDto refreshToken(
            @RequestBody RefreshTokenDto refreshTokenDto
    ) {
        log.info("RefreshToken_Dto:{}",refreshTokenDto);
        return refreshTokenService.findRefreshToken(refreshTokenDto);
    }
}
