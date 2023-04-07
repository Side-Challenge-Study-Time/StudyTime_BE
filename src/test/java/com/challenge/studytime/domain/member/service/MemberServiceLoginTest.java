package com.challenge.studytime.domain.member.service;

import com.challenge.studytime.domain.member.dto.request.MemberLoginDto;
import com.challenge.studytime.domain.member.dto.request.MemberSignupDto;
import com.challenge.studytime.domain.member.dto.response.MemberLoginResponseDto;
import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepositry;
import com.challenge.studytime.domain.refreshToken.service.RefreshTokenService;
import com.challenge.studytime.domain.role.entity.Role;
import com.challenge.studytime.domain.role.enums.RoleEnum;
import com.challenge.studytime.domain.role.repositry.RoleRepository;
import com.challenge.studytime.global.jwt.util.JwtTokenizer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class MemberServiceLoginTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepositry memberRepositry;


    @BeforeEach
    void setUp() {
        MemberSignupDto memberSignupDto = MemberSignupDto.builder()
                .email("test@email.com")
                .password("1234")
                .name("김무건")
                .birthday("1234")
                .build();
        memberService.signUpMember(memberSignupDto);
    }


    @Test
    @DisplayName("POST_LOGIN_VALID")
    public void memberLoginWithValid() throws Exception {
        //given
        MemberLoginDto loginDto = MemberLoginDto.builder()
                .email("test@email.com")
                .password("1234")
                .build();
        //when
        MemberLoginResponseDto memberLoginResponseDto = memberService.login(loginDto);
        //Then
        Assertions.assertThat(memberLoginResponseDto.getEmail()).isEqualTo("test@email.com");
        Assertions.assertThat(memberLoginResponseDto.getName()).isEqualTo("김무건");
    }

}