package com.challenge.studytime.domain.comment.controller;

import com.challenge.studytime.domain.comment.dto.response.CommentDto;
import com.challenge.studytime.domain.comment.entity.Comment;
import com.challenge.studytime.domain.comment.repository.CommentRepository;
import com.challenge.studytime.domain.comment.service.CommentService;
import com.challenge.studytime.domain.role.enums.RoleEnum;
import com.challenge.studytime.global.jwt.util.JwtTokenizer;
import com.challenge.studytime.global.util.LoginUserDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hibernate.boot.TempTableDdlTransactionHandling;
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

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("최초 댓글 생성")
    public void registerCommentValid() throws Exception {
        // given
        Long studyId = 1L;
        String content = "This is a comment";
        LoginUserDto userDto = LoginUserDto.builder()
                .memberId(1L)
                .roles(List.of(RoleEnum.ROLE_USER.getRoleName()))
                .build();

        CommentDto commentDto = CommentDto.builder()
                .content(content)
                .build();

        given(commentService.createParentComment(content, userDto, studyId))
                .willReturn(commentDto);

        String token = jwtTokenizer.createAccessToken(1L, "test@test.com", List.of("ROLE_USER"));

        // when
        mockMvc.perform(post("/api/study/{studyId}/comment", studyId)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isCreated())
                .andDo(print());

        // then
        verify(commentService).createParentComment(content, userDto, studyId);
    }

    @Test
    @DisplayName("최초 댓글에 대댓글 작성하기")
    public void registerReplyCommentValid() throws Exception {
        // given
        Long parentId = 1L;
        String content = "This is a reply";
        LoginUserDto userDto = LoginUserDto.builder()
                .memberId(1L)
                .roles(List.of(RoleEnum.ROLE_USER.getRoleName()))
                .build();
        CommentDto expectedCommentDto = CommentDto.builder()
                .content(content)
                .build();
        given(commentService.createReplyComment(parentId, content, userDto))
                .willReturn(expectedCommentDto);

        String token = jwtTokenizer.createAccessToken(1L, "test@test.com", List.of("ROLE_USER"));

        // when
        MvcResult result = mockMvc.perform(post("/api/study/comment/{parentId}/reply", parentId)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isCreated())
                .andReturn();

        // then
        String responseBody = result.getResponse().getContentAsString();
        CommentDto actualCommentDto = objectMapper.readValue(responseBody, CommentDto.class);
        assertThat(actualCommentDto).isEqualTo(expectedCommentDto);
        verify(commentService).createReplyComment(parentId, content, userDto);
    }

    @Test
    @DisplayName("작성한 댓글 가져오기")
    public void getCommentValid() throws Exception {
        // given
        List<CommentDto> expectedCommentDtoList = new ArrayList<>();
        CommentDto commentDto1 = CommentDto.builder()
                .id(1L)
                .content("This is a parent comment")
                .build();
        CommentDto commentDto2 = CommentDto.builder()
                .id(2L)
                .content("This is a reply comment")
                .parentId(1L)
                .build();
        expectedCommentDtoList.add(commentDto1);
        expectedCommentDtoList.add(commentDto2);
        given(commentService.fullSrchWithComment())
                .willReturn(expectedCommentDtoList);

        // when
        MvcResult result = mockMvc.perform(get("/api/study/comments/search")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String responseBody = result.getResponse().getContentAsString();
        List<CommentDto> actualCommentDtoList = objectMapper.readValue(responseBody, new TypeReference<>() {
        });
        assertThat(actualCommentDtoList).isEqualTo(expectedCommentDtoList);
        verify(commentService).fullSrchWithComment();
    }
}