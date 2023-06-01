package com.challenge.studytime.domain.study.dto.response;

import com.challenge.studytime.domain.study.entity.Study;
import com.challenge.studytime.domain.study.entity.StudyMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyResponseDto {
    private String title;
    private String content;
    private Integer membersCount;

    public static StudyResponseDto toDto(Study study) {
        return StudyResponseDto.builder()
                .content(study.getContent())
                .title(study.getTitle())
                .membersCount(study.getMembersCount())
                .build();
    }

    public StudyResponseDto(Study study) {
        this.title = study.getTitle();
        this.content = study.getContent();
        this.membersCount = study.getMembersCount();
    }
}