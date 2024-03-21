package com.baekjoon_problem_recommendation.repository;

import com.baekjoon_problem_recommendation.domain.Member;
import com.baekjoon_problem_recommendation.dto.MemberDto;
import com.baekjoon_problem_recommendation.dto.MemberLoginDto;
import com.baekjoon_problem_recommendation.exception.EmptyMemberAccessException;
import com.baekjoon_problem_recommendation.exception.global.LoginException;
import com.baekjoon_problem_recommendation.exception.global.SignupException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member save(MemberDto memberDto) {
        existsByUsername(memberDto);

        Member member = new Member(memberDto.getUserId(), memberDto.getUsername(),
                passwordEncoder.encode(memberDto.getPassword1()),memberDto.getEmail());

        String insertQuery = "INSERT INTO member (user_id, username, password, email) VALUES (?,?,?,?)";
        jdbcTemplate.update(insertQuery, member.getUserId(), member.getUsername(),
                member.getPassword(), member.getEmail());
        return member;

    }

    public Optional<Member> findByUsername(String username) {
        String query = "SELECT * from member WHERE username = ?";
        try {
            Member member = jdbcTemplate.queryForObject(query, new Object[]{username}, new MemberRowMapper());
            log.info("findByUsername : {}", member.getUsername());
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private void existsByUsername(MemberDto member) {
        // 이미 존재하는 아이디인지 확인하기 위해 데이터베이스에서 아이디를 검색합니다.
        String checkQuery = "SELECT COUNT(*) FROM member WHERE user_id = ?";
        int count = jdbcTemplate.queryForObject(checkQuery, Integer.class, member.getUserId());

        // 해당하는 백준 아이디가 이미 존재하는 경우
        if (count > 0) {
            throw new SignupException("해당하는 백준 아이디가 이미 존재합니다.");
        }
    }

    @Transactional
    public void updateUserId(MemberDto member) {
        String query = "UPDATE member SET user_id = ?";
        jdbcTemplate.update(query, member.getUserId());
    }

    public void updateUsername(MemberDto member) {
        String query = "UPDATE member SET username = ?";
        jdbcTemplate.update(query, member.getUsername());
    }

    public Member findById(Long id) {
        String query = "SELECT id, user_id, username FROM member WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> {
            Member member = new Member(
                    rs.getString("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"));
            return member;
        });
    }

    public Optional<Member> login(MemberLoginDto member) {
        String query = "SELECT id FROM member WHERE user_id = ? AND username = ? AND password = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, Member.class, member.getUserId(), member.getUsername(), member.getPassword()));
        } catch (EmptyResultDataAccessException e) {
            throw new LoginException("백준 ID 또는 이름 또는 비밀번호를 잘못 입력했습니다.");
        }
    }

    public Member findByUserId(String userId) {
        String query = "SELECT id, user_id, username FROM member WHERE user_id = ?";
        try {
            Member memberDto = jdbcTemplate.queryForObject(query, (rs, rowNum) ->
                                new Member(rs.getString("user_id"),
                                        rs.getString("username"),
                                        rs.getString("password"),
                                        rs.getString("email")),
                                userId);
            return memberDto;
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyMemberAccessException(userId + "는 존재하지 않는 회원입니다.");
        }
    }

    static class MemberRowMapper implements RowMapper<Member> {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Member member = new Member(
                    rs.getString("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email")
            );
            return member;
        }
    }
}
