package com.challenge.studytime.domain.reservation.repository;

import com.challenge.studytime.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
        Reservation findByIdAndStudyRoomIdAndMemberId(Long reservationId, Long roomId, Long memberId);
        boolean existsByStudyRoomIdAndMemberId(Long roomId, Long memberId);
        boolean existsByIdAndStudyRoomIdAndMemberId(Long reservationId, Long roomId, Long memberId);
        void deleteByIdAndStudyRoomIdAndMemberId(Long reservationId, Long roomId, Long memberId);
        List<Reservation> findAllByMemberId(Long memberId);
        List<Reservation> findAllByStudyRoomIdAndMemberId(Long roomId, Long memberId);
        @Query("SELECT r FROM Reservation r " +
                "WHERE r.studyRoom.id = :studyRoomId " +
                "AND r.reservationDate = :date " +
                "AND ((r.startDate < :end AND r.endDate > :start) OR " +
                "(r.startDate = :start AND r.endDate = :end))")
        List<Reservation> findConflictingReservationsByDate(
                @Param("studyRoomId") Long studyRoomId,
                @Param("date") LocalDate date,
                @Param("start") LocalDateTime start,
                @Param("end") LocalDateTime end
        );
}