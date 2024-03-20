package com.baekjun_problem_recommendation.domain;

import lombok.Getter;

@Getter
public class Member {
    private long id;
    private String userId;
    private String username;

    public Member(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
