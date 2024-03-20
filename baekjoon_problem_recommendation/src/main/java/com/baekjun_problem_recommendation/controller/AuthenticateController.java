package com.baekjun_problem_recommendation.controller;

import com.baekjun_problem_recommendation.dto.MemberDto;
import com.baekjun_problem_recommendation.repository.MemberRepository;
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
        // 로그인 실패 -> 다시 로그인 페이지로 이동할 때 사용자 아이디를 보존하기 위해 모델에 추가
        model.addAttribute("userId", member.getUserId());
        return "login";
    }

    @PostMapping("/login")
    public String showMemberForm(@ModelAttribute("member") MemberDto member, Model model, RedirectAttributes attributes) {
        long findMemberId = memberRepository.authenticate(member);

        if (findMemberId == -1) {
            attributes.addAttribute("userId", member.getUserId());
            return "redirect:/login";
        }

        log.info("\n로그인 성공\n백준 ID : {}\n이름 : {}", member.getUserId(), member.getUsername());
        model.addAttribute("member", member);
        return "redirect:/member/" + findMemberId;
    }

    @GetMapping("/signup")
    public String memberSignup(@ModelAttribute("member") MemberDto member) {
        return "signup";
    }

    @PostMapping("/signup")
    public String saveMember(@ModelAttribute("member") MemberDto member) {
        memberRepository.save(member);
        return "redirect:/";
    }
}
