package org.example.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.*;
import org.example.entity.User;
import org.example.exception.ScheduleDeletionException;
import org.example.exception.ScheduleFindException;
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

@Slf4j
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
        String sql = "SELECT * FROM users";
        if(since != null || until != null){
            sql += " WHERE createDate >= ? AND createDate <= ? ORDER BY createDate DESC";
            return jdbcTemplate.query(sql, userRowMapper(), since, until);
        } else {
            sql +=" ORDER BY updateDate DESC";
            return jdbcTemplate.query(sql, userRowMapper());
        }
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
    public Optional<UserResponseDto> modifyUserLoginId(ModifyUserLoginIdDto modifyUserLoginIdDto) {
        String sql = "UPDATE users SET loginId = ? WHERE loginId = ?";
        jdbcTemplate.update(sql,
                modifyUserLoginIdDto.getNewLoginId(),
                modifyUserLoginIdDto.getTempLoginId());
        return findUser(modifyUserLoginIdDto.getNewLoginId());
    }

    @Override
    public Optional<UserResponseDto> modifyUserInfo(ModifyUserInfoDto modifyUserInfoDto) {
        String sql = "UPDATE users SET nickname = ?, email = ? WHERE loginId = ?";
        jdbcTemplate.update(sql,
                modifyUserInfoDto.getNickname(),
                modifyUserInfoDto.getEmail(),
                modifyUserInfoDto.getLoginId());
        return findUser(modifyUserInfoDto.getLoginId());
    }

    @Override
    public Optional<UserResponseDto> modifyUserPassword(ModifyUserPasswordDto modifyUserPasswordDto) {
        String sql = "UPDATE users SET password = ? WHERE loginId = ?";
        jdbcTemplate.update(sql,
                modifyUserPasswordDto.getNewPassword(),
                modifyUserPasswordDto.getLoginId());
        return findUser(modifyUserPasswordDto.getLoginId());
    }

    @Override
    public boolean existsByLoginId(String loginId) {
        String sql = "SELECT exists(SELECT * FROM users WHERE loginId = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, loginId));
    }

    @Override
    public boolean existsByUserId(int userId) {
        String sql = "SELECT exists(SELECT * FROM users WHERE userId = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, userId));
    }

    @Override
    public boolean validatePassword(String loginId, String password) {
        String sql = "SELECT password FROM users WHERE loginId = ?";
        List<User> user = jdbcTemplate.query(sql, userPasswordRowMapper(), loginId);
        if (user.isEmpty()) return false;
        return user.get(0).getPassword().equals(password);
    }

    @Override
    public boolean deleteUser(DeleteUserDto deleteUserDto) {
        String sql = "DELETE FROM users WHERE loginId = ?";
        int result = jdbcTemplate.update(sql, deleteUserDto.getLoginId());
        if(result == 0) throw new ScheduleFindException("존재하지 않는 일정입니다.");
        return result > 0;
    }

    private RowMapper<User> userPasswordRowMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(
                        rs.getString("password")
                );
            }
        };
    }

}
