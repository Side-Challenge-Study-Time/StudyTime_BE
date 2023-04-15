package com.challenge.studytime.domain.point.repositroy;

import com.challenge.studytime.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
    Point findByMemberId(Long memberId);
    boolean existsByMemberId(Long memberId);
}
