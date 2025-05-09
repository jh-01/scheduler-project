package org.example.controller;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.example.dto.ScheduleRequestDto;
import org.example.dto.ScheduleResponseDto;
import org.example.entity.Schedule;
import org.example.repository.JdbcTemplateScheduleRepository;
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
            @RequestParam(required = false) Integer user_id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime until
    ) {
        if (user_id != null && since != null && until != null) {
            // 두 조건 다 있을 때
            return ResponseEntity.ok(scheduleService.findAllSchedule(user_id, since, until));
        } else if (user_id != null) {
            return ResponseEntity.ok(scheduleService.findAllSchedule(user_id));
        } else if (since != null && until != null) {
            return ResponseEntity.ok(scheduleService.findAllSchedule(since, until));
        } else {
            return ResponseEntity.ok(scheduleService.findAllSchedule());
        }
    }

    // 일정 하나 조회
    @GetMapping("/one")
    public ResponseEntity<ScheduleResponseDto> findOneSchedule(@RequestParam int schedule_id){
        return new ResponseEntity<>(scheduleService.findOnseSchedule(schedule_id), HttpStatus.CREATED);
    }
}
