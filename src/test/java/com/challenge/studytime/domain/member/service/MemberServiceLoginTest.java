package com.challenge.studytime.domain.member.service;

import com.challenge.studytime.enums.TestInValidEnum;
import com.challenge.studytime.enums.TestValidEnum;
import com.challenge.studytime.domain.member.dto.request.MemberLoginDto;
import com.challenge.studytime.domain.member.dto.request.MemberSignupDto;
import com.challenge.studytime.domain.member.dto.response.MemberLoginResponseDto;
import com.challenge.studytime.global.exception.member.NotFoundMemberEmail;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class MemberServiceLoginTest {

    @Autowired
    private MemberService memberService;


    @BeforeEach
    void setUp() {
        MemberSignupDto memberSignupDto = MemberSignupDto.builder()
                .email(TestValidEnum.VALID_EMAIL.getMessage())
                .password(TestValidEnum.VALID_PASSWORD.getMessage())
                .name(TestValidEnum.VALID_NAME.getMessage())
                .birthday(TestValidEnum.VALID_BIRTHDAY.getMessage())
                .build();
        memberService.signUpMember(memberSignupDto);
    }


    @Test
    @DisplayName("POST_LOGIN_VALID")
    public void memberLoginWithValid() throws Exception {
        //given
        MemberLoginDto loginDto = MemberLoginDto.builder()
                .email(TestValidEnum.VALID_EMAIL.getMessage())
                .password(TestValidEnum.VALID_PASSWORD.getMessage())
                .build();
        //when
        MemberLoginResponseDto memberLoginResponseDto = memberService.login(loginDto);
        //Then
        Assertions.assertThat(memberLoginResponseDto.getEmail()).isEqualTo(TestValidEnum.VALID_EMAIL.getMessage());
        Assertions.assertThat(memberLoginResponseDto.getName()).isEqualTo(TestValidEnum.VALID_NAME.getMessage());
    }

    @Test
    @DisplayName("POST_LOGIN_INVALID_TEST")
    public void memberLoginWithInValid() {
        //given
        MemberLoginDto loginDto = MemberLoginDto.builder()
                .email(TestInValidEnum.VALID_EMAIL.getMessage())
                .password(TestInValidEnum.VALID_PASSWORD.getMessage())
                .build();
        //Then
        Assertions.assertThatThrownBy(() -> memberService.login(loginDto))
                .isInstanceOf(NotFoundMemberEmail.class)
                .hasMessage("USER NOT Found: test");
    }
}