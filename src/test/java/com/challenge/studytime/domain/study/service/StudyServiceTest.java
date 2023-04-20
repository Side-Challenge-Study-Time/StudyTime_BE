package com.challenge.studytime.domain.study.service;

import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepository;
import com.challenge.studytime.domain.member.service.MemberService;
import com.challenge.studytime.domain.role.repositry.RoleRepository;
import com.challenge.studytime.domain.study.dto.request.StudyRequestDto;
import com.challenge.studytime.domain.study.dto.response.StudyResponseDto;
import com.challenge.studytime.domain.study.repository.StudyRepository;
import com.challenge.studytime.enums.TestValidEnum;
import com.challenge.studytime.global.exception.member.NotFoundMemberEmail;
import com.challenge.studytime.global.exception.member.NotFoundMemberid;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
class StudyServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyService studyService;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private RoleRepository roleRepository;


    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .id(1L)
                .email(TestValidEnum.VALID_EMAIL.getMessage())
                .password(TestValidEnum.VALID_PASSWORD.getMessage())
                .name(TestValidEnum.VALID_NAME.getMessage())
                .birthday(TestValidEnum.VALID_BIRTHDAY.getMessage())
                .build();

        memberRepository.save(member);

        StudyRequestDto requestDto = StudyRequestDto.builder()
                .title("제목")
                .content("내용")
                .membersCount(100)
                .build();

        //when
        studyService.registerStudyProject(1L, requestDto);
    }

    @Test
    @DisplayName("회원A 스터디 생성 VALID")
    public void registerStudyProjectWithValid() throws Exception {
        //given
        StudyRequestDto requestDto = StudyRequestDto.builder()
                .title("제목")
                .content("내용")
                .membersCount(100)
                .build();

        //when
        studyService.registerStudyProject(1L, requestDto);

        //then
        assertThat(studyRepository.count()).isNotNull();
    }


    @Test
    @DisplayName("회원B 스터디 생성 Invalid 테스트")
    public void registerInValidWithNotFoundMember() {
        //given
        StudyRequestDto requestDto = StudyRequestDto.builder()
                .title("제목")
                .content("내용")
                .membersCount(100)
                .build();

        //When, Then
        assertThatThrownBy(() -> studyService.registerStudyProject(99L, requestDto))
                .isInstanceOf(NotFoundMemberid.class)
                .hasMessage("Not Found Member Id:  99");
    }


    @Test
    @DisplayName("특정 회원이 특정 스터디를 조회하는 Detail")
    public void detailValidWithStudy() throws Exception {
        //when
        List<StudyResponseDto> studyResponseDtos = studyService.detailStudy(1L);
        //Then
        assertThat(studyResponseDtos).isNotNull();
        assertThat(studyResponseDtos).hasSize(1);
        StudyResponseDto studyResponseDto = studyResponseDtos.get(0);
        assertThat(studyResponseDto.getTitle()).isEqualTo("제목");
        assertThat(studyResponseDto.getContent()).isEqualTo("내용");
    }
}