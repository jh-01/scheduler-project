package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Schedule {
    private int scheduleId;
    private int userId;
    private String title;
    private String contents;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}