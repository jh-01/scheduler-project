package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Integer scheduleId;
    private String nickname;
    private String title;
    private String contents;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
