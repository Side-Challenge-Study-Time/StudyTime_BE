package com.challenge.studytime.global.jwt.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Jwt Access Token, Refresh Token")
class JwtTokenizerTest {

    private JwtTokenizer jwtTokenizer;

    private static final String SECRET = "12345678901234567890123456789012";
    private static final String REFRESH = "12345678901234567890123456789012";


    @BeforeEach
    void setUp(){
        jwtTokenizer = new JwtTokenizer(SECRET, REFRESH);
    }

    @Test
    @DisplayName("createToken")
    public void createTokenWithValid() throws Exception{
        String accessToken = jwtTokenizer.createAccessToken(1L, "test@email.com", List.of("USER"));
        System.out.println("accessToken = " + accessToken);
        assertThat(accessToken).isNotBlank();
    }

    @Test
    @DisplayName("createRefreshToken")
    public void createRefreshTokenWithValidToken() throws Exception{
        String refreshToken = jwtTokenizer.createRefreshToken(1L, "test@email.com", List.of("USER"));
        System.out.println("refreshToken = " + refreshToken);
        assertThat(refreshToken).isNotBlank();
    }

}