package org.example.service;

import org.example.dto.ScheduleRequestDto;
import org.example.dto.ScheduleResponseDto;
import org.example.entity.Schedule;
import org.example.repository.JdbcTemplateScheduleRepository;
import org.example.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

        LocalDateTime nowDate = LocalDateTime.now();
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
    public List<ScheduleResponseDto> findAllSchedule() {
        return scheduleRepository.findAllSchedules();
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(int user_id) {
        return scheduleRepository.findAllSchedules(user_id);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(LocalDateTime localDateTime) {
        return scheduleRepository.findAllSchedules(localDateTime);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(int user_id, LocalDateTime localDateTime) {
        return scheduleRepository.findAllSchedules(user_id, localDateTime);
    }

    @Override
    public ScheduleResponseDto findOnseSchedule(int schedule_id) {
        Optional<ScheduleResponseDto> scheduleResponseDto = scheduleRepository.findOneSchedule(schedule_id);
        if(scheduleResponseDto.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다.");
        return scheduleResponseDto.get();
    }
}
