package org.example.repository;

import org.example.dto.*;
import org.example.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository{
    Optional<ScheduleResponseDto> saveSchedule(int userId, ScheduleRequestDto schedule);
    PageResponseDto<ScheduleResponseDto> findAllSchedules(PageRequestDto pageRequestDto);
    Optional<ScheduleResponseDto> findOneSchedule(int schedule_id);
    Optional<ScheduleResponseDto> modifySchedule(ModifyScheduleDto modifyScheduleDto);
    boolean deleteSchedule(int schedule_id);
}
