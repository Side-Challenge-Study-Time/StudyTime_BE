package com.challenge.studytime.domain.study.service;

import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepositry;
import com.challenge.studytime.domain.role.entity.Role;
import com.challenge.studytime.domain.role.enums.RoleEnum;
import com.challenge.studytime.domain.role.repositry.RoleRepository;
import com.challenge.studytime.domain.study.dto.request.StudyModifyRequestDto;
import com.challenge.studytime.domain.study.dto.request.StudyRequestDto;
import com.challenge.studytime.domain.study.dto.request.StudySearchDto;
import com.challenge.studytime.domain.study.dto.response.StudyResponseDto;
import com.challenge.studytime.domain.study.dto.response.StudySearcResponseDto;
import com.challenge.studytime.domain.study.entity.Study;
import com.challenge.studytime.domain.study.repository.StudyRepository;
import com.challenge.studytime.global.exception.member.NotFoundMemberid;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final MemberRepositry memberRepositry;
    private final StudyRepository studyRepository;
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

        return StudyResponseDto.toDto(studyRepository.save(study));
    }

    @Transactional(readOnly = true)
    public Page<StudySearcResponseDto> fullSearch(
            StudySearchDto requestDto,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return studyRepository.fullSrchWithStudy(requestDto, pageable);
    }

    @Transactional(readOnly = true)
    public StudyResponseDto detailStudy(Long studyId) {
        Study study = studyRepository.findByIdAndDeleteStudyFalse(studyId)
                .orElseThrow();
        return StudyResponseDto.toDto(study);
    }

    @Transactional
    public void deleteByStudy(Long id) {
        Study study = studyRepository.findByIdAndDeleteStudyFalse(id)
                .orElseThrow();
        study.changeStudy();
    }

    @Transactional
    public StudyResponseDto modifyById(Long id, StudyModifyRequestDto studyModifyRequestDto) {
        Study study = studyRepository.findByIdAndDeleteStudyFalse(id)
                .orElseThrow();

        study.updateStudy(studyModifyRequestDto);
        return StudyResponseDto.toDto(study);
    }
}
