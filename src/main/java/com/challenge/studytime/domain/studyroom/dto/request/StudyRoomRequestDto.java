package com.challenge.studytime.domain.studyroom.dto.request;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoomRequestDto {
    @NotNull
    private int price;
    @NotNull
    private int capacity;
    @NotNull
    private String name;
    @NotNull
    private String location;
    @NotNull
    private String description;
}