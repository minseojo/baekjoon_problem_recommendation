package com.baekjoon_problem_recommendation.service;

import com.baekjoon_problem_recommendation.dto.QuestionDto;
import com.baekjoon_problem_recommendation.dto.QuestionFormDto;
import com.baekjoon_problem_recommendation.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public void save(QuestionFormDto question) {
        questionRepository.save(question);
    }

    public List<QuestionDto> findAll() {
        return questionRepository.findAll();
    }
}
