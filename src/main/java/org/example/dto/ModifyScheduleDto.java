package org.example.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyScheduleDto {
    @NotBlank(message = "일정 수정 시 로그인 아이디 입력은 필수입니다.")
    private String loginId;
    @NotBlank(message = "일정 수정 시 비밀번호 입력은 필수입니다.")
    private String password;
    @Valid
    @NotNull(message = "일정 데이터는 필수입니다.")
    private ScheduleData scheduleData;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ScheduleData {
        @NotNull(message = "일정 아이디는 필수입니다.")
        @Min(value = 1, message = "수정할 일정 아이디를 전달해주세요.")
        private Integer scheduleId;
        @NotBlank(message = "일정 수정 시 제목 입력은 필수입니다.")
        private String title;
        @NotBlank(message = "일정 수정 시 내용 입력은 필수입니다.")
        private String contents;
    }
}