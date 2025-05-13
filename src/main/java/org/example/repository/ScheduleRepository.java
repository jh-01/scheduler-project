package org.example.repository;

import org.example.dto.*;
import org.example.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository{
    Optional<ScheduleResponseDto> saveSchedule(ScheduleRequestDto schedule);
    PageResponseDto<ScheduleResponseDto> findAllSchedules(PageRequestDto pageRequestDto);
    List<ScheduleResponseDto> findAllSchedules(int user_id);
    List<ScheduleResponseDto> findAllSchedules(LocalDateTime since, LocalDateTime until);
    List<ScheduleResponseDto> findAllSchedules(int user_id, LocalDateTime since, LocalDateTime until);
    Optional<ScheduleResponseDto> findOneSchedule(int schedule_id);
    Optional<ScheduleResponseDto> modifySchedule(int schedule_id, ModifyScheduleDto modifyScheduleDto);
    boolean deleteSchedule(int schedule_id);
}
