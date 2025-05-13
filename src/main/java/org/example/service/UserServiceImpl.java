package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.*;
import org.example.repository.JdbcTemplateUserRepository;
import org.example.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(JdbcTemplateUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto user) {
        validateUserExists(user.getLoginId());
        Optional<UserResponseDto> newUser = userRepository.saveUser(user);
        if(newUser.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저 생성에 오류가 발생했습니다.");
        return newUser.get();
    }

    @Override
    public List<UserResponseDto> findAllUsers(LocalDateTime since, LocalDateTime until) {
        return userRepository.findAllUsers(since, until);
    }

    @Override
    public UserResponseDto findUser(String loginId) {
        Optional<UserResponseDto> user = userRepository.findUser(loginId);
        if(user.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다..");
        return user.get();
    }

    @Override
    public UserResponseDto modifyUserLoginId(ModifyUserLoginIdDto modifyUserLoginIdDto) {
        validateUserExists(modifyUserLoginIdDto.getTempLoginId());
        validateUserNotExists(modifyUserLoginIdDto.getNewLoginId());
        validatePassword(modifyUserLoginIdDto.getTempLoginId(), modifyUserLoginIdDto.getPassword());

        Optional<UserResponseDto> modifiedUser = userRepository.modifyUserLoginId(modifyUserLoginIdDto);
        if(modifiedUser.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저 로그인 아이디 수정에 실패했습니다.");
        return modifiedUser.get();
    }

    @Override
    public UserResponseDto modifyUserInfo(ModifyUserInfoDto modifyUserInfoDto) {
        // 존재하는 유저인지 먼저 확인하고 비밀번호 검증
        validateUserExists(modifyUserInfoDto.getLoginId());
        validatePassword(modifyUserInfoDto.getLoginId(), modifyUserInfoDto.getPassword());

        Optional<UserResponseDto> modifiedUser = userRepository.modifyUserInfo(modifyUserInfoDto);
        if(modifiedUser.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저 정보 수정에 실패했습니다.");
        return modifiedUser.get();
    }

    @Override
    public UserResponseDto modifyUserPassword(ModifyUserPasswordDto modifyUserPasswordDto) {
        // 존재하는 유저인지 먼저 확인하고 비밀번호 검증
        validateUserExists(modifyUserPasswordDto.getLoginId());
        validatePassword(modifyUserPasswordDto.getLoginId(), modifyUserPasswordDto.getTempPassword());

        Optional<UserResponseDto> modifiedUser = userRepository.modifyUserPassword(modifyUserPasswordDto);
        if(modifiedUser.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "유저 비밀번호 수정에 실패했습니다.");
        return modifiedUser.get();
    }

    @Override
    public MessageResponseDto validateLoginIdExists(String loginId){
        if(userRepository.existsByLoginId(loginId)) return new MessageResponseDto("중복 아이디");
        return new MessageResponseDto("사용 가능 아이디");
    }

    @Override
    public void validateUserExists(String loginId){
        if (!userRepository.existsByLoginId(loginId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 유저가 존재하지 않습니다.");
        }
    }

    @Override
    public void validateUserNotExists(String loginId) {
        if (userRepository.existsByLoginId(loginId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용 중인 로그인 아이디입니다.");
        }
    }

    @Override
    public void validatePassword(String loginId, String password){
        if (!userRepository.validatePassword(loginId, password)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 유저가 존재하지 않습니다.");
        }
    }

    @Override
    public MessageResponseDto deleteUSer(DeleteUserDto deleteUserDto) {
        validateUserExists(deleteUserDto.getLoginId());
        validatePassword(deleteUserDto.getLoginId(), deleteUserDto.getPassword());
        if(!userRepository.deleteUser(deleteUserDto)) throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "유저 삭제에 실패했습니다.");
        return new MessageResponseDto("삭제 완료");
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
