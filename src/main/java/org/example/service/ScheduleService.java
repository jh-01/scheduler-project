package org.example.service;

import org.example.dto.MessageResponseDto;
import org.example.dto.ModifyScheduleDto;
import org.example.dto.ScheduleRequestDto;
import org.example.dto.ScheduleResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto);
    List<ScheduleResponseDto> findAllSchedule();
    List<ScheduleResponseDto> findAllSchedule(int user_id);
    List<ScheduleResponseDto> findAllSchedule(LocalDateTime since, LocalDateTime until);
    List<ScheduleResponseDto> findAllSchedule(int user_id, LocalDateTime since, LocalDateTime until);
    ScheduleResponseDto findOneSchedule(int schedule_id);
    ScheduleResponseDto modifySchedule(int schedule_id, ModifyScheduleDto modifyScheduleDto);
    MessageResponseDto deleteSchedule(int scheduleId, String password);
}
