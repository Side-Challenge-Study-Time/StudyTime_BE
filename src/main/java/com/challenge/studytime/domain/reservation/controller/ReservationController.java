package com.challenge.studytime.domain.reservation.controller;

import com.challenge.studytime.domain.point.dto.responsedto.PointResponseDto;
import com.challenge.studytime.domain.reservation.dto.request.ReservationRequestDto;
import com.challenge.studytime.domain.reservation.dto.response.ReservationResponseDto;
import com.challenge.studytime.domain.reservation.service.ReservationService;
import com.challenge.studytime.global.exception.ErrorResponse;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Tag(name = "예약", description = "스터디 방을 예약해주는 기능")
@Slf4j
@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    @Operation(summary = "예약 기능", description = "스터디 방을 예약한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "예약 성공", content = @Content(schema = @Schema(implementation = ReservationResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "예약 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/room/{roomId}")
    public ReservationResponseDto registerReservation(
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto,
            @Parameter(name = "예약할 방 아이디", description = "특정 방 아이디를 통해 접근 가능하다.", in = ParameterIn.PATH)
            @PathVariable Long roomId,
            @Parameter(name = "생성할 예약의 내용", description = "예약하는 날짜, 시작 시간, 종료 시간", in = ParameterIn.PATH)
            @RequestBody @Valid ReservationRequestDto requestDto){
        return reservationService.registerReservation(userDto.getMemberId(), roomId, requestDto);
    }
    @Operation(summary = "예약 조회 기능", description = "모든 예약을 조회 예약한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "조회 성공", content = @Content(schema = @Schema(implementation = ReservationResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/search")
    public List<ReservationResponseDto> fullSearchStudyRoom(
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto){
        return reservationService.fullSearchReservation(userDto.getMemberId());
    }
    @Operation(summary = "사용자 예약 조회 기능", description = "사용자가 한 예약을 조회 예약한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "조회 성공", content = @Content(schema = @Schema(implementation = ReservationResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/room/{roomId}")
    public List<ReservationResponseDto> searchStudyRoom(
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto,
            @Parameter(name = "예약할 방 아이디", description = "특정 방 아이디를 통해 접근 가능하다.", in = ParameterIn.PATH)
            @PathVariable Long roomId){
        return reservationService.searchReservation(userDto.getMemberId(), roomId);
    }
    @Operation(summary = "사용자 예약 수정 기능", description = "사용자가 한 예약을 수정할 수 있다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "수정 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{reservationId}/room/{roomId}")
    public void modifyReservation(
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto,
            @Parameter(name = "예약 내역 아이디", description = "특정 예약 아이디를 통해 접근 가능하다.", in = ParameterIn.PATH)
            @PathVariable("reservationId") Long reservationId,
            @Parameter(name = "예약할 방 아이디", description = "특정 방 아이디를 통해 접근 가능하다.", in = ParameterIn.PATH)
            @PathVariable("roomId") Long roomId,
            @Parameter(name = "수정할 예약 내용", description = "예약하는 날짜, 시작 시간, 종료 시간", in = ParameterIn.PATH)
            @RequestBody @Valid ReservationRequestDto requestDto){
        reservationService.modifyReservation(reservationId, userDto.getMemberId(), roomId, requestDto);
    }
    @Operation(summary = "사용자 예약 삭제 기능", description = "사용자가 한 예약을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "삭제 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{reservationId}/room/{roomId}")
    public void deleteReservation(
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto,
            @Parameter(name = "예약 내역 아이디", description = "특정 예약 아이디를 통해 접근 가능하다.", in = ParameterIn.PATH)
            @PathVariable("reservationId") Long reservationId,
            @Parameter(name = "삭제할 방 아이디", description = "특정 방 아이디를 통해 접근 가능하다.", in = ParameterIn.PATH)
            @PathVariable("roomId") Long roomId){
        reservationService.deleteReservation(reservationId, userDto.getMemberId(), roomId);
    }
}