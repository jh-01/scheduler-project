package org.example.service;

import org.example.dto.*;
import org.example.exception.ScheduleCreationException;
import org.example.exception.ScheduleDeletionException;
import org.example.exception.ScheduleFindException;
import org.example.exception.ScheduleModifyException;
import org.example.repository.JdbcTemplateScheduleRepository;
import org.example.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final UserServiceImpl userService;

    public ScheduleServiceImpl(JdbcTemplateScheduleRepository scheduleRepository, UserServiceImpl userService){
        this.scheduleRepository = scheduleRepository;
        this.userService = userService;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto){
        // 존재하는 유저인지 확인
        userService.validateIdAndPassword(scheduleRequestDto.getLoginId(), scheduleRequestDto.getPassword());
        // 유저의 인덱스 아이디 값 확인
        UserResponseDto userResponseDto = userService.findUser(scheduleRequestDto.getLoginId());

        // 일정 생성 후 확인
        Optional<ScheduleResponseDto> newSchedule = scheduleRepository.saveSchedule(userResponseDto.getUserId(), scheduleRequestDto);
        if(newSchedule.isEmpty()) throw new ScheduleCreationException("일정 생성에 오류가 발생했습니다.");
        return newSchedule.get();
    }

    @Override
    public PageResponseDto<ScheduleResponseDto> findAllSchedule(PageRequestDto pageRequestDto) {
        // 존재하는 유저인지 확인
        userService.validateUserExists(pageRequestDto.getLoginId());
        // 조건에 맞는 모든 일정 찾기 처리
        return scheduleRepository.findAllSchedules(pageRequestDto);
    }

    @Override
    public ScheduleResponseDto findOneSchedule(int schedule_id) {
        // 특정 일정 찾기 처리
        Optional<ScheduleResponseDto> scheduleResponseDto = scheduleRepository.findOneSchedule(schedule_id);
        if(scheduleResponseDto.isEmpty()) throw new ScheduleFindException("존재하지 않는 일정입니다.");
        return scheduleResponseDto.get();
    }

    @Override
    public ScheduleResponseDto modifySchedule(ModifyScheduleDto modifyScheduleDto) {
        // 아이디 비밀번호 검증
        userService.validateIdAndPassword(modifyScheduleDto.getLoginId(), modifyScheduleDto.getPassword());

        // 일정 수정 처리
        Optional<ScheduleResponseDto> scheduleResponseDto = scheduleRepository.modifySchedule(modifyScheduleDto);
        if(scheduleResponseDto.isEmpty()) throw new ScheduleModifyException("일정 수정 중 오류가 발생했습니다.");
        return scheduleResponseDto.get();
    }

    @Override
    public MessageResponseDto deleteSchedule(DeleteScheduleDto deleteScheduleDto) {
        // 아이디 비밀번호 검증
        userService.validateIdAndPassword(deleteScheduleDto.getLoginId(), deleteScheduleDto.getPassword());

        // 일정 삭제 처리
        if(!scheduleRepository.deleteSchedule(deleteScheduleDto.getScheduleId()))
            throw new ScheduleDeletionException("일정 삭제 중 오류가 발생했습니다.");
        return new MessageResponseDto(true, "일정 삭제를 완료했습니다.");
    }
}
