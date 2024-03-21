package com.baekjoon_problem_recommendation.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
public class Member {
    private long id;
    private String userId;
    private String username;
    private String password;
    private String email;

    private MemberStatistics statistics;

    public Member(String userId, String username, String password, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Member(String userId, String username, MemberStatistics statistics) {
        this.userId = userId;
        this.username = username;
        this.statistics = statistics;
    }
}
