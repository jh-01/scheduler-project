package org.example.controller;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.example.dto.ScheduleRequestDto;
import org.example.dto.ScheduleResponseDto;
import org.example.entity.Schedule;
import org.example.repository.JdbcTemplateScheduleRepository;
import org.example.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedule (@RequestParam int user_id) {
        return null;
    }

    @GetMapping("/{schedule_id}")
    public ResponseEntity<ScheduleResponseDto> findOneSchedule(@RequestParam int schedule_id){
        return null;
    }
}
