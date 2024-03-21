package com.baekjoon_problem_recommendation.exception.global;

public class SignupException extends RuntimeException {

    public SignupException() {
        super();
    }

    public SignupException(String message) {
        super(message);
    }

    public SignupException(String message, Throwable cause) {
        super(message, cause);
    }

}
