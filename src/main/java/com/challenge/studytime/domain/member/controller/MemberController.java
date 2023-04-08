package com.challenge.studytime.domain.member.controller;

import com.challenge.studytime.domain.member.dto.request.MemberLoginDto;
import com.challenge.studytime.domain.member.dto.request.MemberSignupDto;
import com.challenge.studytime.domain.member.dto.response.MemberLoginResponseDto;
import com.challenge.studytime.domain.member.dto.response.MemberSignupResponseDto;
import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepositry;
import com.challenge.studytime.domain.member.service.MemberService;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepositry memberRepositry;


    @PostMapping("signUp")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberSignupResponseDto signUpWithMember(
//            @Valid
            @RequestBody
            MemberSignupDto signupDto
    ) {
        log.info("signupDto:{}", signupDto);
        return memberService.signUpMember(signupDto);
    }

    @PostMapping("signUpCustomer")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberSignupResponseDto signUpWithCustomer(
//            @Valid
            @RequestBody
            MemberSignupDto signupDto
    ) {
        log.info("signupDto:{}", signupDto);
        return memberService.signUpCustomer(signupDto);
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public MemberLoginResponseDto login(
//            @Valid
            @RequestBody
            MemberLoginDto loginDto
    ) {
        log.info("loginDto:{}", loginDto);
        return memberService.login(loginDto);
    }

    @GetMapping("/info")
    public ResponseEntity userinfo(@IfLogin LoginUserDto loginUserDto) {
        log.info("==========================================================");
        log.info("loginUserDto:{}" , loginUserDto.getMemberId());
        log.info("loginUserDto:{}" , loginUserDto.getRoles());
        log.info("==========================================================");
        Member member = memberRepositry.findById(loginUserDto.getMemberId())
                .orElseThrow(() -> new UsernameNotFoundException("예외"));
        return new ResponseEntity(member, HttpStatus.OK);
    }
}
