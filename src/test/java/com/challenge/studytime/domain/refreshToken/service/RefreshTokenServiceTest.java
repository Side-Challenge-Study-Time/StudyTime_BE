package com.challenge.studytime.domain.refreshToken.service;

import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.refreshToken.dto.request.RefreshTokenDto;
import com.challenge.studytime.domain.refreshToken.repository.RefreshTokenRepositry;
import com.challenge.studytime.enums.TestValidEnum;
import com.challenge.studytime.global.exception.member.NotFoundMemberid;
import com.challenge.studytime.global.redis.RedisService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class RefreshTokenServiceTest {

    private static final String REFRESH_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGVtYWlsLmNvbSIsIm1lbWJlcklkIjoxLCJyb2xlcy" +
            "I6WyJVU0VSIl0sImlhdCI6MTY4MTcyNjI0NywiZXhwIjoxNjgyMzMxMDQ3fQ.YCQ1euB_cHdfLOzporKX3JaaqvRsOs7-x5y1QvqyFvs";

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RefreshTokenRepositry refreshTokenRepositry;

    @Autowired
    private RedisService redisService;

    @Test
    @DisplayName("Refresh Token 생성")
    public void addRefreshTokenWithValid() throws Exception {
        //given
        Member member = Member.builder()
                .id(1L)
                .email(TestValidEnum.VALID_EMAIL.getMessage())
                .password(TestValidEnum.VALID_PASSWORD.getMessage())
                .build();
        //when
        refreshTokenService.addRefreshToken(REFRESH_TOKEN, member);
        //Then
        assertThat(refreshTokenRepositry.count()).isNotNull();
    }

    @Test
    @DisplayName("delete Refresh Token Valid")
    public void deleteRefreshTokenWithValidToken() throws Exception {
        //given
        redisService.setValues(REFRESH_TOKEN);

        //when
        refreshTokenService.deleteRefreshToken(REFRESH_TOKEN);

        //Then
        Assertions.assertThat(redisService.getValues(REFRESH_TOKEN)).isNull();
    }

    @Test
    @DisplayName("findRefreshToken With Valid Test")
    public void findRefreshTokenWithValid() throws Exception {
        //given
        RefreshTokenDto refreshTokenDto = RefreshTokenDto.builder()
                .refreshToken(REFRESH_TOKEN)
                .build();

        redisService.setValues(REFRESH_TOKEN);
        //When, Then
        assertThatThrownBy(() -> refreshTokenService.findRefreshToken(refreshTokenDto))
                .isInstanceOf(NotFoundMemberid.class)
                .hasMessageContaining("Not Found Member Id:  1");
    }


    @BeforeEach
    void setUp() {
        RefreshTokenDto refreshTokenDto = RefreshTokenDto.builder()
                .refreshToken(REFRESH_TOKEN)
                .build();

        redisService.setValues(REFRESH_TOKEN);
    }

    @Test
    @DisplayName("재발급 토큰 찾기")
    public void findRefreshToken() throws Exception {
        RefreshTokenDto refreshTokenDto = RefreshTokenDto.builder()
                .refreshToken(REFRESH_TOKEN)
                .build();

        String values = redisService.getValues(REFRESH_TOKEN);
        //When, Then
        assertThat(values).isEqualTo(REFRESH_TOKEN);

    }
}