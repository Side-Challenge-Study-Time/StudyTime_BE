package com.challenge.studytime.domain.member.controller;

import com.challenge.studytime.domain.member.dto.request.MemberLoginDto;
import com.challenge.studytime.domain.member.dto.request.MemberSignupDto;
import com.challenge.studytime.domain.member.dto.response.MemberLoginResponseDto;
import com.challenge.studytime.domain.member.dto.response.MemberSignupResponseDto;
import com.challenge.studytime.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/members/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberSignupResponseDto signUpWithMember(
            @Valid
            @RequestBody MemberSignupDto signupDto
    ) {
        log.info("signupDto:{}", signupDto);
        return memberService.signUpMember(signupDto);
    }

    @PostMapping("/customers/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberSignupResponseDto signUpWithCustomer(
            @Valid
            @RequestBody MemberSignupDto signupDto
    ) {
        log.info("signupDto:{}", signupDto);
        return memberService.signUpCustomer(signupDto);
    }

    @PostMapping("/members/login")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberLoginResponseDto login(
            @Valid
            @RequestBody MemberLoginDto loginDto
    ) {
        log.info("loginDto:{}", loginDto);
        return memberService.login(loginDto);
    }
}
