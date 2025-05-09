package org.example.service;

import org.example.dto.MessageResponseDto;
import org.example.dto.ModifyScheduleDto;
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
import java.util.Objects;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepository scheduleRepository;

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
    public List<ScheduleResponseDto> findAllSchedule(LocalDateTime since, LocalDateTime until) {
        return scheduleRepository.findAllSchedules(since, until);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(int user_id, LocalDateTime since, LocalDateTime until) {
        return scheduleRepository.findAllSchedules(user_id, since, until);
    }

    @Override
    public ScheduleResponseDto findOneSchedule(int schedule_id) {
        Optional<ScheduleResponseDto> scheduleResponseDto = scheduleRepository.findOneSchedule(schedule_id);
        if(scheduleResponseDto.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다.");
        return scheduleResponseDto.get();
    }

    @Override
    public ScheduleResponseDto modifySchedule(int schedule_id, ModifyScheduleDto modifyScheduleDto) {
        ScheduleResponseDto tempSchedule = findOneSchedule (schedule_id);

        // 비밀번호 처리 -> 유저 테이블 기능 만든 후에..

        // 비어있는 항목이 있을 경우 기존의 값을 유지하도록 처리
        if(Objects.equals(modifyScheduleDto.getScheduleData().getTitle(), "")) modifyScheduleDto.getScheduleData().setTitle(tempSchedule.getTitle());
        if(Objects.equals(modifyScheduleDto.getScheduleData().getContents(), "")) modifyScheduleDto.getScheduleData().setContents(tempSchedule.getContents());

        Optional<ScheduleResponseDto> scheduleResponseDto = scheduleRepository.modifySchedule(schedule_id, modifyScheduleDto);
        if(scheduleResponseDto.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다.");
        return scheduleResponseDto.get();
    }

    @Override
    public MessageResponseDto deleteSchedule(int schedule_id, String password) {
        // 비밀번호 처리 -> 유저 테이블 기능 만든 후에..

        if(!scheduleRepository.deleteSchedule(schedule_id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다.");
        return new MessageResponseDto("삭제 완료");
    }
}
