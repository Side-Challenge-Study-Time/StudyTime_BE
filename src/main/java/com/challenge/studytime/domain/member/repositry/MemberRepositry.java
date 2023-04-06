package com.challenge.studytime.domain.member.repositry;

import com.challenge.studytime.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepositry extends JpaRepository<Member,Long> {
}
