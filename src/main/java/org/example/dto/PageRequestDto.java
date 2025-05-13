package org.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class PageRequestDto {
    @NotNull(message = "일정 아이디는 필수입니다.")
    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
    private Integer page;
    @NotNull(message = "일정 아이디는 필수입니다.")
    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
    private Integer size;
    private String loginId;
    private LocalDateTime since;
    private LocalDateTime until;

    public int getOffset() {
        return (page - 1) * size;
    }
}
