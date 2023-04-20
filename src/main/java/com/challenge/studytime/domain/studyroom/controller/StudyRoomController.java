package com.challenge.studytime.domain.studyroom.controller;

import com.challenge.studytime.domain.reservation.dto.response.ReservationResponseDto;
import com.challenge.studytime.domain.studyroom.dto.request.StudyRoomModifyRequestDto;
import com.challenge.studytime.domain.studyroom.dto.request.StudyRoomRequestDto;
import com.challenge.studytime.domain.studyroom.dto.response.StudyRoomResponseDto;
import com.challenge.studytime.domain.studyroom.service.StudyRoomService;
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

@Tag(name = "스터디 방", description = "스터디 방을 생성해주는 기능")
@Slf4j
@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class StudyRoomController {
    private final StudyRoomService studyRoomService;
    @Operation(summary = "생성 기능", description = "스터디 방을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공", content = @Content(schema = @Schema(implementation = StudyRoomResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "생성 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public StudyRoomResponseDto registerStudyRoom(
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto,
            @Parameter(name = "생성할 방의 내용", description = "가격, 수용인원, 방 이름, 방 위치, 방 설명", in = ParameterIn.PATH)
            @RequestBody @Valid StudyRoomRequestDto requestDto){
        return studyRoomService.registerStudyRoom(userDto, requestDto);
    }
    @Operation(summary = "특정 방 조회 기능", description = "득정 스터디 방을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "조회 성공", content = @Content(schema = @Schema(implementation = StudyRoomResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{roomId}")
    public List<StudyRoomResponseDto> searchStudyRoom(
            @Parameter(name = "조회 할 방 아이디", description = "특정 방 아이디를 통해 접근 가능하다.", in = ParameterIn.PATH)
            @PathVariable Long roomId){
        return studyRoomService.detailStudyRoom(roomId);
    }
    @Operation(summary = "모든 방 조회 기능", description = "스터디 방을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "조회 성공", content = @Content(schema = @Schema(implementation = StudyRoomResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/search")
    public List<StudyRoomResponseDto> fullSearchStudyRoom(){
        return studyRoomService.fullSearchStudyRoom();
    }
    @Operation(summary = "특정 방 수정 기능", description = "특정 스터디 방을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "수정 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{roomId}")
    public void modifyStudyRoom(
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto,
            @Parameter(name = "수정 할 방 아이디", description = "특정 방 아이디를 통해 접근 가능하다.", in = ParameterIn.PATH)
            @PathVariable Long roomId,
            @Parameter(name = "수정 할 방 내용", description = "가격, 수용인원, 방 이름, 방 위치, 방 설명", in = ParameterIn.PATH)
            @RequestBody StudyRoomModifyRequestDto requestDto){
        studyRoomService.modifyStudyRoom(userDto.getMemberId(), roomId, requestDto);
    }
    @Operation(summary = "방 삭제 기능", description = "특정 스터디 방을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "삭제 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{roomId}")
    public void deleteStudyRoom(
            @Parameter(name = "삭제 할 방 아이디", description = "특정 방 아이디를 통해 접근 가능하다.", in = ParameterIn.PATH)
            @PathVariable Long roomId){
        studyRoomService.deleteStudyRoom(roomId);
    }
}