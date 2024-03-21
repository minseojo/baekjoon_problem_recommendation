package com.baekjoon_problem_recommendation.controller;

import com.baekjoon_problem_recommendation.dto.QuestionDto;
import com.baekjoon_problem_recommendation.dto.QuestionFormDto;
import com.baekjoon_problem_recommendation.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        List<QuestionDto> questionList = questionService.findAll();
        model.addAttribute("questionList", questionList);
        return "question/list";
    }

    @GetMapping("/create")
    public String createQuestionForm(@ModelAttribute("question") QuestionFormDto question) {
        return "question/create"; // "question/create"를 반환하도록 수정
    }

    @PostMapping("/create")
    public String createQuestion(@ModelAttribute("question") QuestionFormDto question) {
        // 질문을 등록하는 로직을 추가
        questionService.save(question);
        return "redirect:/question/list"; // 등록 후에 리스트 페이지로 리다이렉트
    }

}
