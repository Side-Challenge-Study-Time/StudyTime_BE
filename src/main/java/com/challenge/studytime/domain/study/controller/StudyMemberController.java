package com.challenge.studytime.domain.study.controller;

import com.challenge.studytime.domain.study.dto.response.StudyMemberResponse;
import com.challenge.studytime.domain.study.dto.response.StudyResponseDto;
import com.challenge.studytime.domain.study.service.StudyMemberService;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "스터디 참여 인원", description = "생성 스터디를 참여")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study")
public class StudyMemberController {

    private final StudyMemberService studyMemberService;


    @Operation(summary = "생성 스터디에 대한 참가", description = "특정 스터디에 대한 참가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "스터디 참가 성공", content = @Content(schema = @Schema(implementation = StudyResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "스터디 참가 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/member/{studyId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @Parameter(name = "참가 스터디 ID", description = "생성 스터디 참가 ID", in = ParameterIn.PATH)
            @PathVariable Long studyId,
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto
    ) {
        studyMemberService.create(studyId, userDto);
    }

    @Operation(summary = "모든 참가 인원 조회", description = "스터디 참가한 회원을 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = StudyMemberResponse.class))),
            @ApiResponse(responseCode = "400", description = "조히 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/members")
    @ResponseStatus(HttpStatus.OK)
    public List<StudyMemberResponse> findFullSrchStudyMember() {
        return studyMemberService.searchStudyMemberFullSrch();
    }
}
