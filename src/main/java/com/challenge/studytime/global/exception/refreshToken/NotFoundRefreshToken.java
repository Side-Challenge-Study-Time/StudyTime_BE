package com.challenge.studytime.global.exception.refreshToken;

public class NotFoundRefreshToken extends RuntimeException{
    public NotFoundRefreshToken(String refreshToken) {
        super("Not Found Refresh Token Value: " + refreshToken);
    }
}
