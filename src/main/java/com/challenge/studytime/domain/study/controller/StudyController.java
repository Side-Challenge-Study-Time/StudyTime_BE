package com.challenge.studytime.domain.study.controller;

import com.challenge.studytime.domain.study.dto.request.StudyRequestDto;
import com.challenge.studytime.domain.study.dto.request.StudySearchDto;
import com.challenge.studytime.domain.study.dto.response.StudyResponseDto;
import com.challenge.studytime.domain.study.dto.response.StudySearcResponseDto;
import com.challenge.studytime.domain.study.service.StudyService;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study")
public class StudyController {

    private final StudyService studyService;

    @PostMapping("register")
    public StudyResponseDto registerStudy(@IfLogin LoginUserDto userDto, @RequestBody StudyRequestDto requestDto) {
        return studyService.registerStudyProject(userDto, requestDto);
    }

    @GetMapping("fullSrch")
    public Page<StudySearcResponseDto> validStudyFullsearch(
            StudySearchDto requestDto,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "3", required = false) int size
    ) {
        return studyService.fullSearch(requestDto, page, size);
    }

}
