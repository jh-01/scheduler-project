package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {
    private int userId;
    private String loginId;
    private String email;
    private String nickname;
    private String password;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
