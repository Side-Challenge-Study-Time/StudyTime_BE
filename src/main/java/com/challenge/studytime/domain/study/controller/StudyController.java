package com.challenge.studytime.domain.study.controller;

import com.challenge.studytime.domain.study.dto.StudyRequestDto;
import com.challenge.studytime.domain.study.dto.StudyResponseDto;
import com.challenge.studytime.domain.study.service.StudyService;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study")
public class StudyController {

    private final StudyService studyService;

    @PostMapping("/register")
    public StudyResponseDto registerStudy(@IfLogin LoginUserDto userDto, @RequestBody StudyRequestDto requestDto) {
        return studyService.registerStudyProject(userDto,requestDto);
    }

}
