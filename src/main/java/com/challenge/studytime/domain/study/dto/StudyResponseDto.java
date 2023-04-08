package com.challenge.studytime.domain.study.dto;

import com.challenge.studytime.domain.study.entity.Study;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyResponseDto {
    private String title;
    private String content;
    private int membersCount;

    public static StudyResponseDto toDto(Study study) {
        return StudyResponseDto.builder()
                .content(study.getContent())
                .title(study.getTitle())
                .membersCount(study.getMembersCount())
                .build();
    }
}
/*
    public static MemberSignupResponseDto toDto(Member member) {
        return MemberSignupResponseDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .birthday(member.getBirthday())
                .regdate(LocalDateTime.now())
                .build();
    }
 */