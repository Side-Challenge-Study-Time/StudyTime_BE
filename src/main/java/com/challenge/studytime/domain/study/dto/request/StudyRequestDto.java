package com.challenge.studytime.domain.study.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRequestDto {
    private String title;
    private String content;
    private int membersCount;
}
