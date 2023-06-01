package com.challenge.studytime.domain.study.repository;

import com.challenge.studytime.domain.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long>, StudyRepositoryCustom {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Study s where s.id=:id AND s.deleteStudy=false ")
    Optional<Study> findByIdAndDeleteStudyFalse(@Param("id") Long id);

//        @Query("select s from Study s where s.member.id=:memberId")
@Query("SELECT DISTINCT s FROM Study s " +
        "JOIN FETCH s.member m " +
        "JOIN FETCH s.studyMembers sm " +
        "WHERE m.id = :memberId")
    List<Study> findCustomId(@Param("memberId") Long memberId);
}
