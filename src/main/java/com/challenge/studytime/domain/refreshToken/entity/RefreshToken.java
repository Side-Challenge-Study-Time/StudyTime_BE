package com.challenge.studytime.domain.refreshToken.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "refresh_token")
@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private String value;
}
