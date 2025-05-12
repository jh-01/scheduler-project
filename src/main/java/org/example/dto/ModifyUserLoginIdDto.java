package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyUserLoginIdDto {
    private String tempLoginId;
    private String password;
    private String newLoginId;
}
