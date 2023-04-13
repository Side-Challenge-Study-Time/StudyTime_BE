package com.challenge.studytime.domain.studyroom.dto.response;

import com.challenge.studytime.domain.studyroom.entity.StudyRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoomResponseDto {
    private Long id;
    private int price;
    private int capacity;
    private String name;
    private String location;
    private String description;
    public static StudyRoomResponseDto toDto(StudyRoom studyRoom){
        return StudyRoomResponseDto.builder()
                .id(studyRoom.getId())
                .price(studyRoom.getPrice())
                .capacity(studyRoom.getCapacity())
                .name(studyRoom.getName())
                .location(studyRoom.getLocation())
                .description(studyRoom.getDescription())
                .build();
    }
}
