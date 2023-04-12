package com.challenge.studytime.domain.studyroom.repository;

import com.challenge.studytime.domain.studyroom.entity.StudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRoomRepository extends JpaRepository<StudyRoom,Long> {
    boolean existsByIdAndMemberId(Long roomId, Long memberId);
    StudyRoom findByIdAndMemberId(Long roomId, Long memberId);
}
