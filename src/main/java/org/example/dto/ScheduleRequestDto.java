package org.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleRequestDto {
    @NotBlank(message = "유저 로그인 아이디를 전달해주세요.")
    private String loginId;
    @NotBlank(message = "유저 비밀번호를 전달해주세요.")
    private String password;

    @NotBlank(message = "일정 생성 시 제목 입력은 필수입니다.")
    private String title;

    @NotBlank(message = "일정 생성 시 내용 입력은 필수입니다.")
    @Size(max = 200, message = "할일은 최대 200자 이내여야 합니다.")
    private String contents;
}
