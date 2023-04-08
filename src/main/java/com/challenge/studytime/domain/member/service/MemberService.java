package com.challenge.studytime.domain.member.service;

import com.challenge.studytime.domain.member.dto.request.MemberLoginDto;
import com.challenge.studytime.domain.member.dto.request.MemberSignupDto;
import com.challenge.studytime.domain.member.dto.response.MemberLoginResponseDto;
import com.challenge.studytime.domain.member.dto.response.MemberSignupResponseDto;
import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepositry;
import com.challenge.studytime.domain.refreshToken.service.RefreshTokenService;
import com.challenge.studytime.domain.role.entity.Role;
import com.challenge.studytime.domain.role.enums.RoleEnum;
import com.challenge.studytime.domain.role.repositry.RoleRepository;
import com.challenge.studytime.global.exception.member.NotFoundMemberEmail;
import com.challenge.studytime.global.exception.member.NotMatchPassword;
import com.challenge.studytime.global.exception.member.UserEmailDuplicationException;
import com.challenge.studytime.global.jwt.util.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepositry memberRepositry;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenService refreshTokenService;


    @Transactional
    public MemberSignupResponseDto signUpMember(MemberSignupDto loginDto) {

        String memberEmail = loginDto.getEmail();
        if (memberRepositry.existsByEmail(memberEmail)) {
            throw new UserEmailDuplicationException(memberEmail);
        }

        Member member = Member.builder()
                .email(loginDto.getEmail())
                .name(loginDto.getName())
                .birthday(loginDto.getBirthday())
                .password(passwordEncoder.encode(loginDto.getPassword()))
                .build();

        Optional<Role> userRole = roleRepository.findByName(RoleEnum.ROLE_USER.getRoleName());
        userRole.ifPresent(member::addRole);

        return MemberSignupResponseDto.toDto(memberRepositry.save(member));
    }


    @Transactional
    public MemberSignupResponseDto signUpCustomer(MemberSignupDto signupDto) {

        String memberEmail = signupDto.getEmail();

        if (memberRepositry.existsByEmail(memberEmail)) {
            throw new UserEmailDuplicationException(memberEmail);
        }

        Member member = Member.builder()
                .email(signupDto.getEmail())
                .name(signupDto.getName())
                .birthday(signupDto.getBirthday())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .build();


        Optional<Role> customRole = roleRepository.findByName(RoleEnum.ROLE_STUDY_LEADER.getRoleName());
        customRole.ifPresent(member::addRole);

        return MemberSignupResponseDto.toDto(memberRepositry.save(member));
    }

    @Transactional
    public MemberLoginResponseDto login(MemberLoginDto loginDto) {
        Member member = memberRepositry.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new NotFoundMemberEmail(loginDto.getEmail()));


        List<String> roles = member.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        String accessToken = jwtTokenizer.createAccessToken(
                member.getId(),
                member.getEmail(),
                roles
        );

        String refreshToken = jwtTokenizer.createRefreshToken(
                member.getId(),
                member.getEmail(),
                roles
        );

        refreshTokenService.addRefreshToken(refreshToken, member);

        return MemberLoginResponseDto.toDto(member, accessToken, refreshToken);
    }
}
