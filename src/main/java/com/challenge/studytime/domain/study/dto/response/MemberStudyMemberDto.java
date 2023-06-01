package com.challenge.studytime.domain.study.dto.response;

import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.study.entity.Study;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberStudyMemberDto {
    private Long memberId;
    private String memberName;
    private List<StudyResponseDto> studyResponseDtos;
    private List<StudyMemberResponse> studyMemberResponses;

    public MemberStudyMemberDto(Member member) {
        this.memberId = member.getId();
        this.memberName = member.getName();

        this.studyResponseDtos = member.getStudyList().stream()
                .map(StudyResponseDto::new)
                .collect(Collectors.toList());

        this.studyMemberResponses = member.getStudyMembers().stream()
                .map(StudyMemberResponse::new)
                .collect(Collectors.toList());
    }

}
