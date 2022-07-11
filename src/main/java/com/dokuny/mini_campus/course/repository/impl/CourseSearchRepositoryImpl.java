package com.dokuny.mini_campus.course.repository.impl;

import com.dokuny.mini_campus.admin.dto.MemberListDto;
import com.dokuny.mini_campus.admin.entity.QCategory;
import com.dokuny.mini_campus.course.dto.CourseListDto;
import com.dokuny.mini_campus.course.entity.QCourse;
import com.dokuny.mini_campus.course.repository.CourseSearchRepository;
import com.dokuny.mini_campus.course.repository.cond.CourseSearchCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.dokuny.mini_campus.admin.entity.QCategory.*;
import static com.dokuny.mini_campus.course.entity.QCourse.*;
import static com.dokuny.mini_campus.member.entity.QMember.member;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class CourseSearchRepositoryImpl implements CourseSearchRepository {

    private final JPAQueryFactory queryFactory;

    public CourseSearchRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<CourseListDto> search(CourseSearchCondition cond, Pageable pageable) {

        List<CourseListDto> content = queryFactory.
                select(Projections.bean(CourseListDto.class,
                        course.id,
                        course.subject,
                        course.createdAt.as("registeredAt"),
                        category.name.as("categoryName")))
                .from(course)
                .join(course.category,category)
                .where(subjectContains(cond.getSubject()),
                        categoryContains(cond.getCategoryName()))
                .orderBy(course.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(course.count())
                .from(course)
                .where(subjectContains(cond.getSubject()));

        return PageableExecutionUtils.getPage(content,pageable,countQuery::fetchOne);
    }

    private BooleanExpression subjectContains(String subject) {
        return hasText(subject)? course.subject.contains(subject) : null;
    }

    private BooleanExpression categoryContains(String categoryName) {
        return hasText(categoryName)? category.name.contains(categoryName) : null;
    }


}