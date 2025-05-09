package org.example.repository;

import org.example.dto.MessageResponseDto;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
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
    Optional<UserResponseDto> modifyUser();
    Optional<MessageResponseDto> deleteUser();
}
