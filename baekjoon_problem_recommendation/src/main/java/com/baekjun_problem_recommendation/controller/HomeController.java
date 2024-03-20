package com.baekjun_problem_recommendation.controller;

import com.baekjun_problem_recommendation.dto.MemberDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@ModelAttribute("member") MemberDto member) {
        return "redirect:/login";
    }
}
