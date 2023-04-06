package com.challenge.studytime.domain.member.service;

import com.challenge.studytime.domain.member.repositry.MemberRepositry;
import com.challenge.studytime.domain.refreshToken.repository.RefreshTokenService;
import com.challenge.studytime.domain.role.repositry.RoleRepositry;
import com.challenge.studytime.global.jwt.util.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberSercice {

    private final MemberRepositry memberRepositry;
    private final RoleRepositry roleRepositry;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenService refreshTokenService;

}
