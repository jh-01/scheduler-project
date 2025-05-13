package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PageRequestDto {
    private int page;
    private int size;
    private String loginId;
    private LocalDateTime since;
    private LocalDateTime until;

    public int getOffset() {
        return (page - 1) * size;
    }
}
