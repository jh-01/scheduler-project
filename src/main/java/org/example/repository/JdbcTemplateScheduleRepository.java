package org.example.repository;

import org.example.dto.ModifyScheduleDto;
import org.example.dto.ScheduleRequestDto;
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

import static java.lang.Integer.parseInt;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<ScheduleResponseDto> saveSchedule(ScheduleRequestDto schedule) {
        LocalDateTime nowDateTime = LocalDateTime.now();
        // 문자열을 작성하지 않고 insert 가능
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("scheduleId");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", schedule.getUserId());
        parameters.put("title", schedule.getTitle());
        parameters.put("contents", schedule.getContents());
        parameters.put("createDate", nowDateTime);
        parameters.put("updateDate", nowDateTime);

        // 식별자 auto increment
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return findOneSchedule(parseInt(key.toString()));
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return jdbcTemplate.query("select * from schedule ORDER BY updateDate DESC", scheduleRowMapper());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(int user_id) {
        String sql = "SELECT * FROM schedule WHERE userId = ? ORDER BY updateDate DESC";
        return jdbcTemplate.query(sql, scheduleRowMapper(), user_id);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(LocalDateTime since, LocalDateTime until) {
        String sql = "SELECT * FROM schedule WHERE updateDate >= ? AND updateDate <= ? ORDER BY updateDate DESC";
        return jdbcTemplate.query(sql, scheduleRowMapper(), since, until);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(int user_id, LocalDateTime since, LocalDateTime until) {
        String sql = "SELECT * FROM schedule WHERE updateDate >= ? AND updateDate <= ?  AND userId = ? ORDER BY updateDate DESC";
        return jdbcTemplate.query(sql, scheduleRowMapper(), since, until, user_id);
    }

    @Override
    public Optional<ScheduleResponseDto> findOneSchedule(int schedule_id) {
        String sql = "SELECT * FROM schedule WHERE scheduleId = ?";
        return jdbcTemplate.query(sql, scheduleRowMapper(), schedule_id).stream().findAny();
    }

    @Override
    public Optional<ScheduleResponseDto> modifySchedule(int schedule_id, ModifyScheduleDto modifyScheduleDto) {
        String sql = "UPDATE schedule SET title = ?, contents = ?, updateDate = ? WHERE scheduleId = ?";
        jdbcTemplate.update(sql,
                modifyScheduleDto.getScheduleData().getTitle(),
                modifyScheduleDto.getScheduleData().getContents(),
                LocalDateTime.now(),
                schedule_id
        );
        return findOneSchedule(schedule_id);
    }

    @Override
    public boolean deleteSchedule(int schedule_id) {
        String sql = "DELETE FROM schedule WHERE scheduleId = ?";
        int sqlResult = jdbcTemplate.update(sql, schedule_id);
        return sqlResult > 0;
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getInt("scheduleId"),
                        rs.getInt("userId"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getTimestamp("createDate").toLocalDateTime(),
                        rs.getTimestamp("updateDate").toLocalDateTime()
                );
            }
        };
    }
}