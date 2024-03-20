package com.baekjun_problem_recommendation.repository;

import com.baekjun_problem_recommendation.domain.Member;
import com.baekjun_problem_recommendation.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
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
        String query = "INSERT INTO member (user_id, username) VALUES (?, ?)";
        int row = jdbcTemplate.update(query, member.getUserId(), member.getUsername());
        return row;
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
            return -1;
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
