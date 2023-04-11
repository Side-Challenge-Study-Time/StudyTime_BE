package com.challenge.studytime.domain.study.repository;

import com.challenge.studytime.domain.study.dto.request.StudyRequestDto;
import com.challenge.studytime.domain.study.dto.request.StudySearchDto;
import com.challenge.studytime.domain.study.dto.response.StudySearcResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudyRepositoryCustom {
    Page<StudySearcResponseDto> fullSrchWithStudy(StudySearchDto requestDto, Pageable pageable);
}
