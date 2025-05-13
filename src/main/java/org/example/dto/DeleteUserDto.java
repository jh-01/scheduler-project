package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteUserDto {
    @NotBlank(message = "삭제할 유저 아이디를 전달해주세요.")
    private String loginId;
    @NotBlank(message = "유저 삭제 시 비밀번호를 전달해주세요.")
    private String password;
}
