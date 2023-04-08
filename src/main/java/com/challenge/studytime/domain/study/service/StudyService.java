package com.challenge.studytime.domain.study.service;

import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepositry;
import com.challenge.studytime.domain.role.entity.Role;
import com.challenge.studytime.domain.role.enums.RoleEnum;
import com.challenge.studytime.domain.role.repositry.RoleRepository;
import com.challenge.studytime.domain.study.dto.StudyRequestDto;
import com.challenge.studytime.domain.study.dto.StudyResponseDto;
import com.challenge.studytime.domain.study.entity.Study;
import com.challenge.studytime.domain.study.repository.StudyRepositry;
import com.challenge.studytime.global.exception.member.NotFoundMemberid;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final MemberRepositry memberRepositry;
    private final StudyRepositry studyRepositry;
    private final RoleRepository roleRepository;

    @Transactional
    public StudyResponseDto registerStudyProject(LoginUserDto userDto, StudyRequestDto requestDto) {
        Member member = memberRepositry.findById(userDto.getMemberId())
                .orElseThrow(() -> new NotFoundMemberid(userDto.getMemberId()));

        Study study = Study.builder()
                .content(requestDto.getContent())
                .title(requestDto.getTitle())
                .membersCount(requestDto.getMembersCount())
                .build();

        study.addMemberWithStudy(member);

        Optional<Role> leaderRole = roleRepository.findByName(RoleEnum.ROLE_STUDY_LEADER.getRoleName());
        leaderRole.ifPresent(member::addRole);

        memberRepositry.save(member);

        return StudyResponseDto.toDto(studyRepositry.save(study));
    }

}
