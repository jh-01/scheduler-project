package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.Schedule;
import java.time.LocalDate;

@Getter
@Setter
public class ScheduleDto {
    private int schedule_id;
    private int user_id;
    private String title;
    private String contents;
    private LocalDate createDate;
    private LocalDate updateDate;

    public ScheduleDto(Schedule schedule){
        this.schedule_id = schedule.getScheduleId();
        this.user_id = schedule.getUserId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
    }
}
