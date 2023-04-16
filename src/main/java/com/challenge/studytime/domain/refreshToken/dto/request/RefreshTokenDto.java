package com.challenge.studytime.domain.refreshToken.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RefreshTokenDto {
    @NotEmpty(message = "refresh token을 입력하세요.")
    String refreshToken;
}
