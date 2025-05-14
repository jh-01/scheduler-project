package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.*;
import org.example.exception.*;
import org.example.repository.JdbcTemplateUserRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

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
        validateUserNotExists(user.getLoginId());
        validatePasswordPolicy(user.getPassword());
        Optional<UserResponseDto> newUser = userRepository.saveUser(user);
        if(newUser.isEmpty()) throw new UserCreationException("유저 생성에 오류가 발생했습니다.");
        return newUser.get();
    }

    @Override
    public List<UserResponseDto> findAllUsers(LocalDateTime since, LocalDateTime until) {
        return userRepository.findAllUsers(since, until);
    }

    @Override
    public UserResponseDto findUser(String loginId) {
        Optional<UserResponseDto> user = userRepository.findUser(loginId);
        if(user.isEmpty()) throw new UserFindException("존재하지 않는 유저입니다.");
        return user.get();
    }

    @Override
    public UserResponseDto modifyUserLoginId(ModifyUserLoginIdDto modifyUserLoginIdDto) {
        // 수정하려고 받은 새 로그인 아이디가 중복된 아이디인지 확인
        validateUserNotExists(modifyUserLoginIdDto.getNewLoginId());
        // 패스워드 검증
        validateIdAndPassword(modifyUserLoginIdDto.getTempLoginId(), modifyUserLoginIdDto.getPassword());

        // 유저 아이디 수정 처리
        Optional<UserResponseDto> modifiedUser = userRepository.modifyUserLoginId(modifyUserLoginIdDto);
        if(modifiedUser.isEmpty()) throw new UserModifyException("유저 로그인 아이디 수정에 실패했습니다.");
        return modifiedUser.get();
    }

    @Override
    public UserResponseDto modifyUserInfo(ModifyUserInfoDto modifyUserInfoDto) {
        // 아이디 패스워드 검증
        validateIdAndPassword(modifyUserInfoDto.getLoginId(), modifyUserInfoDto.getPassword());

        // 유저 정보 수정 처리
        Optional<UserResponseDto> modifiedUser = userRepository.modifyUserInfo(modifyUserInfoDto);
        if(modifiedUser.isEmpty()) throw new UserModifyException("유저 정보 수정에 실패했습니다.");
        return modifiedUser.get();
    }

    @Override
    public UserResponseDto modifyUserPassword(ModifyUserPasswordDto modifyUserPasswordDto) {
        // 아이디 패스워드 검증
        validateIdAndPassword(modifyUserPasswordDto.getLoginId(), modifyUserPasswordDto.getTempPassword());
        // 수정할 패스워드가 규칙을 지켰는지 검증
        validatePasswordPolicy(modifyUserPasswordDto.getNewPassword());

        // 유저 비밀번호 수정 처리
        Optional<UserResponseDto> modifiedUser = userRepository.modifyUserPassword(modifyUserPasswordDto);
        if(modifiedUser.isEmpty()) throw new UserModifyException("유저 비밀번호 수정에 실패했습니다.");
        return modifiedUser.get();
    }

    @Override
    public MessageResponseDto validateLoginIdExists(String loginId){
        if(userRepository.existsByLoginId(loginId)) throw new DuplicatedLoginIdException("중복 아이디입니다.");
        return new MessageResponseDto(true, "사용 가능 아이디입니다.");
    }

    @Override
    public void validateUserExists(String loginId){
        if (!userRepository.existsByLoginId(loginId)) {
            throw new UserFindException("해당 유저가 존재하지 않습니다.");
        }
    }

    @Override
    public void validateUserExists(int userId){
        if (!userRepository.existsByUserId(userId)) {
            throw new UserFindException("해당 유저가 존재하지 않습니다.");
        }
    }

    @Override
    public void validateUserNotExists(String loginId) {
        if (userRepository.existsByLoginId(loginId)) {
            throw new DuplicatedLoginIdException("이미 사용 중인 로그인 아이디입니다.");
        }
    }

    @Override
    public void validateIdAndPassword(String loginId, String password){
        validateUserExists(loginId);
        if (!userRepository.validatePassword(loginId, password)) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public MessageResponseDto deleteUSer(DeleteUserDto deleteUserDto) {
        validateIdAndPassword(deleteUserDto.getLoginId(), deleteUserDto.getPassword());
        if(!userRepository.deleteUser(deleteUserDto)) throw new UserDeletionException("유저 삭제에 실패했습니다.");
        return new MessageResponseDto(true, "유저 삭제 완료했습니다.");
    }

    @Override
    public void validatePasswordPolicy(String password) {
        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&^])[A-Za-z\\d@$!%*#?&^]{8,}$";
        if (!password.matches(pattern)) {
            throw new InvalidPasswordException("비밀번호는 8자리 이상이어야 하고, 영어, 숫자, 특수문자를 모두 포함해야 합니다.");
        }
    }
}
