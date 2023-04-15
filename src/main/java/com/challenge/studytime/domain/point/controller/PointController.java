package com.challenge.studytime.domain.point.controller;

import com.challenge.studytime.domain.point.dto.requestdto.PointRequestDto;
import com.challenge.studytime.domain.point.dto.responsedto.PointResponseDto;
import com.challenge.studytime.domain.point.service.PointService;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/point")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public PointResponseDto registerPoint(@IfLogin LoginUserDto userDto,
                                          @RequestBody PointRequestDto requestDto){
        return pointService.registerPoint(userDto.getMemberId(), requestDto);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("/charge")
    public void chargePoint(@IfLogin LoginUserDto userDto,
                            @RequestBody PointRequestDto requestDto){
        pointService.chargePoint(userDto.getMemberId(), requestDto);
    }
}
