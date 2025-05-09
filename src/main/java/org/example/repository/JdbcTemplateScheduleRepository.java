package org.example.repository;

import org.example.dto.ScheduleResponseDto;
import org.example.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        // 문자열을 작성하지 않고 insert 가능
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("schedule_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", schedule.getUserId());
        parameters.put("title", schedule.getTitle());
        parameters.put("contents", schedule.getContents());
        parameters.put("createDate", schedule.getCreateDate());
        parameters.put("updateDate", schedule.getUpdateDate());

        // 식별자 auto increment
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(
                key.intValue(),
                schedule.getUserId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreateDate(),
                schedule.getUpdateDate()
        );
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return jdbcTemplate.query("select * from schedule ORDER BY updateDate DESC", scheduleRowMapper());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(int user_id) {
        String sql = "SELECT * FROM schedule WHERE user_id = ? ORDER BY updateDate DESC";
        return jdbcTemplate.query(sql, scheduleRowMapper(), user_id);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(LocalDateTime since, LocalDateTime until) {
        String sql = "SELECT * FROM schedule WHERE updateDate >= ? AND updateDate <= ? ORDER BY updateDate DESC";
        return jdbcTemplate.query(sql, scheduleRowMapper(), since, until);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(int user_id, LocalDateTime since, LocalDateTime until) {
        String sql = "SELECT * FROM schedule WHERE updateDate >= ? AND updateDate <= ?  AND user_id = ? ORDER BY updateDate DESC";
        return jdbcTemplate.query(sql, scheduleRowMapper(), since, until, user_id);
    }

    @Override
    public Optional<ScheduleResponseDto> findOneSchedule(int schedule_id) {
        String sql = "SELECT * FROM schedule WHERE schedule_id = ?";
        return jdbcTemplate.query(sql, scheduleRowMapper(), schedule_id).stream().findAny();
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getInt("schedule_id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getTimestamp("createDate").toLocalDateTime(),
                        rs.getTimestamp("updateDate").toLocalDateTime()
                );
            }

        };
    }

}