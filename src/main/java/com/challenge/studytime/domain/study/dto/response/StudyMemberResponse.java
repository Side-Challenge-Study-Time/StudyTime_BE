package com.challenge.studytime.domain.study.dto.response;

import com.challenge.studytime.domain.study.entity.StudyMember;
import com.challenge.studytime.domain.study.service.StudyMemberService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyMemberResponse {
    private Long id;
    private Long memberId;
    private Long studyId;

    public static StudyMemberResponse toDto(StudyMember studyMember) {
        return StudyMemberResponse.builder()
                .id(studyMember.getId())
                .memberId(studyMember.getMember().getId())
                .studyId(studyMember.getStudy().getId())
                .build();
    }
}
