package org.example.repository;

import org.example.dto.ScheduleResponseDto;
import org.example.entity.Schedule;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedules();
    List<ScheduleResponseDto> findAllSchedules(int user_id);
    ScheduleResponseDto findOneSchedule(int schedule_id);
}
