package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.entity.Schedule;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private int schedule_id;
    private int user_id;
    private String title;
    private String contents;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
