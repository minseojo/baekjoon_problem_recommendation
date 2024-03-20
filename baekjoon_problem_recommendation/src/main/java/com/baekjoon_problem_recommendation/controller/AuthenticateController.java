package com.baekjoon_problem_recommendation.controller;

import com.baekjoon_problem_recommendation.dto.MemberDto;
import com.baekjoon_problem_recommendation.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AuthenticateController {
    private final MemberRepository memberRepository;

    @GetMapping("/login")
    public String login(@ModelAttribute("member") MemberDto member, Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String showMemberForm(@ModelAttribute("member") MemberDto member, Model model, RedirectAttributes attributes) {
        try {
            long findMemberId = memberRepository.authenticate(member);

            log.info("\n로그인 성공\n백준 ID : {}\n이름 : {}", member.getUserId(), member.getUsername());
            model.addAttribute("member", member);
            return "redirect:/member/" + findMemberId;
        } catch (IllegalArgumentException e) {
            attributes.addAttribute("userId", member.getUserId());
            attributes.addAttribute("error", e.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/signup")
    public String memberSignup(@ModelAttribute("member") MemberDto member) {
        return "signup";
    }

    @PostMapping("/signup")
    public String saveMember(@ModelAttribute("member") MemberDto member, RedirectAttributes attributes) {
        try {
            memberRepository.save(member);
        } catch (IllegalArgumentException e) {
            attributes.addAttribute("error", e.getMessage());
            return "redirect:/signup";
        }
        return "redirect:/";
    }
}
