package com.challenge.studytime.domain.reservation.controller;

import com.challenge.studytime.domain.reservation.dto.request.ReservationRequestDto;
import com.challenge.studytime.domain.reservation.dto.response.ReservationResponseDto;
import com.challenge.studytime.domain.reservation.service.ReservationService;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/room/{roomId}")
    public ReservationResponseDto registerReservation(@IfLogin LoginUserDto userDto,
                                                      @PathVariable Long roomId,
                                                      @RequestBody @Valid ReservationRequestDto requestDto){
        return reservationService.registerReservation(userDto.getMemberId(), roomId, requestDto);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/search")
    public List<ReservationResponseDto> fullSearchStudyRoom(@IfLogin LoginUserDto userDto){
        return reservationService.fullSearchReservation(userDto.getMemberId());
    }
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/room/{roomId}")
    public List<ReservationResponseDto> searchStudyRoom(@IfLogin LoginUserDto userDto,
                                                        @PathVariable Long roomId){
        return reservationService.searchReservation(userDto.getMemberId(), roomId);
    }
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{reservationId}/room/{roomId}")
    public void modifyReservation(@IfLogin LoginUserDto userDto,
                                  @PathVariable("reservationId") Long reservationId,
                                  @PathVariable("roomId") Long roomId,
                                  @RequestBody @Valid ReservationRequestDto requestDto){
        reservationService.modifyReservation(reservationId, userDto.getMemberId(), roomId, requestDto);
    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{reservationId}/room/{roomId}")
    public void deleteReservation(@IfLogin LoginUserDto userDto,
                                  @PathVariable("reservationId") Long reservationId,
                                  @PathVariable("roomId") Long roomId){
        reservationService.deleteReservation(reservationId, userDto.getMemberId(), roomId);
    }
}
