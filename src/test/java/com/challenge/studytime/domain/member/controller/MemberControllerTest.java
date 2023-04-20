package com.challenge.studytime.domain.member.controller;

import com.challenge.studytime.domain.member.dto.request.MemberLoginDto;
import com.challenge.studytime.domain.member.dto.request.MemberSignupDto;
import com.challenge.studytime.domain.member.dto.response.MemberLoginResponseDto;
import com.challenge.studytime.domain.member.dto.response.MemberSignupResponseDto;
import com.challenge.studytime.domain.member.service.MemberService;
import com.challenge.studytime.enums.TestValidEnum;
import com.challenge.studytime.global.config.SecurityConfig;
import com.challenge.studytime.global.exception.member.UserEmailDuplicationException;
import com.challenge.studytime.global.jwt.util.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;


    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MemberSignupDto member = MemberSignupDto.builder()
                .email(TestValidEnum.VALID_EMAIL.getMessage())
                .password(TestValidEnum.VALID_PASSWORD.getMessage())
                .name(TestValidEnum.VALID_NAME.getMessage())
                .birthday(TestValidEnum.VALID_BIRTHDAY.getMessage())
                .build();

        given(memberService.signUpMember(any(MemberSignupDto.class))).willReturn(MemberSignupResponseDto.builder()
                .memberId(1L)
                .email(TestValidEnum.VALID_EMAIL.getMessage())
                .name(TestValidEnum.VALID_NAME.getMessage())
                .birthday(TestValidEnum.VALID_BIRTHDAY.getMessage())
                .build());

        given(memberService.signUpCustomer(any(MemberSignupDto.class))).willReturn(MemberSignupResponseDto.builder()
                .memberId(1L)
                .email(TestValidEnum.VALID_EMAIL.getMessage())
                .name(TestValidEnum.VALID_NAME.getMessage())
                .birthday(TestValidEnum.VALID_BIRTHDAY.getMessage())
                .build());
        given(memberService.login(any(MemberLoginDto.class))).willReturn(MemberLoginResponseDto.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .email("valid@email.com")
                .build());


    }


    @Test
    @DisplayName("회원가입 유효성 검사 성공")
    void signUpWithValidAttributes() throws Exception {
        // when
        mockMvc.perform(
                        post("/api/members/signup")
                                .contentType(APPLICATION_JSON)
                                .content("{\"email\":\"test1234@email.com\",\"password\":\"Aa1!bbccddeeff\"," +
                                        "\"name\":\"김무건\",\"birthday\":\"2020-01-01\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(TestValidEnum.VALID_EMAIL.getMessage()))
                .andExpect(jsonPath("$.name").value(TestValidEnum.VALID_NAME.getMessage()))
                .andExpect(jsonPath("$.birthday").value(TestValidEnum.VALID_BIRTHDAY.getMessage()))
                .andDo(print());
        //then
        verify(memberService).signUpMember(any(MemberSignupDto.class));
    }

    @Test
    @DisplayName("회원가입 유효성 검사 실패_비밀번호 패턴")
    void signUpWithInValidAttributes() throws Exception {
    // when
    mockMvc.perform(
                    post("/api/members/signup")
                    .contentType(APPLICATION_JSON)
                    .content("{\"email\":\"test12@email.com\",\"password\":\"1234\"," +
                            "\"name\":\"김무건\",\"birthday\":\"2020-01-01\"}")
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("MethodArgumentNotValidException"))
            .andDo(print());
    }

    @Test
    @DisplayName("판매자 회원가입 성공")
    void customerSignUpWithValidAttributes() throws Exception {
        // when
        mockMvc.perform(
                        post("/api/customers/signup")
                                .contentType(APPLICATION_JSON)
                                .content("{\"email\":\"test1234@email.com\",\"password\":\"Aa1!bbccddeeff\"," +
                                        "\"name\":\"김무건\",\"birthday\":\"2020-01-01\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(TestValidEnum.VALID_EMAIL.getMessage()))
                .andExpect(jsonPath("$.name").value(TestValidEnum.VALID_NAME.getMessage()))
                .andExpect(jsonPath("$.birthday").value(TestValidEnum.VALID_BIRTHDAY.getMessage()))
                .andDo(print());
        //then
        verify(memberService).signUpMember(any(MemberSignupDto.class));
    }

    @Test
    @DisplayName("회원가입 실패_Empty Attributes")
    void customerSignUpWithInValidEmptyAttributes() throws Exception {
        mockMvc.perform(post("/api/members/signup")
                        .contentType(APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("판매자 회원가입 성공")
    void customerSignUpW() throws Exception {
        // when
        mockMvc.perform(
                        post("/api/customers/signup")
                                .contentType(APPLICATION_JSON)
                                .content("{\"email\":\"test@email.com\",\"password\":\"Aa1!bbccddeeff\"," +
                                        "\"name\":\"김무건\",\"birthday\":\"2020-01-01\"}")
                )
                .andExpect(status().isCreated())
                .andDo(print());
        //then
        verify(memberService).signUpCustomer(any(MemberSignupDto.class));
    }

    @DisplayName("판매자 회원가입 실패_json empty")
    @Test
    void signUpCustomerWith_InValidEmptyJson() throws Exception {
        mockMvc.perform(post("/api/customers/signup")
                        .contentType(APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인")
    void loginWith_ValidDto() throws Exception {
        // when
        mockMvc.perform(
                        post("/api/members/login")
                                .contentType(APPLICATION_JSON)
                                .content("{\"email\":\"test@email.com\",\"password\":\"Aa1!bbccddeeff\"}")
                )
                .andExpect(status().isCreated())
                .andDo(print());
        //then
        verify(memberService).login(any(MemberLoginDto.class));
    }


}