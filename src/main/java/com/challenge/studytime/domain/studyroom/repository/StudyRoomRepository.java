package com.challenge.studytime.domain.studyroom.repository;

import com.challenge.studytime.domain.studyroom.entity.StudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRoomRepository extends JpaRepository<StudyRoom,Long> {
}
