package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyUserInfoDto {
    private String loginId;
    private String email;
    private String nickname;
    private String password;
}
