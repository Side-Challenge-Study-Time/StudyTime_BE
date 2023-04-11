package com.challenge.studytime.domain.study.service;

import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepositry;
import com.challenge.studytime.domain.study.entity.Study;
import com.challenge.studytime.domain.study.entity.StudyMember;
import com.challenge.studytime.domain.study.repository.StudyMemberRepository;
import com.challenge.studytime.domain.study.repository.StudyRepository;
import com.challenge.studytime.global.exception.member.NotFoundMemberid;
import com.challenge.studytime.global.exception.study.MembersWhoInvalidJoinCount;
import com.challenge.studytime.global.exception.study.NotFoundStudyWithId;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyMemberService {

    private final StudyMemberRepository studyMemberRepository;
    private final StudyRepository studyRepository;
    private final MemberRepositry memberRepositry;

    @Transactional
    public void create(Long studyId, LoginUserDto userDto) {

        preventionDuplicateStudyParticipation(studyId, userDto);

        // StudyId로 Study 엔티티를 찾아옵니다.
        Study study = studyRepository.findByIdAndDeleteStudyFalse(studyId)
                .orElseThrow(() -> new NotFoundStudyWithId(studyId));

        // MemberId로 Member 엔티티를 찾아옵니다.
        Member member = memberRepositry.findById(userDto.getMemberId())
                .orElseThrow(() -> new NotFoundMemberid(userDto.getMemberId()));


        if (study.getMembersCount() != 0) {
            study.decreaseMembersCount();
        } else {
            throw new MembersWhoInvalidJoinCount();
        }


        // StudyMember 객체를 생성하고 연관 관계를 설정합니다.
        StudyMember studyMemberS = StudyMember.builder()
                .study(study)
                .member(member)
                .build();

        // StudyMember를 저장합니다.
        studyMemberRepository.save(studyMemberS);
    }

    private void preventionDuplicateStudyParticipation(Long studyId, LoginUserDto userDto) {
        List<StudyMember> studyMember = studyMemberRepository.findAllWithStudyAndMemberByStudyId(studyId);


        List<Long> memberIds = studyMember.stream()
                .map(sm -> sm.getMember().getId())
                .collect(Collectors.toList());

        if (memberIds.contains(userDto.getMemberId())) {
            throw new RuntimeException("Duplicate member id detected: " + userDto.getMemberId()); // 특정 값과 중복된 경우 오류 발생
        }
    }
}
