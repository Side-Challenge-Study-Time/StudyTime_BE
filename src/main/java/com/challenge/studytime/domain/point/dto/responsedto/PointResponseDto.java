package com.challenge.studytime.domain.point.dto.responsedto;

import com.challenge.studytime.domain.point.dto.requestdto.PointRequestDto;
import com.challenge.studytime.domain.point.entity.Point;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointResponseDto {
    private int point;
    private Long memberId;
    public static PointResponseDto toDto(Point point, PointRequestDto requestDto){
        return PointResponseDto.builder()
                .point(requestDto.getPoint())
                .memberId(point.getId())
                .build();
    }
}
