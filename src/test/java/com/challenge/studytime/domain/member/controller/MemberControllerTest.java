package com.challenge.studytime.domain.member.controller;

import com.challenge.studytime.domain.member.dto.request.MemberSignupDto;
import com.challenge.studytime.domain.member.dto.response.MemberSignupResponseDto;
import com.challenge.studytime.domain.member.service.MemberService;
import com.challenge.studytime.global.config.SecurityConfig;
import com.challenge.studytime.global.jwt.util.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(value = MemberControllerTest.class)
@MockBean(JpaMetamodelMappingContext.class)
class MemberControllerTest {

    private static final String EMAIL = "john.doe@example.com";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    @WithMockUser
    @DisplayName("signUpWithMember_POST_VALID")
    void signUpWithMemberWithValid() throws Exception {
        //given
        MemberSignupResponseDto member = MemberSignupResponseDto.builder()
                .memberId(1L)
                .email(EMAIL)
                .name("김무건")
                .birthday("2020-11-11")
                .regdate(LocalDateTime.now())
                .build();

        given(memberService.signUpMember(any(MemberSignupDto.class))).willReturn(member);
        // when
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/member/signUp")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(member))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
        //then
        //verify()
    }
}