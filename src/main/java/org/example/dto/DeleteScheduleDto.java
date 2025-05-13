package org.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteScheduleDto {
    @NotNull(message = "일정 아이디는 필수입니다.")
    @Min(value = 1, message = "삭제할 일정 아이디를 전달해주세요.")
    private Integer scheduleId;
    @NotBlank(message = "일정 삭제 시 로그인 아이디 입력은 필수입니다.")
    private String loginId;
    @NotBlank(message = "일정 삭제 시 비밀번호 입력은 필수입니다.")
    private String password;
}
