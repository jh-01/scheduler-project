package org.example.repository;

import org.example.dto.ScheduleResponseDto;
import org.example.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedules();
    List<ScheduleResponseDto> findAllSchedules(int user_id);

    List<ScheduleResponseDto> findAllSchedules(LocalDateTime since, LocalDateTime until);

    List<ScheduleResponseDto> findAllSchedules(int user_id, LocalDateTime since, LocalDateTime until);

    Optional<ScheduleResponseDto> findOneSchedule(int schedule_id);
}
