package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleRequestDto {
    private int user_id;
    private String title;
    private String contents;
    private String password;
}
