package com.challenge.studytime.domain.refreshToken.service;

import com.challenge.studytime.domain.member.dto.response.MemberLoginResponseDto;
import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepository;
import com.challenge.studytime.domain.refreshToken.dto.request.RefreshTokenDto;
import com.challenge.studytime.domain.refreshToken.entity.RefreshToken;
import com.challenge.studytime.domain.refreshToken.repository.RefreshTokenRepositry;
import com.challenge.studytime.global.exception.member.NotFoundMemberEmail;
import com.challenge.studytime.global.exception.member.NotFoundMemberid;
import com.challenge.studytime.global.jwt.util.JwtTokenizer;
import com.challenge.studytime.global.redis.RedisService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final JwtTokenizer jwtTokenizer;
    private final MemberRepository memberRepository;
    private final RedisService redisService;

    @Transactional
    public void addRefreshToken(String refreshToken, Member member) {

        RefreshToken token = RefreshToken.builder()
                .value(refreshToken)
                .memberId(member.getId())
                .build();

        redisService.setValues(refreshToken);
    }

    @Transactional
    public void deleteRefreshToken(String refreshToken) {
        redisService.delValues(refreshToken);
    }

    @Transactional(readOnly = true)
    public MemberLoginResponseDto findRefreshToken(RefreshTokenDto refreshTokenDto) {

        String refreshToken = redisService.getValues(refreshTokenDto.getRefreshToken());

        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken);
        Long userId = Long.valueOf((Integer) claims.get("memberId"));

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new NotFoundMemberid(userId));

        List roles = (List) claims.get("roles");
        String email = claims.getSubject();
        String accessToken = jwtTokenizer.createAccessToken(userId, email, roles);

        return MemberLoginResponseDto.toDto(member, accessToken, refreshTokenDto.getRefreshToken());
    }
}
