package com.dokuny.mini_campus.course.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseAddInput {

    private long categoryId;

    private String imagePath;

    private String keyword;

    private String subject;

    private String summary;

    private String contents;

    private long price;

    private long salePrice;

    private String saleEndAt;


}
