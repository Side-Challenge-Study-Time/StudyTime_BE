package com.challenge.studytime.domain.study.repository;

import com.challenge.studytime.domain.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long>,StudyRepositoryCustom {
}
