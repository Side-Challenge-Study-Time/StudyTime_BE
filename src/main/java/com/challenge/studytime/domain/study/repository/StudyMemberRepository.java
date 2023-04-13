package com.challenge.studytime.domain.study.repository;

import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.study.entity.Study;
import com.challenge.studytime.domain.study.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    Optional<StudyMember> findByMemberId(Long studyId);

    Optional<StudyMember> findByStudyAndMember(Study study, Member member);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT sm FROM StudyMember sm " +
            "JOIN FETCH sm.study s " +
            "JOIN FETCH sm.member m " +
            "WHERE s.id = :studyId")
    List<StudyMember> findAllWithStudyAndMemberByStudyId(@Param("studyId") Long studyId);
}
