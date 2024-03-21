package com.baekjoon_problem_recommendation.repository;

import com.baekjoon_problem_recommendation.dto.QuestionDto;
import com.baekjoon_problem_recommendation.dto.QuestionFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<QuestionDto> findAll() {
        String query = "SELECT * FROM question";
        return jdbcTemplate.query(query, new QuestionRowMapper());
    }

    public void save(QuestionFormDto questionDTO) {
        // 현재 인증된 사용자의 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName(); // 현재 사용자의 ID를 가져옴

        // 나머지 저장 로직 구현
        QuestionDto question = new QuestionDto(questionDTO.getTitle(), questionDTO.getContent(), currentUserName, questionDTO.getCreatedAt());

        String insertQuery = "INSERT INTO question (title, content, author_name, created_at) VALUES (?,?,?,?)";
        jdbcTemplate.update(insertQuery, question.getTitle(), question.getContent(), question.getAuthorName(), question.getCreatedAt());
    }

    static class QuestionRowMapper implements RowMapper<QuestionDto> {
        @Override
        public QuestionDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            QuestionDto question = new QuestionDto(
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("author_name"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );

            return question;
        }
    }
}
