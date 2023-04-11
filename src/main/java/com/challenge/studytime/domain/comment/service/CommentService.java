package com.challenge.studytime.domain.comment.service;

import com.challenge.studytime.domain.comment.dto.response.CommentDto;
import com.challenge.studytime.domain.comment.entity.Comment;
import com.challenge.studytime.domain.comment.repository.CommentRepository;
import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepositry;
import com.challenge.studytime.domain.study.entity.Study;
import com.challenge.studytime.domain.study.repository.StudyRepository;
import com.challenge.studytime.global.exception.comment.NotFoundCommentWithParentId;
import com.challenge.studytime.global.exception.member.NotFoundMemberid;
import com.challenge.studytime.global.exception.study.NotFoundStudyWithId;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepositry memberRepositry;
    private final StudyRepository studyRepository;

    @Transactional
    public CommentDto createParentComment(String content, LoginUserDto userDto, Long studyId) {
        Comment comment = Comment.builder()
                .content(content)
                .build();

        Member member = memberRepositry.findById(userDto.getMemberId())
                .orElseThrow(() -> new NotFoundMemberid(userDto.getMemberId()));

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new NotFoundStudyWithId(studyId));

        comment.addCommentWithMember(member);
        comment.addCommentWithStudy(study);

        return CommentDto.toDto(commentRepository.save(comment));
    }

    @Transactional
    public CommentDto createReplyComment(Long parentId, String content, LoginUserDto userDto) {
        Comment parentComment = commentRepository.findById(parentId)
                .orElseThrow(() -> new NotFoundCommentWithParentId(parentId));

        Comment replyComment = Comment.builder()
                .content(content)
                .parent(parentComment)
                .build();

        Member member = memberRepositry.findById(userDto.getMemberId())
                .orElseThrow(() -> new NotFoundMemberid(userDto.getMemberId()));

        parentComment.addCommentWithMember(member);

        parentComment.getReplies().add(replyComment);

        return CommentDto.toDto(commentRepository.save(replyComment));
    }

    @Transactional(readOnly = true)
    public List<CommentDto> fullSrchWithComment() {
        return commentRepository.getParentCommentsWithReplies();
    }
}
