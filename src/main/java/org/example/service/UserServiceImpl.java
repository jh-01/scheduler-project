package org.example.service;

import org.example.dto.MessageResponseDto;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.entity.User;
import org.example.repository.JdbcTemplateScheduleRepository;
import org.example.repository.JdbcTemplateUserRepository;
import org.example.repository.ScheduleRepository;
import org.example.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(JdbcTemplateUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto user) {
        Optional<UserResponseDto> newUser = userRepository.saveUser(user);
        if(newUser.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저 생성에 오류가 발생했습니다.");
        return newUser.get();
    }

    @Override
    public List<UserResponseDto> findAllUsers(LocalDateTime since, LocalDateTime until) {
        return userRepository.findAllUsers();
    }

    @Override
    public List<UserResponseDto> findAllUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public UserResponseDto findUser(String loginId) {
        Optional<UserResponseDto> user = userRepository.findUser(loginId);
        if(user.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 유저입니다.");
        return user.get();
    }

//    @Override
//    public UserResponseDto modifyUser() {
//        return null;
//    }
//
//    @Override
//    public MessageResponseDto deleteUser() {
//        return null;
//    }
}
