package com.challenge.studytime.domain.study.service;

import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepository;
import com.challenge.studytime.domain.role.entity.Role;
import com.challenge.studytime.domain.role.enums.RoleEnum;
import com.challenge.studytime.domain.role.repositry.RoleRepository;
import com.challenge.studytime.domain.study.entity.Study;
import com.challenge.studytime.domain.study.repository.StudyRepository;
import com.challenge.studytime.global.exception.study.NotFoundStudyWithId;
import com.challenge.studytime.global.util.LoginUserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class StudyMemberServiceTest {

    @Autowired
    private StudyMemberService studyMemberService;
    @Autowired
    private StudyRepository studyRepository;
    @Autowired
    private MemberRepository MemberRepository;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .email("test@gmail.com")
                .name("김무건")
                .password("1234")
                .build();

        MemberRepository.save(member);

        Study study = Study.builder()
                .title("제목")
                .content("내용")
                .membersCount(100)
                .build();

        study.addMemberWithStudy(member);

        Optional<Role> leaderRole = roleRepository.findByName(RoleEnum.ROLE_STUDY_LEADER.getRoleName());
        leaderRole.ifPresent(member::addRole);

        studyRepository.save(study);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("싱글 스레드 환경에서 참여 스터디 조회")
    public void studyMemberCreateJoinMemberWithSingleThread
            () throws Exception {
        //given
        LoginUserDto userDto = LoginUserDto.builder()
                .memberId(1L)
                .build();
        //when
        studyMemberService.create(1L, userDto);

        //Then
        Study study = studyRepository.findByIdAndDeleteStudyFalse(1L)
                .orElseThrow(() -> new NotFoundStudyWithId(1L));

        Assertions.assertThat(study.getMembersCount()).isEqualTo(99);
    }


    @Test
    @DisplayName("멀티쓰레드 환경에서 테스트")
    public void studyMemberCreateJoinMemberWithMultiThread() throws Exception {
        LoginUserDto userDto = LoginUserDto.builder()
                .memberId(1L)
                .build();
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    studyMemberService.create(1L, userDto);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        Study study = studyRepository.findById(1L)
                .orElseThrow();
        assertThat(study.getMembersCount()).isEqualTo(0);
    }

}