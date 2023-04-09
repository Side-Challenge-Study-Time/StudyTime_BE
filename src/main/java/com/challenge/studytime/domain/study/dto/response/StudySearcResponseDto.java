package com.challenge.studytime.domain.study.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class StudySearcResponseDto {
    private Long memberId;
    private String memberName;
    private String studyTitle;
    private String studyContent;
    private int membersCount;

    @QueryProjection
    public StudySearcResponseDto(Long memberId, String memberName, String studyTitle, String studyContent, int membersCount) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.studyTitle = studyTitle;
        this.studyContent = studyContent;
        this.membersCount = membersCount;
    }
}

