package com.challenge.studytime.domain.member.controller;

import com.challenge.studytime.domain.member.dto.request.MemberLoginDto;
import com.challenge.studytime.domain.member.dto.request.MemberSignupDto;
import com.challenge.studytime.domain.member.dto.response.MemberLoginResponseDto;
import com.challenge.studytime.domain.member.dto.response.MemberSignupResponseDto;
import com.challenge.studytime.domain.member.service.MemberService;
import com.challenge.studytime.global.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "회원", description = "권한 회원가입, 로그인")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "유저 회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공", content = @Content(schema = @Schema(implementation = MemberSignupResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "생성 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/members/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberSignupResponseDto signUpWithMember(
            @Parameter(name = "회원가입 DTO",description = "이메일,비밀번호,이름,생일", in = ParameterIn.PATH)
            @Valid
            @RequestBody MemberSignupDto signupDto
    ) {
        log.info("signupDto:{}", signupDto);
        return memberService.signUpMember(signupDto);
    }
    @Operation(summary = "판매자 회원가입", description = "판매자 회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공", content = @Content(schema = @Schema(implementation = MemberSignupResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "생성 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/customers/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberSignupResponseDto signUpWithCustomer(
            @Parameter(name = "회원가입 DTO",description = "이메일,비밀번호,이름,생일", in = ParameterIn.PATH)
            @Valid
            @RequestBody MemberSignupDto signupDto
    ) {
        log.info("signupDto:{}", signupDto);
        return memberService.signUpCustomer(signupDto);
    }
    @Operation(summary = "회원 로그인", description = "로그인을 통해 JWT 인증, 재발급 토큰")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "로그인 성공", content = @Content(schema = @Schema(implementation = MemberLoginResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "로그인 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/members/login")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberLoginResponseDto login(
            @Parameter(name = "로그인 DTO",description = "이메일, 비밀번호", in = ParameterIn.PATH)
            @Valid
            @RequestBody MemberLoginDto loginDto
    ) {
        log.info("loginDto:{}", loginDto);
        return memberService.login(loginDto);
    }
}
