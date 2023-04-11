package com.challenge.studytime.global.exception.comment;

public class NotFoundCommentWithParentId extends RuntimeException{
    public NotFoundCommentWithParentId(Long commentId) {
        super("Not Found Comment With ParentId : " + commentId);
    }
}
