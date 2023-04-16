package com.challenge.studytime.domain.study.controller;

import com.challenge.studytime.domain.study.dto.response.StudyMemberResponse;
import com.challenge.studytime.domain.study.entity.StudyMember;
import com.challenge.studytime.domain.study.service.StudyMemberService;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/fullSrch")
    @ResponseStatus(HttpStatus.OK)
    public List<StudyMemberResponse> findFullSrchStudyMember() {
        return studyMemberService.searchStudyMemberFullSrch();
    }
}
