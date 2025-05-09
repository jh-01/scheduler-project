package org.example.controller;

import org.example.dto.MessageResponseDto;
import org.example.dto.ModifyScheduleDto;
import org.example.dto.ScheduleRequestDto;
import org.example.dto.ScheduleResponseDto;
import org.example.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedule(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime until
    ) {
        if (userId != null && since != null && until != null) {
            // 두 조건 다 있을 때
            return ResponseEntity.ok(scheduleService.findAllSchedule(userId, since, until));
        } else if (userId != null) {
            return ResponseEntity.ok(scheduleService.findAllSchedule(userId));
        } else if (since != null && until != null) {
            return ResponseEntity.ok(scheduleService.findAllSchedule(since, until));
        } else {
            return ResponseEntity.ok(scheduleService.findAllSchedule());
        }
    }

    // 일정 하나 조회
    @GetMapping("/one")
    public ResponseEntity<ScheduleResponseDto> findOneSchedule(@RequestParam int scheduleId){
        return new ResponseEntity<>(scheduleService.findOneSchedule(scheduleId), HttpStatus.FOUND);
    }

    // 일정 수정
    @PutMapping("/modify")
    public ResponseEntity<ScheduleResponseDto> modifySchedule(
            @RequestParam int scheduleId,
            @RequestBody ModifyScheduleDto modifyScheduleDto
    ){
        return ResponseEntity.ok(scheduleService.modifySchedule(scheduleId, modifyScheduleDto));
    }

    // 일정 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<MessageResponseDto> deleteSchedule(
            @RequestParam int scheduleId,
            @RequestBody String password
    ) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(scheduleId, password));
    }
}
