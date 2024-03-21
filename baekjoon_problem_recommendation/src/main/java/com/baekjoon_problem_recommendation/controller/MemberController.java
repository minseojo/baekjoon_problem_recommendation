package com.baekjoon_problem_recommendation.controller;

import com.baekjoon_problem_recommendation.domain.Member;
import com.baekjoon_problem_recommendation.dto.MemberDto;
import com.baekjoon_problem_recommendation.dto.MemberLoginDto;
import com.baekjoon_problem_recommendation.exception.EmptyMemberAccessException;
import com.baekjoon_problem_recommendation.exception.global.LoginException;
import com.baekjoon_problem_recommendation.repository.MemberRepository;
import com.baekjoon_problem_recommendation.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    @GetMapping("/login")
    public String login(@ModelAttribute("member") MemberLoginDto member,
                        @ModelAttribute("error") String error,
                        @ModelAttribute("userId") String userId) {
        return "member/login";
    }

    @PostMapping("/login")
    public String showMemberForm(@Validated @ModelAttribute("member") MemberLoginDto member,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            log.info("errors {}", bindingResult);
            return "/member/login";
        }

        Optional<Member> findMember;
        try {
            findMember = memberService.login(member);
        } catch (LoginException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addAttribute("userId", member.getUserId());
            log.info("LoginException : {}", member.getUserId());
            return "redirect:/login";
        }

        model.addAttribute("member", member);
        System.out.println(findMember.get().getId());
        return "redirect:/member/" + findMember.get().getId();
    }

    @GetMapping("/signup")
    public String memberSignup(@ModelAttribute("member") MemberDto member, Model model) {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String saveMember(@Validated @ModelAttribute("member") MemberDto member,
                             BindingResult bindingResult,
                             RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            log.info("errors {}", bindingResult);
            return "/member/signup";
        }

        if (!member.getPassword1().equals(member.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "/member/signup";
        }

        memberService.save(member);
        return "redirect:/";
    }
    
    @GetMapping("/member/{userId}")
    public String getMemberProfile(@PathVariable String userId, Model model) {
        try {
            Member member = memberService.findByUserId(userId);
            model.addAttribute("member", member); // 멤버가 존재 하면
        } catch (EmptyMemberAccessException e) {
            log.info("EmptyMemberAccessException : " + e.getMessage());
            model.addAttribute("error", e.getMessage());
        }

        return "/member/memberProfile"; // 멤버 프로필을 보여주는 Thymeleaf 템플릿 페이지로 이동합니다.
    }
}
