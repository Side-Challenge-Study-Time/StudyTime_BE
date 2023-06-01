package com.challenge.studytime.domain.study.controller;

import com.challenge.studytime.domain.study.dto.request.StudyModifyRequestDto;
import com.challenge.studytime.domain.study.dto.request.StudyRequestDto;
import com.challenge.studytime.domain.study.dto.request.StudySearchDto;
import com.challenge.studytime.domain.study.dto.response.MemberStudyMemberDto;
import com.challenge.studytime.domain.study.dto.response.StudyResponseDto;
import com.challenge.studytime.domain.study.dto.response.StudySearcResponseDto;
import com.challenge.studytime.domain.study.service.StudyService;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StudyController {

    private final StudyService studyService;

    @PostMapping("/study/register")
    @ResponseStatus(HttpStatus.CREATED)
    public StudyResponseDto registerStudy(
            @IfLogin LoginUserDto userDto,
            @RequestBody StudyRequestDto requestDto) {
        Long memberId = userDto.getMemberId();
        return studyService.registerStudyProject(memberId, requestDto);
    }

    @GetMapping("studies")
    @ResponseStatus(HttpStatus.OK)
    public Page<StudySearcResponseDto> validStudyFullsearch(
            StudySearchDto requestDto,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "3", required = false) int size
    ) {
        return studyService.fullSearch(requestDto, page, size);
    }


    @GetMapping("/study/search")
    @ResponseStatus(HttpStatus.OK)
    public List<MemberStudyMemberDto> detailStudy(
            @IfLogin LoginUserDto userDto
    ) {
        Long memberId = userDto.getMemberId();
        return studyService.detailStudy(memberId);
    }

    @DeleteMapping("/studies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudy(
            @PathVariable Long id
    ) {
        studyService.deleteByStudy(id);
    }

    @PatchMapping("/studies/{studyId}")
    @ResponseStatus(HttpStatus.OK)
    public StudyResponseDto modifyStudy(
            @PathVariable Long studyId,
            @Valid @RequestBody StudyModifyRequestDto studyModifyRequestDto
    ) {
        return studyService.modifyById(studyId, studyModifyRequestDto);
    }

}
