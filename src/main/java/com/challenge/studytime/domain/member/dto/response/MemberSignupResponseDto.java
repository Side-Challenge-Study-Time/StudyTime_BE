package com.challenge.studytime.domain.member.dto.response;

import com.challenge.studytime.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignupResponseDto {
    private Long memberId;
    private String email;
    private String name;
    private String birthday;
    private LocalDateTime regdate;

    public static MemberSignupResponseDto toDto(Member member) {
        return MemberSignupResponseDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .birthday(member.getBirthday())
                .regdate(LocalDateTime.now())
                .build();
    }
}
