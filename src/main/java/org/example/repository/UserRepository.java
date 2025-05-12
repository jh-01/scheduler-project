package org.example.repository;

import org.example.dto.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<UserResponseDto> saveUser(UserRequestDto user);
    List<UserResponseDto> findAllUsers(LocalDateTime since, LocalDateTime until);
    List<UserResponseDto> findAllUsers();
    Optional<UserResponseDto> findUser(int userId);
    Optional<UserResponseDto> findUser(String loginId);
    Optional<UserResponseDto> modifyUserLoginId(ModifyUserLoginIdDto modifyUserLoginIdDto);
    Optional<UserResponseDto> modifyUserInfo(ModifyUserInfoDto modifyUserInfoDto);
    Optional<UserResponseDto> modifyUserPassword(ModifyUserPasswordDto modifyUserPasswordDto);
    boolean existsByLoginId(String loginId);
    boolean validatePassword(String loginId, String password);
    boolean deleteUser(DeleteUserDto deleteUserDto);
}
