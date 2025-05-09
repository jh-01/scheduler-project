package org.example.repository;

import org.example.dto.MessageResponseDto;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Integer.parseInt;

@Repository
public class JdbcTemplateUserRepository implements UserRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<UserResponseDto> saveUser(UserRequestDto user) {
        LocalDateTime nowdate = LocalDateTime.now();
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("users").usingGeneratedKeyColumns("userId");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("loginId", user.getLoginId());
        parameters.put("nickname", user.getNickname());
        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());
        parameters.put("createDate", nowdate);
        parameters.put("updateDate", nowdate);

        // 식별자 auto increment
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return findUser(parseInt(key.toString()));
    }

    @Override
    public List<UserResponseDto> findAllUsers(LocalDateTime since, LocalDateTime until) {
        String sql = "SELECT * FROM users WHERE updateDate >= ? AND updateDate <= ? ORDER BY updateDate DESC";
        return jdbcTemplate.query(sql, userRowMapper(), since, until);
    }

    @Override
    public List<UserResponseDto> findAllUsers() {
        return jdbcTemplate.query("select * from users ORDER BY updateDate DESC", userRowMapper());
    }

    private RowMapper<UserResponseDto> userRowMapper() {
        return new RowMapper<UserResponseDto>() {
            @Override
            public UserResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new UserResponseDto(
                        rs.getInt("userId"),
                        rs.getString("loginId"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        rs.getTimestamp("createDate").toLocalDateTime(),
                        rs.getTimestamp("updateDate").toLocalDateTime()
                );
            }
        };
    }

    @Override
    public Optional<UserResponseDto> findUser(int userId) {
        String sql = "SELECT * FROM users WHERE userId = ? ORDER BY updateDate DESC";
        return jdbcTemplate.query(sql, userRowMapper(), userId).stream().findAny();
    }

    @Override
    public Optional<UserResponseDto> findUser(String loginId) {
        String sql = "SELECT * FROM users WHERE loginId = ? ORDER BY updateDate DESC";
        return jdbcTemplate.query(sql, userRowMapper(), loginId).stream().findAny();
    }

    @Override
    public Optional<UserResponseDto> modifyUser() {
        return Optional.empty();
    }

    @Override
    public Optional<MessageResponseDto> deleteUser() {
        return Optional.empty();
    }
}
