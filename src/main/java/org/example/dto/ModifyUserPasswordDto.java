package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyUserPasswordDto {
    private String loginId;
    private String tempPassword;
    private String newPassword;
}
