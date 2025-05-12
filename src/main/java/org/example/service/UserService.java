package org.example.service;

import org.example.dto.*;
import org.example.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    UserResponseDto saveUser(UserRequestDto user);
    List<UserResponseDto> findAllUsers(LocalDateTime since, LocalDateTime until);
    List<UserResponseDto> findAllUsers();
    UserResponseDto findUser(String loginId);
    UserResponseDto modifyUserLoginId(ModifyUserLoginIdDto modifyUserLoginIdDto);
    UserResponseDto modifyUserInfo(ModifyUserInfoDto modifyUserInfoDto);
    UserResponseDto modifyUserPassword(ModifyUserPasswordDto modifyUserPasswordDto);
    MessageResponseDto validateLoginIdExists(String loginId);
    void validateUserExists(String loginId);
    void validateUserNotExists(String loginId);
    void validatePassword(String loginId, String password);
    MessageResponseDto deleteUSer(DeleteUserDto deleteUserDto);
}
