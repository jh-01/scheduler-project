package org.example.controller;

import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

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
}
