package com.baekjoon_problem_recommendation.service;

import com.baekjoon_problem_recommendation.domain.Member;
import com.baekjoon_problem_recommendation.dto.MemberDto;
import com.baekjoon_problem_recommendation.dto.MemberLoginDto;
import com.baekjoon_problem_recommendation.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(MemberDto member) {
        return memberRepository.save(member);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id);
    }

    public Member findByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }
    public Optional<Member> login(MemberLoginDto member) {
        return memberRepository.login(member);
    }

}
