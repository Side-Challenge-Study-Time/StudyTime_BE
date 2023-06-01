package com.challenge.studytime.domain.member.repositry;

import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT DISTINCT m FROM Member m " +
            "JOIN FETCH m.studyList s " +
            "WHERE m.id = :memberId")
    List<Member> findCustomId(@Param("memberId") Long memberId);
}
