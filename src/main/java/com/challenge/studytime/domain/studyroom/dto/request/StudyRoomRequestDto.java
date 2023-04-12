package com.challenge.studytime.domain.studyroom.dto.request;


import lombok.*;

import javax.persistence.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoomRequestDto {
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int capacity;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String description;
}