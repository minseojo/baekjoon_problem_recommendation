package com.baekjoon_problem_recommendation.repository;

import com.baekjoon_problem_recommendation.domain.Member;
import com.baekjoon_problem_recommendation.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public long save(MemberDto member) {
        try {
            // 이미 존재하는 아이디인지 확인하기 위해 데이터베이스에서 아이디를 검색합니다.
            String checkQuery = "SELECT COUNT(*) FROM member WHERE user_id = ?";
            int count = jdbcTemplate.queryForObject(checkQuery, Integer.class, member.getUserId());

            // 해당하는 백준 아이디가 이미 존재하는 경우
            if (count > 0) {
                throw new IllegalArgumentException();
            }

            String insertQuery = "INSERT INTO member (user_id, username) VALUES (?, ?)";
            int row = jdbcTemplate.update(insertQuery, member.getUserId(), member.getUsername());
            System.out.println(row);
            return row;
        } catch (IncorrectResultSetColumnCountException | IllegalArgumentException e) {
            throw new IllegalArgumentException("해당하는 백준 아이디가 이미 존재합니다.");
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

    public MemberDto findById(Long id) {
        String query = "SELECT id, user_id, username FROM member WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> {
            MemberDto member = new MemberDto(
                    rs.getString("user_id"),
                    rs.getString("username"));
            return member;
        });
    }

    public long authenticate(MemberDto member) {
        String query = "SELECT id FROM member WHERE user_id = ? AND username = ?";
        try {
            return jdbcTemplate.queryForObject(query, Integer.class, member.getUserId(), member.getUsername());
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("백준 ID 또는 이름을 잘못 입력했습니다.");
        }
    }

    static class MemberRowMapper implements RowMapper<Member> {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            Member member = new Member(
                    rs.getString("user_id"),
                    rs.getString("username")
            );
            return member;
        }
    }
}
