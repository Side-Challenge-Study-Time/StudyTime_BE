package com.challenge.studytime.domain.member.service;

import com.challenge.studytime.domain.member.dto.request.MemberSignupDto;
import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepository;
import com.challenge.studytime.domain.refreshToken.service.RefreshTokenService;
import com.challenge.studytime.domain.role.entity.Role;
import com.challenge.studytime.domain.role.enums.RoleEnum;
import com.challenge.studytime.domain.role.repositry.RoleRepository;
import com.challenge.studytime.enums.TestValidEnum;
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

    private final MemberRepository MemberRepository = mock(MemberRepository.class);
    private final RoleRepository roleRepository = mock(RoleRepository.class);


    private final RefreshTokenService refreshTokenService= mock(RefreshTokenService.class);


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
                .password(TestValidEnum.VALID_PASSWORD.getMessage()) // 예상되는 암호화된 비밀번호
                .build();


        Role userRole = Role.builder()
                .name(RoleEnum.ROLE_USER.getRoleName())
                .build();

        Role customerRole = Role.builder()
                .name(RoleEnum.ROLE_CUSTOMER.getRoleName())
                .build();

        when(MemberRepository.existsByEmail(TestValidEnum.VALID_EMAIL.getMessage())).thenReturn(false);
        when(roleRepository.findByName(RoleEnum.ROLE_USER.getRoleName())).thenReturn(Optional.of(userRole));
        when(roleRepository.findByName(RoleEnum.ROLE_CUSTOMER.getRoleName())).thenReturn(Optional.of(userRole));
        when(MemberRepository.save(any(Member.class))).thenReturn(member);
    }

    @Test
    @DisplayName("POST-/api/member/signUpMember")
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
    @DisplayName("POST-/api/member/signUpCustomer")
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
}