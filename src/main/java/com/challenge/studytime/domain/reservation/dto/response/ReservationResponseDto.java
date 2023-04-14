package com.challenge.studytime.domain.reservation.dto.response;

import com.challenge.studytime.domain.reservation.dto.request.ReservationRequestDto;
import com.challenge.studytime.domain.studyroom.entity.StudyRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDto {
    private Long id;
    private Long memberId;
    private int capacity;
    private int price;
    private String roomName;
    private String location;
    private String description;
    private LocalDate reservationDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    public static ReservationResponseDto toDto(StudyRoom studyRoom,
                                               Long memberId,
                                               ReservationRequestDto requestDto){
        return ReservationResponseDto.builder()
                .id(studyRoom.getId())
                .memberId(memberId)
                .capacity(studyRoom.getCapacity())
                .price(studyRoom.getPrice() * (requestDto.getEndDate().getHour() - requestDto.getStartDate().getHour()))
                .roomName(studyRoom.getName())
                .location(studyRoom.getLocation())
                .description(studyRoom.getDescription())
                .reservationDate(requestDto.getReservationDate())
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .build();
    }
}
