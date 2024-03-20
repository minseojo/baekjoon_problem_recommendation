package com.baekjoon_problem_recommendation.controller;

import com.baekjoon_problem_recommendation.dto.MemberDto;
import com.baekjoon_problem_recommendation.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberRepository memberRepository;


    @GetMapping("/member/{id}")
    public String getMemberProfile(@PathVariable Long id, Model model) {
        MemberDto member = memberRepository.findById(id);
        System.out.println(member.getUserId() + " " + member.getUsername());
        if (member == null) {
            return "redirect:/member";
        }
        model.addAttribute("member", member);
        return "memberProfile"; // 멤버 프로필을 보여주는 Thymeleaf 템플릿 페이지로 이동합니다.
    }
}
