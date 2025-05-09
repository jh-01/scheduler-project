package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.Schedule;
import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleDto {
    private int scheduleId;
    private int userID;
    private String title;
    private String contents;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public ScheduleDto(Schedule schedule){
        this.scheduleId = schedule.getScheduleId();
        this.userID = schedule.getUserId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
    }
}
