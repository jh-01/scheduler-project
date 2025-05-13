package org.example.service;

import org.example.dto.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto);
    PageResponseDto<ScheduleResponseDto> findAllSchedule(PageRequestDto pageRequestDto);
    List<ScheduleResponseDto> findAllSchedule(int user_id);
    List<ScheduleResponseDto> findAllSchedule(LocalDateTime since, LocalDateTime until);
    List<ScheduleResponseDto> findAllSchedule(int user_id, LocalDateTime since, LocalDateTime until);
    ScheduleResponseDto findOneSchedule(int schedule_id);
    ScheduleResponseDto modifySchedule(int schedule_id, ModifyScheduleDto modifyScheduleDto);
    MessageResponseDto deleteSchedule(int scheduleId, String password);
}
