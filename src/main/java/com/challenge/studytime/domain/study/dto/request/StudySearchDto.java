package com.challenge.studytime.domain.study.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudySearchDto {

    private String studyTitle;

    private String studyContent;

    private Integer membersCountGoe;

    private Integer membersCountsLoe;
}

