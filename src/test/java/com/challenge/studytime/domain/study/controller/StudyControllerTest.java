package com.challenge.studytime.domain.study.controller;

import com.challenge.studytime.domain.study.dto.request.StudyModifyRequestDto;
import com.challenge.studytime.domain.study.dto.request.StudyRequestDto;
import com.challenge.studytime.domain.study.dto.response.StudyResponseDto;
import com.challenge.studytime.domain.study.entity.Study;
import com.challenge.studytime.domain.study.service.StudyService;
import com.challenge.studytime.global.jwt.util.JwtTokenizer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudyService studyService;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private ObjectMapper objectMapper;

    private Long memberId;
    private StudyRequestDto studyRequestDto;
    private StudyResponseDto expectedResponseDto;
    private String token;

    @BeforeEach
    public void setUp() {
        memberId = 1L;

        studyRequestDto = new StudyRequestDto();
        studyRequestDto.setTitle("test title");
        studyRequestDto.setContent("test content");
        studyRequestDto.setMembersCount(5);

        expectedResponseDto = StudyResponseDto.builder()
                .title("test title")
                .content("test content")
                .membersCount(5)
                .build();

        given(studyService.registerStudyProject(memberId, studyRequestDto))
                .willReturn(expectedResponseDto);

        token = jwtTokenizer.createAccessToken(1L, "test@test.com", List.of("ROLE_USER"));
    }

    @Test
    @DisplayName("Post 스터디 생성 테스트")
    public void registerVA() throws Exception {
        // when
        mockMvc.perform(post("/api/study/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content("{\"title\":\"test title\", \"content\":\"test content\", \"membersCount\":5}"))
                .andExpect(status().isCreated())
                .andDo(print());

        // then
        verify(studyService).registerStudyProject(memberId,studyRequestDto);
    }

    @Test
    @DisplayName("Get Study 상세 조회 테스트")
    public void detailValidWithAccessToken() throws Exception{
        // given
        Long memberId = 1L;
        StudyResponseDto expectedResponseDto1 = StudyResponseDto.builder()
                .title("Study 1")
                .content("Study Content 1")
                .membersCount(5)
                .build();

        StudyResponseDto expectedResponseDto2 = StudyResponseDto.builder()
                .title("Study 2")
                .content("Study Content 2")
                .membersCount(3)
                .build();

        List<StudyResponseDto> expectedResponseDtoList = new ArrayList<>();
        expectedResponseDtoList.add(expectedResponseDto1);
        expectedResponseDtoList.add(expectedResponseDto2);

        given(studyService.detailStudy(memberId)).willReturn(expectedResponseDtoList);

        String token = jwtTokenizer.createAccessToken(memberId, "test@test.com", List.of("ROLE_USER"));

        // when
        MvcResult result = mockMvc.perform(get("/api/study/detail")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        // then
        List<StudyResponseDto> actualResponseDtoList = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        assertThat(actualResponseDtoList).hasSize(expectedResponseDtoList.size());
        assertThat(actualResponseDtoList.get(0)).usingRecursiveComparison().isEqualTo(expectedResponseDto1);
        assertThat(actualResponseDtoList.get(1)).usingRecursiveComparison().isEqualTo(expectedResponseDto2);

        verify(studyService).detailStudy(memberId);
    }

    @Test
    @DisplayName("Delete 스터디 삭제 테스트")
    public void deleteStudyTest() throws Exception {
        // given
        Long studyId = 1L;

        // when
        mockMvc.perform(delete("/api/study/delete/{id}", studyId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNoContent())
                .andDo(print());

        // then
        verify(studyService).deleteByStudy(studyId);
    }


    @Test
    @DisplayName("Patch Study 수정 테스트")
    public void modifyValid() throws Exception {
        // given
        Long studyId = 1L;
        StudyModifyRequestDto studyModifyRequestDto = StudyModifyRequestDto.builder()
                .title("Modified Title")
                .content("Modified Content")
                .membersCount(7)
                .build();

        Study study = Study.builder()
                .title("Original Title")
                .content("Original Content")
                .membersCount(5)
                .build();

        StudyResponseDto expectedResponseDto = StudyResponseDto.builder()
                .title("Modified Title")
                .content("Modified Content")
                .membersCount(7)
                .build();

        given(studyService.modifyById(studyId, studyModifyRequestDto)).willReturn(expectedResponseDto);

        // when
        MvcResult result = mockMvc.perform(patch("/api/study/modify/{id}", studyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(objectMapper.writeValueAsString(studyModifyRequestDto)))
                .andExpect(status().isOk())
                .andReturn();

        // then
        StudyResponseDto actualResponseDto = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                StudyResponseDto.class
        );

        assertThat(actualResponseDto).usingRecursiveComparison().isEqualTo(expectedResponseDto);

        verify(studyService).modifyById(studyId, studyModifyRequestDto);
    }

}