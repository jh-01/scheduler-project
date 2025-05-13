package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Integer userId;
    private String loginId;
    private String email;
    private String nickname;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
