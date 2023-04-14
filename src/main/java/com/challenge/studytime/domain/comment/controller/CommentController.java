package com.challenge.studytime.domain.comment.controller;

import com.challenge.studytime.domain.comment.dto.response.CommentDto;
import com.challenge.studytime.domain.comment.entity.Comment;
import com.challenge.studytime.domain.comment.repository.CommentRepository;
import com.challenge.studytime.domain.comment.service.CommentService;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {


    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("{studyId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(
            @RequestBody String content,
            @IfLogin LoginUserDto userDto,
            @PathVariable Long studyId
    ) {
        return commentService.createParentComment(content, userDto, studyId);
    }

    @PostMapping("/reply/{parentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create2(
            @PathVariable Long parentId,
            @RequestBody String content,
            @IfLogin LoginUserDto userDto
    ) {
        return commentService.createReplyComment(parentId, content, userDto);
    }

    @GetMapping("/fullSrchReply")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getParentCommentsWithReplies() {
        return commentService.fullSrchWithComment();
    }

    @DeleteMapping("{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @PathVariable Long commentId
    ) {
        commentRepository.deleteById(commentId);
    }
}
