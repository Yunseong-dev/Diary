package com.diary.auth.controller;

import com.diary.auth.dto.CreateUserDto;
import com.diary.auth.dto.LoginUserDto;
import com.diary.auth.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class AuthController {
    public UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(
            @RequestBody CreateUserDto dto
    ) {
        return userService.createUser(dto);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(
            @RequestBody LoginUserDto dto
    ) {
        return userService.loginUser(dto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> loginUser(
            HttpServletRequest request
    ) {
        String refreshToken = request.getHeader("Authorization").replace("Bearer ", "");
        return userService.refreshToken(refreshToken);
    }

}
