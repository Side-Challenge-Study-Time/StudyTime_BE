package com.challenge.studytime.domain.comment.repository;

import com.challenge.studytime.domain.comment.dto.response.CommentDto;

import java.util.List;

public interface CommentRepositoryCustom {
    public List<CommentDto> getParentCommentsWithReplies();
}
