package com.challenge.studytime.domain.study.controller;

import com.challenge.studytime.domain.member.dto.response.MemberLoginResponseDto;
import com.challenge.studytime.domain.study.dto.request.StudyModifyRequestDto;
import com.challenge.studytime.domain.study.dto.request.StudyRequestDto;
import com.challenge.studytime.domain.study.dto.request.StudySearchDto;
import com.challenge.studytime.domain.study.dto.response.StudyResponseDto;
import com.challenge.studytime.domain.study.dto.response.StudySearcResponseDto;
import com.challenge.studytime.domain.study.service.StudyService;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "스터디", description = "스터디 생성과 QueryDSL 검색 조회, 수정, 삭제")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StudyController {

    private final StudyService studyService;

    @Operation(summary = "스터디 생성", description = "회원이 스터디를 생성을 하여 ROLE_STUDY_LEADER를 흭득")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "스터디 생성 성공", content = @Content(schema = @Schema(implementation = StudyResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "스터디 생성 실패")
    })
    @PostMapping("/study/register")
    @ResponseStatus(HttpStatus.CREATED)
    public StudyResponseDto registerStudy(
            @Parameter(name = "로그인 회원 정보", description = "회원 아이디, 회원 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto,
            @Parameter(name = "스터디 정보 DTO", description = "스터디 제목, 내용, 참여 가능한 인원", in = ParameterIn.PATH)
            @RequestBody StudyRequestDto requestDto) {
        Long memberId = userDto.getMemberId();
        return studyService.registerStudyProject(memberId, requestDto);
    }

    @Operation(summary = "스터디 검색 및 특수 조회", description = "인덱스 쿼리 성능 튜닝을 하여 QueryDSL 조회 성능 개선")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = StudySearcResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "조회 실패")
    })
    @GetMapping("studies")
    @ResponseStatus(HttpStatus.OK)
    public Page<StudySearcResponseDto> validStudyFullsearch(
            @Parameter(name = "스터디 조회 조건", description = "스터디 이름, 스터디 내용, 참여 가능한 회원 범위에 대한 검색", in = ParameterIn.PATH)
            StudySearchDto requestDto,
            @Parameter(name = "페이지 번호", description = "기본적으로 0으로 설정을 하였고 필수적 X", in = ParameterIn.PATH)
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @Parameter(name = "페이지에 대한 글 개수", description = "기본적으로 3으로 설정을 하였고 필수X", in = ParameterIn.PATH)
            @RequestParam(value = "size", defaultValue = "3", required = false) int size
    ) {
        return studyService.fullSearch(requestDto, page, size);
    }

    @Operation(summary = "한 회원이 생성한 스터디 조회", description = "한 회원이 생성한 스터디를 조회를 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = StudyResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "조회 실패")
    })
    @GetMapping("/study/search")
    @ResponseStatus(HttpStatus.OK)
    public List<StudyResponseDto> detailStudy(
            @Parameter(name = "로그인 회원의 정보", description = "로그인 회원의 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto
    ) {
        Long memberId = userDto.getMemberId();
        return studyService.detailStudy(memberId);
    }

    @Operation(summary = "특정 스터디 삭제", description = "Flag 방식을 이용한 스터디 비활성화")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "삭제 실패")
    })
    @DeleteMapping("/studies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudy(
            @Parameter(name = "스터디 번호", description = "특정 스터디 번호", in = ParameterIn.PATH)
            @PathVariable Long id
    ) {
        studyService.deleteByStudy(id);
    }

    @Operation(summary = "특정 스터디에 대한 수정", description = "특정 회원이 스터디에 대한 내용을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공", content = @Content(schema = @Schema(implementation = StudyResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "수정 실패")
    })
    @PatchMapping("/studies/{studyId}")
    @ResponseStatus(HttpStatus.OK)
    public StudyResponseDto modifyStudy(
            @Parameter(name = "스터디 번호", description = "특정 스터디 번호", in = ParameterIn.PATH)
            @PathVariable Long studyId,
            @Parameter(name = "스터디 변경 정보 DTO", description = "변경 스터디 제목, 내용, 참가 가능한 인원", in = ParameterIn.PATH)
            @Valid @RequestBody StudyModifyRequestDto studyModifyRequestDto
    ) {
        return studyService.modifyById(studyId, studyModifyRequestDto);
    }

}
