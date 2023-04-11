package com.challenge.studytime.domain.comment.dto.response;

import com.challenge.studytime.domain.comment.entity.Comment;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String content;
    private Long parentId;

    @QueryProjection
    public CommentDto(Long id, String content, Long parentId) {
        this.id = id;
        this.content = content;
        this.parentId = parentId;
    }

    public static CommentDto toDto(Comment comment) {
        Long parentId = comment.getParent() != null && comment.getParent().getId() != null ? comment.getParent().getId() : 99L;

        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .parentId(parentId)
                .build();
    }
}
