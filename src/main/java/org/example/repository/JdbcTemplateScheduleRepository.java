package org.example.repository;

import org.example.dto.*;
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
    public Optional<ScheduleResponseDto> saveSchedule(int userId, ScheduleRequestDto schedule) {
        LocalDateTime nowDateTime = LocalDateTime.now();
        // 문자열을 작성하지 않고 insert 가능
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("scheduleId");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("title", schedule.getTitle());
        parameters.put("contents", schedule.getContents());
        parameters.put("createDate", nowDateTime);
        parameters.put("updateDate", nowDateTime);

        // 식별자 auto increment
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return findOneSchedule(parseInt(key.toString()));
    }

    @Override
    public PageResponseDto<ScheduleResponseDto> findAllSchedules(PageRequestDto pageRequestDto) {
        String sql = "select * from schedule s JOIN users u on s.userId = u.userId ORDER BY s.updateDate DESC LIMIT ?, ?";
        List<ScheduleResponseDto> schedules = jdbcTemplate.query(sql, scheduleRowMapper(), pageRequestDto.getOffset(), pageRequestDto.getSize());
        int totalSchedules = jdbcTemplate.queryForObject("select COUNT(*) from schedule s JOIN users u on s.userId = u.userId ORDER BY s.updateDate DESC", Integer.class);
        int totalPages = totalSchedules / pageRequestDto.getSize() + (totalSchedules % pageRequestDto.getSize() > 0? 1 : 0);
        PageResponseDto.PageInfo pageInfo = new PageResponseDto.PageInfo(
                pageRequestDto.getPage(),
                pageRequestDto.getSize(),
                totalSchedules,
                totalPages < pageRequestDto.getPage(),
                totalPages > pageRequestDto.getPage()
        );
        return new PageResponseDto<>(schedules, pageInfo);
    }

    @Override
    public Optional<ScheduleResponseDto> findOneSchedule(int schedule_id) {
        String sql = "SELECT * FROM schedule s JOIN users u on s.userId = u.userId WHERE s.scheduleId = ?";
        return jdbcTemplate.query(sql, scheduleRowMapper(), schedule_id).stream().findAny();
    }

    @Override
    public Optional<ScheduleResponseDto> modifySchedule(ModifyScheduleDto modifyScheduleDto) {
        String sql = "UPDATE schedule SET "
                + "title = CASE WHEN ? = '' THEN title ELSE ? END, "
                + "contents = CASE WHEN ? = '' THEN contents ELSE ? END, "
                + "updateDate = ? WHERE scheduleId = ?";
        jdbcTemplate.update(sql,
                modifyScheduleDto.getScheduleData().getTitle(),
                modifyScheduleDto.getScheduleData().getTitle(),
                modifyScheduleDto.getScheduleData().getContents(),
                modifyScheduleDto.getScheduleData().getContents(),
                LocalDateTime.now(),
                modifyScheduleDto.getScheduleData().getScheduleId()
        );
        return findOneSchedule(modifyScheduleDto.getScheduleData().getScheduleId());
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
                        rs.getString("nickname"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getTimestamp("createDate").toLocalDateTime(),
                        rs.getTimestamp("updateDate").toLocalDateTime()
                );
            }
        };
    }
}