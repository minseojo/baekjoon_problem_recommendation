package com.baekjun_problem_recommendation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberDto {

    private String userId;
    private String username;

    public MemberDto(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
