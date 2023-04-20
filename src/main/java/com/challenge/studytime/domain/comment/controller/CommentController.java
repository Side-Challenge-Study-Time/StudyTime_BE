package com.challenge.studytime.domain.comment.controller;

import com.challenge.studytime.domain.comment.dto.response.CommentDto;
import com.challenge.studytime.domain.comment.entity.Comment;
import com.challenge.studytime.domain.comment.repository.CommentRepository;
import com.challenge.studytime.domain.comment.service.CommentService;
import com.challenge.studytime.domain.study.dto.response.StudyResponseDto;
import com.challenge.studytime.global.exception.ErrorResponse;
import com.challenge.studytime.global.util.IfLogin;
import com.challenge.studytime.global.util.LoginUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "스터디에 대한 댓글", description = "특정 스터디에 댓글 기능")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study")
public class CommentController {


    private final CommentService commentService;
    private final CommentRepository commentRepository;


    @Operation(summary = "특정 스터디에 최초 댓글 작성", description = "최초의 댓글을 작성하며 ParentId가 0으로 설정이 된다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공", content = @Content(schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400", description = "생성 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{studyId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(
            @Parameter(name = "댓글 내용", description = "댓글을 작성하고 싶은 내용", in = ParameterIn.PATH)
            @RequestBody String content,
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto,
            @Parameter(name = "스터디 아이디", description = "특정 스터디 아이디를 통해 접근 가능", in = ParameterIn.PATH)
            @PathVariable Long studyId
    ) {
        return commentService.createParentComment(content, userDto, studyId);
    }


    @Operation(summary = "댓글에 대댓글 작성 기능", description = "특정 스터디의 최초 댓글에 대댓글을 작성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공", content = @Content(schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400", description = "셍성 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/comment/{parentId}/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createCommentReply(
            @Parameter(name = "최초 댓글 및 부모 아이디", description = "대댓글을 작성하기 위한 아이디", in = ParameterIn.PATH)
            @PathVariable Long parentId,
            @Parameter(name = "댓글 내용", description = "댓글을 작성하고 싶은 내용", in = ParameterIn.PATH)
            @RequestBody String content,
            @Parameter(name = "로그인 회원의 정보", description = "로그인한 회원의 정보, 아이디, 권한", in = ParameterIn.PATH)
            @IfLogin LoginUserDto userDto
    ) {
        return commentService.createReplyComment(parentId, content, userDto);
    }


    @Operation(summary = "전체 댓글을 조회", description = "전체 댓글을 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/comments/search")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getParentCommentsWithReplies() {
        return commentService.fullSrchWithComment();
    }


    @Operation(summary = "특정 댓글 삭제 기능", description = "특정 댓글에 대한 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공", content = @Content(schema = @Schema(implementation = StudyResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "삭제 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/comment/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @Parameter(name = "댓글 아이디", description = "삭제를 원하는 댓글 아이디", in = ParameterIn.PATH)
            @PathVariable Long commentId
    ) {
        commentRepository.deleteById(commentId);
    }
}
