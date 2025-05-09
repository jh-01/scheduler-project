package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto {
    private int userId;
    private String loginId;
    private String email;
    private String nickname;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
