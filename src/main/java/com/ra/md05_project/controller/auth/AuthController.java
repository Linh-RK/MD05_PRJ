package com.ra.md05_project.controller.auth;

import com.ra.md05_project.dto.user.*;
import com.ra.md05_project.dto.user.login.UserLoginRequestDTO;
import com.ra.md05_project.dto.user.login.UserLoginResponseDTO;
import com.ra.md05_project.dto.user.register.UserRequestDTO;
import com.ra.md05_project.dto.user.register.UserResponseDTO;
import com.ra.md05_project.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDTO> signUp(@Valid @ModelAttribute UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userService.create(userRequestDTO), HttpStatus.CREATED);
    }
    @PostMapping("/sign-in")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO userLoginRequestDTO){
        return new ResponseEntity<>(userService.login(userLoginRequestDTO), HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token)  {
    // Xử lý token, loại bỏ "Bearer " nếu có
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);  // Lấy phần token sau "Bearer "
        }
        userService.logout(token);
        return new ResponseEntity<>("Logout", HttpStatus.OK);
    }
}
