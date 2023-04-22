package com.challenge.studytime.domain.study.controller;

import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepository;
import com.challenge.studytime.domain.study.dto.response.StudyMemberResponse;
import com.challenge.studytime.domain.study.entity.Study;
import com.challenge.studytime.domain.study.repository.StudyRepository;
import com.challenge.studytime.global.jwt.util.JwtTokenizer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class StudyMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    private String token;

    private AtomicLong idGenerator = new AtomicLong(0L);

    @BeforeEach
    void setUp() {
        token = jwtTokenizer.createAccessToken(1L, "test@test.com", List.of("ROLE_USER"));

        Member member = Member.builder()
                .id(1L)
                .email("test@test.com")
                .name("김무건")
                .password("1234")
                .birthday("2015-12-12")
                .build();

        memberRepository.save(member);


        Study study = Study.builder()
                .id(idGenerator.incrementAndGet())
                .title("제목")
                .content("내용")
                .membersCount(10)
                .build();

        studyRepository.save(study);
    }

    @Test
    @DisplayName("특정 스터디 생성 스터디에 참가")
    void registerStudyValidWithMember() throws Exception {
        //given
        Long memberId = 1L;
        String token = jwtTokenizer.createAccessToken(memberId, "test@test.com", List.of("ROLE_USER"));
        Member member = Member.builder().id(memberId).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(member);
        // when
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/study/member/{studyId}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("전체 스터디 멤버 조회")
    void findFullSrchStudyMember() throws Exception {
        //given
        Long memberId = 1L;
        String token = jwtTokenizer.createAccessToken(memberId, "test@test.com", List.of("ROLE_USER"));

        // when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/study/members")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // then
        List<StudyMemberResponse> responseList = new ObjectMapper().readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertThat(responseList).isNotNull();
    }
}
