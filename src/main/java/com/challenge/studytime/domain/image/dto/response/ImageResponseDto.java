package com.challenge.studytime.domain.image.dto.response;

import com.challenge.studytime.domain.image.entity.ImageData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponseDto {
    private String name;
    private String type;

    public static ImageResponseDto toDto(ImageData imageData) {
        return ImageResponseDto.builder()
                .name(imageData.getName())
                .type(imageData.getType())
                .build();
    }
}
