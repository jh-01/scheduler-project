package org.example.service;

import org.example.dto.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto);
    PageResponseDto<ScheduleResponseDto> findAllSchedule(PageRequestDto pageRequestDto);
    ScheduleResponseDto findOneSchedule(int schedule_id);
    ScheduleResponseDto modifySchedule(ModifyScheduleDto modifyScheduleDto);
    MessageResponseDto deleteSchedule(DeleteScheduleDto deleteScheduleDto);
}
