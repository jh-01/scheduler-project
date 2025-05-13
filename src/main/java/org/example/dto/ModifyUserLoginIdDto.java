package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyUserLoginIdDto {
    @NotBlank(message = "수정할 기존 유저 아이디를 입력해주세요.")
    private String tempLoginId;

    @NotBlank(message = "수정할 기존 유저 비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "수정할 새 유저 아이디를 입력해주세요.")
    @Size(min = 4, max = 20, message = "아이디는 4~20자여야 합니다.")
    private String newLoginId;
}
