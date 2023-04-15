package com.challenge.studytime.domain.reservation.entity;

import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.reservation.dto.request.ReservationRequestDto;
import com.challenge.studytime.domain.studyroom.entity.StudyRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;
    @Column(name = "reservation_date",length = 40, nullable = false)
    private LocalDate reservationDate;
    @Column(name = "start_date",length = 40, nullable = false)
    private LocalDateTime startDate;
    @Column(name = "end_date",length = 40, nullable = false)
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_id")
    private StudyRoom studyRoom;

    public void setMemberFromReservation(Member member){
        this.member = member;
        if(member != null && !member.getReservations().contains(this)){
            member.getReservations().add(this);
        }
    }

    public void setStudyRoomFromReservation(StudyRoom studyRoom){
        this.studyRoom = studyRoom;
        if(studyRoom != null && !studyRoom.getReservations().contains(this)){
            studyRoom.getReservations().add(this);
        }
    }
    public void modifyReservation(ReservationRequestDto requestDto){
        this.reservationDate = requestDto.getReservationDate();
        this.startDate = requestDto.getStartDate();
        this.endDate = requestDto.getEndDate();
    }
}
