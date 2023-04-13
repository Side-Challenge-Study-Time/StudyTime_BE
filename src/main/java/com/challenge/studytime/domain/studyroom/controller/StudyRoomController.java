package com.challenge.studytime.domain.studyroom.controller;

import com.challenge.studytime.domain.studyroom.dto.request.StudyRoomModifyRequestDto;
import com.challenge.studytime.domain.studyroom.dto.request.StudyRoomRequestDto;
import com.challenge.studytime.domain.studyroom.dto.response.StudyRoomResponseDto;
import com.challenge.studytime.domain.studyroom.service.StudyRoomService;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/studyRoom")
@RequiredArgsConstructor
public class StudyRoomController {
    private final StudyRoomService studyRoomService;
    @PostMapping("/register")
    public StudyRoomResponseDto registerStudyRoom(@IfLogin LoginUserDto userDto,
                                                  @RequestBody @Valid StudyRoomRequestDto requestDto){
        return studyRoomService.registerStudyRoom(userDto, requestDto);
    }
    @GetMapping("/search/{roomId}")
    public List<StudyRoomResponseDto> searchStudyRoom(@PathVariable Long roomId){
        return studyRoomService.detailStudyRoom(roomId);
    }
    @GetMapping("/fullSearch")
    public List<StudyRoomResponseDto> fullSearchStudyRoom(){
        return studyRoomService.fullSearchStudyRoom();
    }
    @PatchMapping("/modify/{roomId}")
    public void modifyStudyRoom(@IfLogin LoginUserDto userDto,
                                @PathVariable Long roomId,
                                @RequestBody StudyRoomModifyRequestDto requestDto){
        studyRoomService.modifyStudyRoom(userDto.getMemberId(), roomId, requestDto);
    }
    @DeleteMapping("/delete/{roomId}")
    public void deleteStudyRoom(@PathVariable Long roomId){
        studyRoomService.deleteStudyRoom(roomId);
    }
}
