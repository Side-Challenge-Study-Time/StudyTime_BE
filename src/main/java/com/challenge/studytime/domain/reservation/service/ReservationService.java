package com.challenge.studytime.domain.reservation.service;

import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepository;
import com.challenge.studytime.domain.reservation.dto.request.ReservationRequestDto;
import com.challenge.studytime.domain.reservation.dto.response.ReservationResponseDto;
import com.challenge.studytime.domain.reservation.entity.Reservation;
import com.challenge.studytime.domain.reservation.repository.ReservationRepository;
import com.challenge.studytime.domain.studyroom.entity.StudyRoom;
import com.challenge.studytime.domain.studyroom.repository.StudyRoomRepository;
import com.challenge.studytime.global.exception.member.NotFoundMemberid;
import com.challenge.studytime.global.exception.reservation.DifferentDateException;
import com.challenge.studytime.global.exception.reservation.NotFoundReservation;
import com.challenge.studytime.global.exception.studyroom.NotFoundStudyRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final MemberRepository memberRepositry;
    private final StudyRoomRepository studyRoomRepository;
    private final ReservationRepository reservationRepository;

    public boolean isReservationAvailable(Long studyRoomId, ReservationRequestDto requestDto) {
        LocalDateTime start = requestDto.getStartDate();
        LocalDateTime end = requestDto.getEndDate();
        List<Reservation> conflictingReservations = reservationRepository
                .findConflictingReservationsByDate(studyRoomId, requestDto.getReservationDate(), start, end);
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime minValidTime = LocalDateTime.of(requestDto.getReservationDate(), LocalTime.of(7, 0));
        LocalDateTime maxValidTime = LocalDateTime.of(requestDto.getReservationDate(), LocalTime.of(22, 0));
        if (start.isBefore(now) || end.isBefore(start) || start.isBefore(minValidTime) || end.isAfter(maxValidTime)) {
            return false;
        }
        if (!conflictingReservations.isEmpty()) {
            return false;
        }
        if (start.isBefore(now) || end.isBefore(start)) {
            return false;
        }
        return true;
    }
    @Transactional
    public ReservationResponseDto registerReservation(Long memberId, Long RoomId,ReservationRequestDto requestDto){
        Member member = memberRepositry.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberid(memberId));
        StudyRoom studyRoom = studyRoomRepository.findById(RoomId)
                .orElseThrow(() -> new NotFoundStudyRoom());
        if (!isReservationAvailable(RoomId, requestDto))
            throw new DifferentDateException();
        LocalTime startTime = requestDto.getStartDate().toLocalTime();
        LocalTime endTime = requestDto.getEndDate().toLocalTime();

        Reservation reservation = reservationRepository.save(
                Reservation.builder()
                        .reservationDate(requestDto.getReservationDate())
                        .startDate(LocalDateTime.of(requestDto.getReservationDate(), startTime))
                        .endDate(LocalDateTime.of(requestDto.getReservationDate(), endTime))
                        .build()
        );
        reservation.setMemberFromReservation(member);
        reservation.setStudyRoomFromReservation(studyRoom);
        return ReservationResponseDto.toDto(studyRoom , memberId, requestDto);
    }
    public List<ReservationResponseDto> convertToDtoList(List<Reservation> reservations){
        List<ReservationResponseDto> responseDtos = new ArrayList<>();
        for (Reservation reservation : reservations){
            ReservationResponseDto responseDto = ReservationResponseDto.builder()
                    .capacity(reservation.getStudyRoom().getCapacity())
                    .price(reservation.getStudyRoom().getPrice())
                    .roomName(reservation.getStudyRoom().getName())
                    .location(reservation.getStudyRoom().getLocation())
                    .description(reservation.getStudyRoom().getDescription())
                    .reservationDate(reservation.getReservationDate())
                    .startDate(reservation.getStartDate())
                    .endDate(reservation.getEndDate())
                    .build();
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }

    @Transactional(readOnly = true)
    public List<ReservationResponseDto> searchReservation(Long memberId, Long roomId){
        if (!reservationRepository.existsByStudyRoomIdAndMemberId(roomId, memberId)){
            throw new NotFoundReservation();
        }
        List<Reservation> reservations = reservationRepository.findAllByStudyRoomIdAndMemberId(roomId, memberId);
        return convertToDtoList(reservations);
    }
    @Transactional(readOnly = true)
    public List<ReservationResponseDto> fullSearchReservation(Long memberId){
        if (reservationRepository.findAllByMemberId(memberId).isEmpty()){
            throw new NotFoundReservation();
        }
        List<Reservation> reservations = reservationRepository.findAllByMemberId(memberId);
        return convertToDtoList(reservations);
    }
    @Transactional
    public void modifyReservation(Long reservationId, Long memberId, Long roomId, ReservationRequestDto requestDto){
        if(!reservationRepository.existsByIdAndStudyRoomIdAndMemberId(reservationId, roomId, memberId)){
            throw new NotFoundReservation();
        }
        if (!isReservationAvailable(roomId, requestDto)){
            throw new DifferentDateException();
        }
        Reservation reservation = reservationRepository.findByIdAndStudyRoomIdAndMemberId(reservationId, roomId, memberId);
        reservation.modifyReservation(requestDto);
    }
    @Transactional
    public void deleteReservation(Long reservationId, Long memberId, Long roomId) {
        if (!reservationRepository.existsByIdAndStudyRoomIdAndMemberId(reservationId, roomId, memberId)) {
            throw new NotFoundReservation();
        }
        reservationRepository.deleteByIdAndStudyRoomIdAndMemberId(reservationId, roomId, memberId);
    }
}
