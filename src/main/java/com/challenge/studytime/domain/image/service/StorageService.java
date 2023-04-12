package com.challenge.studytime.domain.image.service;

import com.challenge.studytime.domain.image.dto.response.ImageResponseDto;
import com.challenge.studytime.domain.image.entity.ImageData;
import com.challenge.studytime.domain.image.repository.StorageRepository;
import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepositry;
import com.challenge.studytime.domain.study.entity.Study;
import com.challenge.studytime.domain.study.repository.StudyRepository;
import com.challenge.studytime.global.exception.study.NotFoundStudyWithId;
import com.challenge.studytime.global.util.ImageUtils;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageService {

    private final StorageRepository storageRepository;
    private final StudyRepository studyRepository;

    @Transactional
    public String uploadImage(MultipartFile file, Long studyId) throws IOException {
        log.info("upload file: {}", file);
        ImageData imageData = storageRepository.save(
                ImageData.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .imageData(ImageUtils.compressImage(file.getBytes()))
                        .build());
        if (imageData != null) {
            log.info("imageData: {}", imageData);

            Study study = studyRepository.findById(studyId)
                    .orElseThrow(() -> new NotFoundStudyWithId(studyId));

            imageData.setStudy(study);
            return "file uploaded successfully : " + file.getOriginalFilename();
        }

        return null;
    }


    @Transactional
    public byte[] downloadImage(String fileName) {
        ImageData imageData = storageRepository.findByName(fileName)
                .orElseThrow(RuntimeException::new);

        log.info("download imageData: {}", imageData);

        return ImageUtils.decompressImage(imageData.getImageData());
    }
}

