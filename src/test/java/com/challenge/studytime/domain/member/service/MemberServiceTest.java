package com.challenge.studytime.domain.member.service;

import com.challenge.studytime.domain.member.dto.request.MemberSignupDto;
import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepositry;
import com.challenge.studytime.domain.refreshToken.service.RefreshTokenService;
import com.challenge.studytime.domain.role.entity.Role;
import com.challenge.studytime.domain.role.enums.RoleEnum;
import com.challenge.studytime.domain.role.repositry.RoleRepository;
import com.challenge.studytime.global.jwt.util.JwtTokenizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class MemberServiceTest {

    private MemberService memberService;
    private JwtTokenizer jwtTokenizer;

    private MemberRepositry memberRepositry = mock(MemberRepositry.class);
    private RoleRepository roleRepository = mock(RoleRepository.class);


    private RefreshTokenService refreshTokenService= mock(RefreshTokenService.class);


    private static final String SECRET = "12345678901234567890123456789012";
    private static final String REFRESH = "12345678901234567890123456789012";
    private static final String VALID_EMAIL = "test@email.com";
    private static final String VALID_PASSWORD = "1234";
    private static final String VALID_BIRTHDAY = "1997-12-03";
    private static final String VALID_NAME = "김무건";

    @BeforeEach
    void setUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        jwtTokenizer = new JwtTokenizer(SECRET, REFRESH);

        memberService = new MemberService(memberRepositry, roleRepository, passwordEncoder, jwtTokenizer, refreshTokenService);

        Member member = Member.builder()
                .email(VALID_EMAIL)
                .name(VALID_NAME)
                .birthday(VALID_BIRTHDAY)
                .password(VALID_PASSWORD) // 예상되는 암호화된 비밀번호
                .build();


        Role userRole = Role.builder()
                .name(RoleEnum.ROLE_USER.getRoleName())
                .build();

        Role customerRole = Role.builder()
                .name(RoleEnum.ROLE_CUSTOMER.getRoleName())
                .build();

        given(memberRepositry.existsByEmail(VALID_EMAIL)).willReturn(false);
        when(roleRepository.findByName(RoleEnum.ROLE_USER.getRoleName())).thenReturn(Optional.of(userRole));
        when(roleRepository.findByName(RoleEnum.ROLE_CUSTOMER.getRoleName())).thenReturn(Optional.of(userRole));
        when(memberRepositry.save(any(Member.class))).thenReturn(member);
    }

    @Test
    @DisplayName("POST-/api/member/signUpMember")
    public void memberSignUpMemberWithValid() throws Exception {
        //given
        MemberSignupDto memberSignupDto = MemberSignupDto.builder()
                .email(VALID_EMAIL)
                .name(VALID_NAME)
                .password(VALID_PASSWORD)
                .birthday(VALID_PASSWORD)
                .build();
        //when
        memberService.signUpMember(memberSignupDto);
        //Then
        verify(memberRepositry).save(any(Member.class));
    }

    @Test
    @DisplayName("POST-/api/member/signUpCustomer")
    public void memberSignUpCustomerWithValid() throws Exception {
        //given
        MemberSignupDto memberSignupDto = MemberSignupDto.builder()
                .email(VALID_EMAIL)
                .name(VALID_NAME)
                .password(VALID_PASSWORD)
                .birthday(VALID_PASSWORD)
                .build();
        //when
        memberService.signUpMember(memberSignupDto);
        //Then
        verify(memberRepositry).save(any(Member.class));
    }
}