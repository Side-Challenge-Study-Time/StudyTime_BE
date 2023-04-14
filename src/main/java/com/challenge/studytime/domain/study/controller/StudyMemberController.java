package com.challenge.studytime.domain.study.controller;

import com.challenge.studytime.domain.study.service.StudyMemberService;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study/member")
public class StudyMemberController {

    private final StudyMemberService studyMemberService;

    @PostMapping("{studyId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @PathVariable Long studyId,
            @IfLogin LoginUserDto userDto
    ) {
        studyMemberService.create(studyId, userDto);
    }
}
