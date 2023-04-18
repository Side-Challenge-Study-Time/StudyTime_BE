package com.challenge.studytime.domain.comment.service;

import com.challenge.studytime.domain.comment.dto.response.CommentDto;
import com.challenge.studytime.enums.TestValidEnum;
import com.challenge.studytime.domain.comment.repository.CommentRepository;
import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepository;
import com.challenge.studytime.domain.study.entity.Study;
import com.challenge.studytime.domain.study.repository.StudyRepository;
import com.challenge.studytime.global.util.LoginUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    private static final String TITLE = "제목";
    private static final String CONTENT = "내용";

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .email(TestValidEnum.VALID_EMAIL.getMessage())
                .name(TestValidEnum.VALID_NAME.getMessage())
                .password(TestValidEnum.VALID_PASSWORD.getMessage())
                .birthday(TestValidEnum.VALID_BIRTHDAY.getMessage())
                .build();

        memberRepository.save(member);

        Study study = Study.builder()
                .title(TITLE)
                .content(CONTENT)
                .membersCount(100)
                .build();
        studyRepository.save(study);
    }

    @Test
    @DisplayName("부모 댓글 생성 테스트")
    public void createParentCommentTest() {
        //given
        LoginUserDto userDto = LoginUserDto.builder()
                .memberId(1L)
                .build();
        Long studyId = 1L;

        //when
        CommentDto commentDto = commentService.createParentComment(CONTENT, userDto, studyId);

        //then
        assertThat(commentDto.getId()).isNotNull();
        assertThat(commentDto.getContent()).isEqualTo(CONTENT);
    }

    @Test
    @DisplayName("부모 댓글에 대한 답글 생성 테스트")
    public void createReplyCommentTest() {
        //given
        LoginUserDto userDto = LoginUserDto.builder()
                .memberId(1L)
                .build();
        Long studyId = 1L;

        CommentDto parentCommentDto = commentService.createParentComment(CONTENT, userDto, studyId);

        //when
        CommentDto replyCommentDto = commentService.createReplyComment(parentCommentDto.getId(), CONTENT, userDto);

        //then
        assertThat(replyCommentDto.getId()).isNotNull();
        assertThat(replyCommentDto.getContent()).isEqualTo(CONTENT);
        assertThat(replyCommentDto.getParentId()).isEqualTo(parentCommentDto.getId());
    }

    @Test
    @DisplayName("모든 댓글과 답글 검색 테스트")
    public void fullSearchWithCommentTest() {
        //given
        LoginUserDto userDto = LoginUserDto.builder()
                .memberId(1L)
                .build();
        Long studyId = 1L;

        CommentDto parentCommentDto = commentService.createParentComment(CONTENT, userDto, studyId);

        CommentDto replyCommentDto = commentService.createReplyComment(parentCommentDto.getId(), CONTENT, userDto);

        //when
        List<CommentDto> commentDtoList = commentService.fullSrchWithComment();

        //then
        assertThat(commentDtoList).hasSize(3);
    }
}