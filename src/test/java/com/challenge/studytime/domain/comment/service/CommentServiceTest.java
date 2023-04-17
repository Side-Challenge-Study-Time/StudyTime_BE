package com.challenge.studytime.domain.comment.service;

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

    private static final String TITLE ="제목";
    private static final String CONTENT ="내용";

    @BeforeEach
    void setUp(){
        Member member = Member.builder()
                .email(TestValidEnum.VALID_EMAIL.getMessage())
                .name(TestValidEnum.VALID_NAME.getMessage())
                .password(TestValidEnum.VALID_PASSWORD.getMessage())
                .birthday(TestValidEnum.VALID_BIRTHDAY.getMessage())
                .build();

        memberRepository.save(member);

        Study study =Study.builder()
                .title(TITLE)
                .content(CONTENT)
                .membersCount(100)
                .build();
        studyRepository.save(study);
    }

    @Test
    @DisplayName("댓글 생성 테스트 로직")
    public void commentWithValid() throws Exception{
        //given
        LoginUserDto userDto = LoginUserDto.builder()
                .memberId(1L)
                .build();

        Long studyId = 1L;
        //when
        commentService.createParentComment(CONTENT,userDto,studyId);
        //Then
        assertThat(commentRepository.count()).isNotNull();
        assertThat(commentRepository.findById(1L).get().getContent()).isEqualTo(CONTENT);
    }
}