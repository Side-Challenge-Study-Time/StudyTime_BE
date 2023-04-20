package com.challenge.studytime.domain.image.controller;

import com.challenge.studytime.domain.image.dto.response.ImageResponseDto;
import com.challenge.studytime.domain.image.service.StorageService;
import com.challenge.studytime.domain.study.entity.Study;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study")
public class StorageController {

    final private StorageService storageService;

    @PostMapping("/image/{studyId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> uploadImage(
            @RequestParam("image") MultipartFile file
            , @PathVariable Long studyId) throws IOException {
        String uploadImage = storageService.uploadImage(file, studyId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }


    @GetMapping("/image/{fileName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> downloadImage(@PathVariable("fileName") String fileName) {
        byte[] downloadImage = storageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(downloadImage);
    }

}