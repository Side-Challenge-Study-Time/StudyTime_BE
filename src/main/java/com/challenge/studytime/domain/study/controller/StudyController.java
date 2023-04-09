package com.challenge.studytime.domain.study.controller;

import com.challenge.studytime.domain.study.dto.request.StudyModifyRequestDto;
import com.challenge.studytime.domain.study.dto.request.StudyRequestDto;
import com.challenge.studytime.domain.study.dto.request.StudySearchDto;
import com.challenge.studytime.domain.study.dto.response.StudyResponseDto;
import com.challenge.studytime.domain.study.dto.response.StudySearcResponseDto;
import com.challenge.studytime.domain.study.service.StudyService;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study")
public class StudyController {

    private final StudyService studyService;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public StudyResponseDto registerStudy(@IfLogin LoginUserDto userDto, @RequestBody StudyRequestDto requestDto) {
        return studyService.registerStudyProject(userDto, requestDto);
    }

    @GetMapping("fullSrch")
    @ResponseStatus(HttpStatus.OK)
    public Page<StudySearcResponseDto> validStudyFullsearch(
            StudySearchDto requestDto,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "3", required = false) int size
    ) {
        return studyService.fullSearch(requestDto, page, size);
    }

    @GetMapping("detail/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudyResponseDto detailStudy(@PathVariable Long id) {
        return studyService.detailStudy(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudy(@PathVariable Long id) {
        studyService.deleteByStudy(id);
    }

    @PatchMapping("modify/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudyResponseDto modifyStudy(@PathVariable Long id, @RequestBody StudyModifyRequestDto studyModifyRequestDto) {
        return studyService.modifyById(id, studyModifyRequestDto);
    }

}
