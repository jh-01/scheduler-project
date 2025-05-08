package org.example.service;

import org.example.dto.ScheduleRequestDto;
import org.example.dto.ScheduleResponseDto;
import org.example.entity.Schedule;
import org.example.repository.JdbcTemplateScheduleRepository;
import org.example.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    private ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(JdbcTemplateScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto){
        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleRepository.findAllSchedules();
        int newId = scheduleResponseDtoList.size() + 1;

        LocalDate nowDate = LocalDate.now();
        return scheduleRepository.saveSchedule(
                new Schedule(
                        newId,
                        scheduleRequestDto.getUser_id(),
                        scheduleRequestDto.getTitle(),
                        scheduleRequestDto.getContents(),
                        nowDate,
                        nowDate
                ));
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(int user_id) {
        return scheduleRepository.findAllSchedules(user_id);
    }

    @Override
    public ScheduleResponseDto findOnseSchedule(int schedule_id) {
        return scheduleRepository.findOneSchedule(schedule_id);
    }
}
