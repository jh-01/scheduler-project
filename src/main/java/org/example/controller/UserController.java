package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.*;
import org.example.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    // 유저 생성
    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody @Valid UserRequestDto newUser){
        return ResponseEntity.ok(userService.saveUser(newUser));
    }

    // 아이디 중복 확인
    @GetMapping("/isDuplicate")
    public ResponseEntity<MessageResponseDto> validateLoginId(@RequestParam String loginId){
        return ResponseEntity.ok(userService.validateLoginIdExists(loginId));
    }

    // 다중 유저 조회
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> findAllUsers(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime until
    ){
        return ResponseEntity.ok(userService.findAllUsers(since, until));
    }

    // 개별 유저 조회
    @GetMapping("/one")
    public ResponseEntity<UserResponseDto> findUser(
            @RequestParam(required = true) String loginId
    ){
        return ResponseEntity.ok(userService.findUser(loginId));
    }

    // 유저 아이디 수정
    @PatchMapping("/modify/loginId")
    public ResponseEntity<UserResponseDto> modifyUserLoginId(
            @RequestBody @Valid ModifyUserLoginIdDto modifyUserLoginIdDto
    ){
        return ResponseEntity.ok(userService.modifyUserLoginId(modifyUserLoginIdDto));
    }

    // 유저 정보 수정
    @PatchMapping("/modify/info")
    public ResponseEntity<UserResponseDto> modifyUserInfo(
            @RequestBody @Valid ModifyUserInfoDto modifyUserInfoDto
    ){
        return ResponseEntity.ok(userService.modifyUserInfo(modifyUserInfoDto));
    }

    // 유저 비밀번호 수정
    @PatchMapping("/modify/password")
    public ResponseEntity<Map<String, String>> modifyUserPassword(
            @RequestBody @Valid ModifyUserPasswordDto modifyUserPasswordDto
    ){
        userService.modifyUserPassword(modifyUserPasswordDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "비밀번호가 성공적으로 수정되었습니다.");
        return ResponseEntity.ok(response);
    }

    // 유저 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<MessageResponseDto> deleteUser(
        @RequestBody @Valid DeleteUserDto deleteUserDto
    ){
        return ResponseEntity.ok(userService.deleteUSer(deleteUserDto));
    }
}
