package com.baekjoon_problem_recommendation.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class QuestionFormDto {
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public QuestionFormDto(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
