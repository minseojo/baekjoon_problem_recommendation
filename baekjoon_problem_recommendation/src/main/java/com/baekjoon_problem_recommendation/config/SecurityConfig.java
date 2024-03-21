package com.baekjoon_problem_recommendation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
// @EnableWebSecurity
// 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션이다.
// 이 애너테이션을 사용하면 스프링 시큐리티를 활성화하는 역할을 한다
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // 인증되지 않은 모든 페이지의 요청을 허락한다는 의미이다. 따라서 로그인하지 않더라도 모든 페이지에 접근할 수 있도록 한다.
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())

                //.formLogin 메서드는 스프링 시큐리티의 로그인 설정을 담당하는 부분으로,
                // 설정 내용은 로그인 페이지의 URL은 /member/login이고 로그인 성공 시에 이동할 페이지는 루트 URL(/)임을 의미한다.
                .formLogin((formLogin) -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/"))

                // 그아웃 기능을 구현하기 위한 설정을 추가했다.
                // 로그아웃 URL을 /logout으로 설정하고 로그아웃이 성공하면 루트(/) 페이지로 이동하도록 했다.
                // 그리고 .invalidateHttpSession(true)를 통해 로그아웃 시 생성된 사용자 세션도 삭제하도록 처리했다.
                .logout((logout) -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true));




        return http.build();
    }

    // 패스워드 인코더
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 로그인 기능

    /**
     * 이와 같이 AuthenticationManager 빈을 생성했다.
     * AuthenticationManager는 스프링 시큐리티의 인증을 처리한다.
     * AuthenticationManager는 사용자 인증 시 앞에서 작성한
     * UserSecurityService와 PasswordEncoder를 내부적으로 사용하여 인증과 권한 부여 프로세스를 처리한다.
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
