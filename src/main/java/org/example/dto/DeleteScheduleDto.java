package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteScheduleDto {
    private int scheduleId;
    private String loginId;
    private String password;
}
