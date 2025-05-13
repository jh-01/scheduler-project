package org.example.controller;

import org.example.dto.*;
import org.example.service.ScheduleService;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    // 일정 추가
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> saveSchedule(
            @RequestBody ScheduleRequestDto scheduleRequestDto
    ){
        return new ResponseEntity<>(scheduleService.saveSchedule(scheduleRequestDto), HttpStatus.CREATED);
    }

    // 모든 일정 조회
    @GetMapping("/all")
    public ResponseEntity<PageResponseDto<ScheduleResponseDto>> findAllSchedule(
            @RequestBody PageRequestDto pageRequestDto
    ) {
        return ResponseEntity.ok(scheduleService.findAllSchedule(pageRequestDto));
    }

    // 일정 하나 조회
    @GetMapping("/one")
    public ResponseEntity<ScheduleResponseDto> findOneSchedule(@RequestParam int scheduleId){
        return new ResponseEntity<>(scheduleService.findOneSchedule(scheduleId), HttpStatus.FOUND);
    }

    // 일정 수정
    @PatchMapping("/modify")
    public ResponseEntity<ScheduleResponseDto> modifySchedule(
            @RequestParam int scheduleId,
            @RequestBody ModifyScheduleDto modifyScheduleDto
    ){
        return ResponseEntity.ok(scheduleService.modifySchedule(scheduleId, modifyScheduleDto));
    }

    // 일정 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<MessageResponseDto> deleteSchedule(
            @RequestBody DeleteScheduleDto deleteScheduleDto
    ) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(deleteScheduleDto));
    }
}
