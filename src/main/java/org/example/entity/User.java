package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {
    @Id
    private int userId;
    private String loginId;
    private String email;
    private String nickname;
    private String password;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public User(String password) {
        this.password = password;
    }
}
