package com.challenge.studytime.domain.comment.repository;

import com.challenge.studytime.domain.comment.dto.response.CommentDto;
import com.challenge.studytime.domain.comment.entity.QComment;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<CommentDto> getParentCommentsWithReplies() {
        QComment parent = new QComment("parent");

        QComment child = new QComment("child");

        return queryFactory
                .select(Projections.constructor(CommentDto.class,
                        parent.id,
                        parent.content,
                        Expressions.as(parent.parent.id, "parentId")
                ))
                .from(parent)
                .leftJoin(parent.replies, child)
                .orderBy(parent.id.asc().nullsFirst(), child.id.asc().nullsFirst())
                .fetch();
    }
}
