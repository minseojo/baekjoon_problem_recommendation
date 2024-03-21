package com.baekjoon_problem_recommendation.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class QuestionDto {
    private String title;
    private String content;
    private String authorName;
    private LocalDateTime createdAt;


    public QuestionDto(String title, String content, String authorName, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.authorName = authorName;
        this.createdAt = createdAt;
    }
}
