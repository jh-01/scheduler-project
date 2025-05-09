package org.example.service;

import org.example.dto.MessageResponseDto;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
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
//    UserResponseDto modifyUser();
//    MessageResponseDto deleteUser();
}
