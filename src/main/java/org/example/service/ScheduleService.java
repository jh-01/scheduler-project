package org.example.service;

import org.example.dto.ScheduleRequestDto;
import org.example.dto.ScheduleResponseDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto);
    List<ScheduleResponseDto> findAllSchedule(int user_id);
    ScheduleResponseDto findOnseSchedule(int schedule_id);
}
