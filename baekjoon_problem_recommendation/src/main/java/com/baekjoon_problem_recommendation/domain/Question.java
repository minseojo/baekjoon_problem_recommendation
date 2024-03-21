package com.baekjoon_problem_recommendation.domain;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Question {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private LocalDate createdAt;


    public Question(String title, String content, Long authorId, LocalDate createdAt) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.createdAt = createdAt;
    }
}
