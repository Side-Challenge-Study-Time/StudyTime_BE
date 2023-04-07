package com.challenge.studytime.domain.member.dto.response;

import com.challenge.studytime.domain.member.entity.Member;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginResponseDto {

    private String accessToken;
    private String refreshToken;

    private Long id;
    private String email;
    private String name;

    public static MemberLoginResponseDto toDto(Member member, String accessToken,String refreshToken) {
        return MemberLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}


