package com.challenge.studytime.domain.study.entity;

import com.challenge.studytime.domain.image.entity.ImageData;
import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.study.dto.request.StudyModifyRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {

    @Mock
    private Member member;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("스터디 수정 로직 테스트")
    void updateStudyTest() {
        Study study = Study.builder()
                .id(1L)
                .title("Test Study")
                .content("This is a test study.")
                .membersCount(3)
                .build();

        StudyModifyRequestDto requestDto = StudyModifyRequestDto.builder()
                .title("Updated Test Study")
                .content("This is an updated test study.")
                .membersCount(5)
                .build();

        study.updateStudy(requestDto);

        assertEquals(requestDto.getTitle(), study.getTitle());
        assertEquals(requestDto.getContent(), study.getContent());
        assertEquals(requestDto.getMembersCount(), study.getMembersCount());
    }

    @Test
    @DisplayName("연관 관계 수정 메소드 테스트")
    void addMemberWithStudyTest() {
        Study study = Study.builder()
                .id(1L)
                .title("Test Study")
                .content("This is a test study.")
                .membersCount(3)
                .build();

        Member member = Member.builder()
                .id(1L)
                .name("Test Member")
                .build();

        study.addMemberWithStudy(member);

        assertEquals(member, study.getMember());
        assertTrue(member.getStudyList().contains(study));
    }

    @Test
    @DisplayName("감소 로직 테스트")
    void decreaseMembersCountTest() {
        Study study = Study.builder()
                .id(1L)
                .title("Test Study")
                .content("This is a test study.")
                .membersCount(3)
                .build();

        study.decreaseMembersCount();

        assertEquals(2, study.getMembersCount());
    }

    @Test
    @DisplayName("변경 테스트 로직")
    void changeStudyTest() {
        Study study = Study.builder()
                .id(1L)
                .title("Test Study")
                .content("This is a test study.")
                .membersCount(3)
                .build();

        study.changeStudy();

        assertTrue(study.isDeleteStudy());
    }

}
