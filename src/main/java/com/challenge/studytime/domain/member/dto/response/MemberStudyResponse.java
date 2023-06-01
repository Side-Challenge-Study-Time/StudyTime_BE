package com.challenge.studytime.domain.member.dto.response;

import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.study.dto.response.StudyMemberResponse;
import com.challenge.studytime.domain.study.dto.response.StudyResponseDto;
import com.challenge.studytime.domain.study.entity.Study;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberStudyResponse {

    private Long id;
    private String email;
    private String name;
    private List<StudyResponseDto> studyResponseDtos;
    private List<StudyMemberResponse> studyMemberResponses;


    public static MemberStudyResponse toDto(Member member) {
        return MemberStudyResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }

    public MemberStudyResponse(Member member, Study study) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.studyResponseDtos = member.getStudyList().stream()
                .map(StudyResponseDto::toDto)
                .collect(Collectors.toList());

        this.studyMemberResponses = member.getStudyMembers().stream()
                .map(StudyMemberResponse::toDto)
                .collect(Collectors.toList());
    }

}

