package com.baekjoon_problem_recommendation.exception.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //@ExceptionHandler(LoginException.class)
    public String handleLoginException(LoginException ex, RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/login";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolationException(DataIntegrityViolationException ex, RedirectAttributes attributes) {
        attributes.addAttribute("error", "이미 등록된 이메일입니다.");
        log.info("DuplicateKeyException {}", ex.getMessage());
        return "redirect:/signup";
    }


    @ExceptionHandler(SignupException.class)
    public String handleSignupException(SignupException ex, RedirectAttributes attributes) {
        attributes.addAttribute("error", ex.getMessage());
        attributes.addAttribute("userId", attributes.getAttribute("userId"));
        log.info("SignupException {}", ex.getMessage());
        return "redirect:/signup";
    }

}
