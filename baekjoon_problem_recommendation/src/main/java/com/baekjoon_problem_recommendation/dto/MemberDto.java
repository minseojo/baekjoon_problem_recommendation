package com.baekjoon_problem_recommendation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberDto {

    @NotBlank(message = "백준 ID를 입력해주세요.")
    @Size(min = 2, max = 30, message = "백준 ID는 2자 이상 30자 이하로 입력해주세요.")
    private String userId;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 30, message = "이름은 2자 이상 30자 이하로 입력해주세요.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;

    public MemberDto(String userId, String username, String password1, String password2, String email) {
        this.userId = userId;
        this.username = username;
        this.password1 = password1;
        this.password2 = password2;
        this.email = email;
    }
}
