package com.challenge.studytime.domain.member.service;

import com.challenge.studytime.domain.member.dto.request.MemberLoginDto;
import com.challenge.studytime.domain.member.dto.request.MemberSignupDto;
import com.challenge.studytime.domain.member.dto.response.MemberLoginResponseDto;
import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepository;
import com.challenge.studytime.domain.refreshToken.service.RefreshTokenService;
import com.challenge.studytime.domain.role.entity.Role;
import com.challenge.studytime.domain.role.enums.RoleEnum;
import com.challenge.studytime.domain.role.repositry.RoleRepository;
import com.challenge.studytime.enums.TestValidEnum;
import com.challenge.studytime.global.exception.member.NotFoundMemberEmail;
import com.challenge.studytime.global.exception.member.UserEmailDuplicationException;
import com.challenge.studytime.global.jwt.util.JwtTokenizer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class MemberServiceTest {

    private MemberService memberService;
    private JwtTokenizer jwtTokenizer;

    private final MemberRepository MemberRepository = mock(MemberRepository.class);
    private final RoleRepository roleRepository = mock(RoleRepository.class);


    private final RefreshTokenService refreshTokenService = mock(RefreshTokenService.class);


    private static final String SECRET = "12345678901234567890123456789012";
    private static final String REFRESH = "12345678901234567890123456789012";

    @BeforeEach
    void setUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        jwtTokenizer = new JwtTokenizer(SECRET, REFRESH);

        memberService = new MemberService(MemberRepository, roleRepository, passwordEncoder, jwtTokenizer, refreshTokenService);

        Member member = Member.builder()
                .email(TestValidEnum.VALID_EMAIL.getMessage())
                .name(TestValidEnum.VALID_NAME.getMessage())
                .birthday(TestValidEnum.VALID_BIRTHDAY.getMessage())
                .password(TestValidEnum.VALID_PASSWORD.getMessage())
                .build();


        Role userRole = Role.builder()
                .name(RoleEnum.ROLE_USER.getRoleName())
                .build();

        Role customerRole = Role.builder()
                .name(RoleEnum.ROLE_CUSTOMER.getRoleName())
                .build();


        given(MemberRepository.existsByEmail(TestValidEnum.VALID_EMAIL.getMessage())).willReturn(false);
        given(roleRepository.findByName(RoleEnum.ROLE_USER.getRoleName())).willReturn(Optional.of(userRole));
        given(roleRepository.findByName(RoleEnum.ROLE_CUSTOMER.getRoleName())).willReturn(Optional.of(userRole));
        given(MemberRepository.save(any(Member.class))).willReturn(member);
        given(MemberRepository.existsByEmail(eq("duplication@email.com"))).willThrow(UserEmailDuplicationException.class);

    }

    @Test
    @DisplayName("/api/member/signUp (POST) 회원가입 성공적인 테스트")
    public void memberSignUpMemberWithValid() throws Exception {
        //given
        MemberSignupDto memberSignupDto = MemberSignupDto.builder()
                .email(TestValidEnum.VALID_EMAIL.getMessage())
                .name(TestValidEnum.VALID_NAME.getMessage())
                .password(TestValidEnum.VALID_PASSWORD.getMessage())
                .birthday(TestValidEnum.VALID_BIRTHDAY.getMessage())
                .build();
        //when
        memberService.signUpMember(memberSignupDto);
        //Then
        verify(MemberRepository).save(any(Member.class));
    }


    @Test
    @DisplayName("중복된 회원 가입에 대한 에서")
    public void memberSignUpInValidWithDuplicationEamil() {
        //given
        MemberSignupDto memberSignupDto = MemberSignupDto.builder()
                .email("duplication@email.com")
                .name(TestValidEnum.VALID_NAME.getMessage())
                .password(TestValidEnum.VALID_PASSWORD.getMessage())
                .birthday(TestValidEnum.VALID_BIRTHDAY.getMessage())
                .build();
        //Then
        Assertions.assertThatThrownBy(() -> memberService.signUpMember(memberSignupDto))
                .isInstanceOf(UserEmailDuplicationException.class);
    }

    @Test
    @DisplayName("/api/member/signUpCustomer (POST) 판매자 권한을 가진 회원가입 테스트")
    public void memberSignUpCustomerWithValid() throws Exception {
        //given
        MemberSignupDto memberSignupDto = MemberSignupDto.builder()
                .email(TestValidEnum.VALID_EMAIL.getMessage())
                .name(TestValidEnum.VALID_NAME.getMessage())
                .password(TestValidEnum.VALID_PASSWORD.getMessage())
                .birthday(TestValidEnum.VALID_BIRTHDAY.getMessage())
                .build();
        //when
        memberService.signUpMember(memberSignupDto);
        //Then
        verify(MemberRepository).save(any(Member.class));
    }

    @Test
    @DisplayName("판매자 회원가입 이메일 중복 테스트")
    public void customerSignUpInValidWithDuplication() {
        //given
        MemberSignupDto memberSignupDto = MemberSignupDto.builder()
                .email("duplication@email.com")
                .name(TestValidEnum.VALID_NAME.getMessage())
                .password(TestValidEnum.VALID_PASSWORD.getMessage())
                .birthday(TestValidEnum.VALID_BIRTHDAY.getMessage())
                .build();
        //Then
        Assertions.assertThatThrownBy(() -> memberService.signUpCustomer(memberSignupDto))
                .isInstanceOf(UserEmailDuplicationException.class);
    }

    @Test
    @DisplayName("/api/member/login (POST) 로그인 성공적인 테스트")
    void memberLoginValid() {
        // given
        MemberLoginDto memberLoginDto = MemberLoginDto.builder()
                .email("test@example.com")
                .password("testpassword")
                .build();

        Member member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .name("Test User")
                .password("$2a$10$wm/lYKwvEV7nMnTzT1/XmO1FrK2htXsMSfECnZV7kI1Y2QG0BqsHW")
                .build();

        given(MemberRepository.findByEmail(memberLoginDto.getEmail())).willReturn(Optional.of(member));

        // when
        MemberLoginResponseDto responseDto = memberService.login(memberLoginDto);

        // then
        verify(refreshTokenService).addRefreshToken(anyString(), eq(member));

        Assertions.assertThat(responseDto.getAccessToken()).isNotNull();
        Assertions.assertThat(responseDto.getRefreshToken()).isNotNull();
        Assertions.assertThat(responseDto.getEmail()).isEqualTo(member.getEmail());
        Assertions.assertThat(responseDto.getName()).isEqualTo(member.getName());
    }

    @Test
    @DisplayName("/api/member/login (POST) 로그인 실패 테스트")
    void memberLoginInValidWithNotfound() {
        // given
        MemberLoginDto memberLoginDto = MemberLoginDto.builder()
                .email("test@example.com")
                .password("testpassword")
                .build();

        Member member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .name("Test User")
                .password("$2a$10$wm/lYKwvEV7nMnTzT1/XmO1FrK2htXsMSfECnZV7kI1Y2QG0BqsHW")
                .build();

        given(MemberRepository.findByEmail(memberLoginDto.getEmail())).willThrow(NotFoundMemberEmail.class);

        // when
        Assertions.assertThatThrownBy(() -> memberService.login(memberLoginDto))
                .isInstanceOf(NotFoundMemberEmail.class);
    }
}