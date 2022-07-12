package com.dokuny.mini_campus.course.service.impl;

import com.dokuny.mini_campus.admin.entity.Category;
import com.dokuny.mini_campus.admin.exception.CategoryNotExistException;
import com.dokuny.mini_campus.admin.repository.CategoryRepository;
import com.dokuny.mini_campus.commons.dto.SearchInput;
import com.dokuny.mini_campus.commons.dto.ResponseResult;
import com.dokuny.mini_campus.course.dto.CourseInput;
import com.dokuny.mini_campus.course.dto.CourseDto;
import com.dokuny.mini_campus.course.dto.CourseListDto;
import com.dokuny.mini_campus.course.dto.TakeCourseInput;
import com.dokuny.mini_campus.course.entity.Course;
import com.dokuny.mini_campus.course.entity.TakeCourse;
import com.dokuny.mini_campus.course.exception.CourseNotExistException;
import com.dokuny.mini_campus.course.exception.TakeCourseAlreadyExistException;
import com.dokuny.mini_campus.course.repository.CourseRepository;
import com.dokuny.mini_campus.course.repository.TakeCourseRepository;
import com.dokuny.mini_campus.course.repository.cond.CourseSearchCondition;
import com.dokuny.mini_campus.course.service.CourseService;
import com.dokuny.mini_campus.course.type.TakeCourseStatus;
import com.dokuny.mini_campus.member.entity.Member;
import com.dokuny.mini_campus.member.exception.MemberException;
import com.dokuny.mini_campus.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    private final TakeCourseRepository takeCourseRepository;
    @Transactional
    @Override
    public boolean add(CourseInput input) {

        Category category = categoryRepository.findById(input.getCategoryId())
                .orElseThrow(() -> new CategoryNotExistException("존재하지 않는 카테고리입니다."));


        courseRepository.save(Course.builder()
                .subject(input.getSubject())
                .category(category)
                .contents(input.getContents())
                .imagePath(input.getImagePath())
                .price(input.getPrice())
                .salePrice(input.getSalePrice())
                .saleEndAt(LocalDateTime.parse(input.getSaleEndAt()))
                .keyword(input.getKeyword())
                .summary(input.getSummary())
                .build());

        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CourseListDto> list(SearchInput input, Pageable pageable) {
        return courseRepository.search(CourseSearchCondition.of(input), pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public CourseDto find(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotExistException("존재하지 않는 강의입니다."));
        return CourseDto.of(course);
    }

    @Transactional
    @Override
    public boolean edit(CourseInput input) {
        Course course = courseRepository.findById(input.getId())
                .orElseThrow(() -> new CourseNotExistException("존재하지 않는 강의입니다."));

        Category category = categoryRepository.findById(input.getCategoryId())
                .orElseThrow(() -> new CategoryNotExistException("존재하지 않는 카테고리입니다."));

        course.setCategory(category);
        course.setImagePath(input.getImagePath());
        course.setKeyword(input.getKeyword());
        course.setSubject(input.getSubject());
        course.setSummary(input.getSummary());
        course.setContents(input.getContents());
        course.setPrice(input.getPrice());
        course.setSalePrice(input.getSalePrice());
        course.setSaleEndAt(LocalDateTime.parse(input.getSaleEndAt()));

        return true;
    }

    @Override
    public boolean delete(String idList) {
        if (idList != null && idList.length() > 0) {
            String[] ids = idList.split(",");

            for (String s : ids) {
                long id = 0L;

                try {
                    id = Long.parseLong(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (id > 0) {
                    courseRepository.deleteById(id);
                }
            }
        }

        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CourseDto> frontList(SearchInput input,Pageable pageable) {
        return courseRepository.searchFront(CourseSearchCondition.of(input), pageable);
    }

    @Transactional
    @Override
    public ResponseResult req(TakeCourseInput input)  {

        try {
            Course course = courseRepository.findById(input.getCourseId())
                    .orElseThrow(() -> new CourseNotExistException("존재하지 않는 강의입니다."));

            Member member = memberRepository.findById(input.getUserId())
                    .orElseThrow(() -> new MemberException("존재하지 않는 회원입니다."));

            if (takeCourseRepository.existsByMember_IdAndCourse_IdAndStatusIn(
                    input.getUserId(),
                    input.getCourseId(),
                    Arrays.asList(TakeCourseStatus.REQUEST, TakeCourseStatus.COMPLETE))) {
                throw new TakeCourseAlreadyExistException("이미 수강중인 강의입니다.");
            }

            takeCourseRepository.save(TakeCourse.builder()
                    .course(course)
                    .member(member)
                    .payPrice(course.getPrice())
                    .status(TakeCourseStatus.REQUEST)
                    .build());
            return ResponseResult.builder().result(true).build();

        } catch (Exception e) {
            return ResponseResult.builder().result(false).message(e.getMessage()).build();
        }
    }


}
