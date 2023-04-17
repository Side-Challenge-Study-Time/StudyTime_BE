package com.challenge.studytime.domain.study.service;

import com.challenge.studytime.domain.member.repositry.MemberRepository;
import com.challenge.studytime.domain.role.repositry.RoleRepository;
import com.challenge.studytime.domain.study.repository.StudyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class StudyServiceTest {

    private StudyService studyService;

    private MemberRepository memberRepository = mock(MemberRepository.class);
    private StudyRepository studyRepository = mock(StudyRepository.class);
    private RoleRepository roleRepository = mock(RoleRepository.class);

    @BeforeEach
    void setUp(){

    }

    @Test
    @DisplayName("회원A가 스터디 생성 VALID")
    public void registerStudyProjectWithValid() throws Exception{
        //given

        //when

        //Then
        //Assertions.assertThat().isEqualTo();
    }

}