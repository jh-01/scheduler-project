package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyScheduleDto {
    private String password;
    private ScheduleData scheduleData;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ScheduleData {
        private String title;
        private String contents;
    }
}