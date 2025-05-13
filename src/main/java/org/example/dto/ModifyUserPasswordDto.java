package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyUserPasswordDto {
    @NotBlank(message = "수정할 유저 아이디를 입력해주세요.")
    private String loginId;
    @NotBlank(message = "수정할 기존 유저 비밀번호 입력해주세요.")
    private String tempPassword;

    @NotBlank(message = "수정할 새 유저 비밀번호 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&~_^])[A-Za-z\\d@$!%*#?&~_^]{8,}$",
            message = "비밀번호는 8자 이상이며, 영문자, 숫자, 특수문자를 포함해야 합니다."
    )
    private String newPassword;
}