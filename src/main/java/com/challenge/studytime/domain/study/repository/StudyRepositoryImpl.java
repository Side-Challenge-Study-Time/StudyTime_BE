package com.challenge.studytime.domain.study.repository;

import com.challenge.studytime.domain.member.entity.QMember;
import com.challenge.studytime.domain.study.dto.request.StudySearchDto;
import com.challenge.studytime.domain.study.dto.response.QStudySearcResponseDto;
import com.challenge.studytime.domain.study.dto.response.StudySearcResponseDto;
import com.challenge.studytime.domain.study.entity.QStudy;
import com.challenge.studytime.domain.study.entity.Study;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.challenge.studytime.domain.member.entity.QMember.*;
import static com.challenge.studytime.domain.study.entity.QStudy.*;


public class StudyRepositoryImpl implements StudyRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public StudyRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<StudySearcResponseDto> fullSrchWithStudy(StudySearchDto requestDto, Pageable pageable) {

        List<StudySearcResponseDto> content = queryFactory
                .select(new QStudySearcResponseDto(
                        member.id.as("memberId"),
                        member.name.as("memberName"),
                        study.title,
                        study.content,
                        study.membersCount
                )).from(study)
                .leftJoin(study.member, member)
                .where(
                        studyTitleEq(requestDto.getStudyTitle()),
                        studyContentEq(requestDto.getStudyContent()),
                        membersCountGoeEq(requestDto.getMembersCountGoe()),
                        membersCountsLoeEq(requestDto.getMembersCountsLoe()),
                        study.deleteStudy.eq(false)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(member.id.asc())
                .fetch();

        JPAQuery<Study> countQuery = queryFactory
                .selectFrom(study)
                .leftJoin(study.member, member)
                .where(
                        studyTitleEq(requestDto.getStudyTitle()),
                        studyContentEq(requestDto.getStudyContent()),
                        membersCountGoeEq(requestDto.getMembersCountGoe()),
                        membersCountsLoeEq(requestDto.getMembersCountsLoe()),
                        study.deleteStudy.eq(false)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression membersCountsLoeEq(Integer membersCountsLoe) {
        return membersCountsLoe != null ? study.membersCount.loe(membersCountsLoe) : null;
    }

    private BooleanExpression membersCountGoeEq(Integer membersCountGoe) {
        return membersCountGoe != null ? study.membersCount.goe(membersCountGoe) : null;
    }

    private BooleanExpression studyContentEq(String studyContent) {
        return StringUtils.hasText(studyContent) ? study.content.eq(studyContent) : null;
    }

    private BooleanExpression studyTitleEq(String studyTitle) {
        return StringUtils.hasText(studyTitle) ? study.title.eq(studyTitle) : null;
    }
}
