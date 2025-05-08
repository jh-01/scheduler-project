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

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> saveSchedule(
            @RequestBody ScheduleRequestDto scheduleRequestDto
    ){
        return new ResponseEntity<>(scheduleService.saveSchedule(scheduleRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedule(
            @RequestParam(required = false) Integer user_id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime updateDate
    ) {
        if (user_id != null && updateDate != null) {
            // 두 조건 다 있을 때
            return ResponseEntity.ok(scheduleService.findAllSchedule(user_id, updateDate));
        } else if (user_id != null) {
            return ResponseEntity.ok(scheduleService.findAllSchedule(user_id));
        } else if (updateDate != null) {
            return ResponseEntity.ok(scheduleService.findAllSchedule(updateDate));
        } else {
            return ResponseEntity.ok(scheduleService.findAllSchedule());
        }
    }


    @GetMapping("/one")
    public ResponseEntity<ScheduleResponseDto> findOneSchedule(@RequestParam int schedule_id){
        return new ResponseEntity<>(scheduleService.findOnseSchedule(schedule_id), HttpStatus.CREATED);
    }
}
