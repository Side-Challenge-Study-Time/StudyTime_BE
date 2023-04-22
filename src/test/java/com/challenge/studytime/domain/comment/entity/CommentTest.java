package com.challenge.studytime.domain.comment.entity;

import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.study.entity.Study;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommentTest {

    @DisplayName("addCommentWithMember 메소드 테스트")
    @Test
    void addCommentWithMemberTest() {
        // given
        Comment comment = Comment.builder()
                .content("content")
                .build();

        Member member = Member.builder()
                .name("memberName")
                .build();

        // when
        comment.addCommentWithMember(member);

        // then
        assertThat(comment.getMember()).isEqualTo(member);
        assertThat(member.getComments()).contains(comment);
    }

    @DisplayName("addCommentWithStudy 메소드 테스트")
    @Test
    void addCommentWithStudyTest() {
        // given
        Comment comment = Comment.builder()
                .content("content")
                .build();

        Study study = Study.builder()
                .title("studyName")
                .build();

        // when
        comment.addCommentWithStudy(study);

        // then
        assertThat(comment.getStudy()).isEqualTo(study);
        assertThat(study.getComments()).contains(comment);
    }
}