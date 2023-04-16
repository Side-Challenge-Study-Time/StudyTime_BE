package com.challenge.studytime.domain.point.service;

import com.challenge.studytime.domain.member.entity.Member;

import com.challenge.studytime.domain.member.repositry.MemberRepository;
import com.challenge.studytime.domain.point.dto.requestdto.PointRequestDto;
import com.challenge.studytime.domain.point.dto.responsedto.PointResponseDto;
import com.challenge.studytime.domain.point.entity.Point;
import com.challenge.studytime.domain.point.repositroy.PointRepository;
import com.challenge.studytime.global.exception.member.NotFoundMemberid;
import com.challenge.studytime.global.exception.point.NotFoundPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public PointResponseDto registerPoint(Long memberId,PointRequestDto requestDto){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberid(memberId));
        Point point = pointRepository.save(
                Point.builder()
                        .wallet(requestDto.getPoint())
                        .member(member)
                        .build()
        );
        point.setMemberFromPoint(member);
        return PointResponseDto.toDto(point, requestDto);
    }
    @Transactional
    public void chargePoint(Long memberId,PointRequestDto requestDto) {
        if (!pointRepository.existsByMemberId(memberId)){
            throw new NotFoundPoint();
        }
        Point point = pointRepository.findByMemberId(memberId);
        point.chargePoint(requestDto.getPoint());
    }
}
