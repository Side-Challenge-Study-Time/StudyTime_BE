package com.challenge.studytime.domain.studyroom.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoomModifyRequestDto {
    private int price;
    private int capacity;
    private String name;
    private String location;
    private String description;
}
