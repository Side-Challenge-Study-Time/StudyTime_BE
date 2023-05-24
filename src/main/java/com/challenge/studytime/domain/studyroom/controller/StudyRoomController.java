package com.challenge.studytime.domain.studyroom.controller;

import com.challenge.studytime.domain.studyroom.dto.request.StudyRoomModifyRequestDto;
import com.challenge.studytime.domain.studyroom.dto.request.StudyRoomRequestDto;
import com.challenge.studytime.domain.studyroom.dto.response.StudyRoomResponseDto;
import com.challenge.studytime.domain.studyroom.service.StudyRoomService;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class StudyRoomController {
    private final StudyRoomService studyRoomService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public StudyRoomResponseDto registerStudyRoom(@IfLogin LoginUserDto userDto,
                                                  @RequestBody @Valid StudyRoomRequestDto requestDto){
        return studyRoomService.registerStudyRoom(userDto, requestDto);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{roomId}")
    public List<StudyRoomResponseDto> searchStudyRoom(@PathVariable Long roomId){
        return studyRoomService.detailStudyRoom(roomId);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/search")
    public List<StudyRoomResponseDto> fullSearchStudyRoom(){
        return studyRoomService.fullSearchStudyRoom();
    }
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{roomId}")
    public void modifyStudyRoom(@IfLogin LoginUserDto userDto,
                                @PathVariable Long roomId,
                                @RequestBody StudyRoomModifyRequestDto requestDto){
        studyRoomService.modifyStudyRoom(userDto.getMemberId(), roomId, requestDto);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{roomId}")
    public void deleteStudyRoom(@PathVariable Long roomId){
        studyRoomService.deleteStudyRoom(roomId);
    }
}
