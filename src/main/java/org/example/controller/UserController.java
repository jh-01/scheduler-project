package org.example.controller;

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

    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody UserRequestDto newUser){
        return ResponseEntity.ok(userService.saveUser(newUser));
    }

    @GetMapping("/isDuplicate")
    public ResponseEntity<MessageResponseDto> validateLoginId(@RequestParam String loginId){
        return ResponseEntity.ok(userService.validateLoginIdExists(loginId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> findAllUsers(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime until
    ){
        if (since != null && until != null) {
            return ResponseEntity.ok(userService.findAllUsers(since, until));
        } else {
            return ResponseEntity.ok(userService.findAllUsers());
        }
    }

    @GetMapping("/one")
    public ResponseEntity<UserResponseDto> findUser(
            @RequestParam String loginId
    ){
        return ResponseEntity.ok(userService.findUser(loginId));
    }

    @PatchMapping("/modify/loginId")
    public ResponseEntity<UserResponseDto> modifyUserLoginId(
            @RequestBody ModifyUserLoginIdDto modifyUserLoginIdDto
    ){
        return ResponseEntity.ok(userService.modifyUserLoginId(modifyUserLoginIdDto));
    }

    @PatchMapping("/modify/info")
    public ResponseEntity<UserResponseDto> modifyUserInfo(
            @RequestBody ModifyUserInfoDto modifyUserInfoDto
    ){
        return ResponseEntity.ok(userService.modifyUserInfo(modifyUserInfoDto));
    }

    @PatchMapping("/modify/password")
    public ResponseEntity<Map<String, String>> modifyUserPassword(
            @RequestBody ModifyUserPasswordDto modifyUserPasswordDto
    ){
        userService.modifyUserPassword(modifyUserPasswordDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "비밀번호가 성공적으로 수정되었습니다.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<MessageResponseDto> deleteUser(
        @RequestBody DeleteUserDto deleteUserDto
    ){
        return ResponseEntity.ok(userService.deleteUSer(deleteUserDto));
    }

//    @GetMapping("validate")
//    public ResponseEntity<MessageResponseDto> validatePassword(
//            @RequestBody ValidatePasswordDto validatePasswordDto
//    ){
//        return ResponseEntity.ok(userService.validatePassword(validatePasswordDto.getLoginId(), validatePasswordDto.getPassword()));
//    }
}
