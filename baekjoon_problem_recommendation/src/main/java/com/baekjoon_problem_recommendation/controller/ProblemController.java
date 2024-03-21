package com.baekjoon_problem_recommendation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/problems")
public class ProblemController {

    @GetMapping("/level")
    public String problemsLevel() {
        return "problems/level";
    }

    @GetMapping("/tags")
    public String problemsTags() {
        return "problems/tags";
    }

    @GetMapping("/recommendation")
    public String problemsRecommendation() {
        return "problems/recommendation";
    }
}
