package com.challenge.studytime.domain.studyroom.repository;

import com.challenge.studytime.domain.studyroom.entity.StudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRoomRepository extends JpaRepository<StudyRoom,Long> {
    boolean existsByIdAndMemberId(Long roomId, Long memberId);
    StudyRoom findByIdAndMemberId(Long roomId, Long memberId);
    List<StudyRoom> findAllById(Long roomId);
}
