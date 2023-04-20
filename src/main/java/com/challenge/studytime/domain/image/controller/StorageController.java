package com.challenge.studytime.domain.image.controller;

import com.challenge.studytime.domain.image.dto.response.ImageResponseDto;
import com.challenge.studytime.domain.image.service.StorageService;
import com.challenge.studytime.domain.member.dto.response.MemberLoginResponseDto;
import com.challenge.studytime.domain.study.entity.Study;
import com.challenge.studytime.global.exception.ErrorResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "스터디에 대한 이미지", description = "이미지를 Byte 코드로 변환하여 DB에 저장하는 방식")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study")
public class StorageController {

    final private StorageService storageService;

    @Operation(summary = "이미지 생성", description = "이미지 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공",content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "생성 실패",content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/image/{studyId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> uploadImage(
            @Parameter(name = "이미지",description = "MultipartFile 이미지", in = ParameterIn.PATH)
            @RequestParam("image") MultipartFile file,
            @Parameter(name = "스터디에 대한 아이디",description = "studyId", in = ParameterIn.PATH)
             @PathVariable Long studyId) throws IOException {
        String uploadImage = storageService.uploadImage(file, studyId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }


    @Operation(summary = "이미지 찾기", description = "이미지 찾기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "조회 실패",content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/image/{fileName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> downloadImage(
            @Parameter(name = "찾고 싶은 이미지",description = "fileName", in = ParameterIn.PATH)
            @PathVariable("fileName") String fileName
    ) {
        byte[] downloadImage = storageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(downloadImage);
    }

}