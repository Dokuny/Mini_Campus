package com.dokuny.mini_campus.course.dto;

import com.dokuny.mini_campus.course.entity.Course;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CourseDto {

    private Long id;

    private Long categoryId;

    private String imagePath;

    private String keyword;

    private String subject;

    private String summary;

    private String contents;

    private long price;

    private long salePrice;

    private LocalDateTime saleEndAt;

    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;


    public static CourseDto of(Course course) {

        return CourseDto.builder()
                .id(course.getId())
                .imagePath(course.getImagePath())
                .keyword(course.getKeyword())
                .subject(course.getSubject())
                .summary(course.getSummary())
                .contents(course.getContents())
                .price(course.getPrice())
                .salePrice(course.getSalePrice())
                .saleEndAt(course.getSaleEndAt())
                .registeredAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }
}
