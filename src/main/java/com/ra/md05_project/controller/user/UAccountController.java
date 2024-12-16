package com.ra.md05_project.controller.user;

import com.ra.md05_project.dto.user.register.UserResponseDTO;
import com.ra.md05_project.dto.user.update.ChangePasswordDTO;
import com.ra.md05_project.dto.user.update.UserUpdateDTO;
import com.ra.md05_project.exception.CustomException;
import com.ra.md05_project.model.entity.ver1.User;
import com.ra.md05_project.security.UserPrinciple;
import com.ra.md05_project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user/account")
public class UAccountController {
    @Autowired
    private UserService userService;

// 1 LẤY THÔNG TIN
    @GetMapping
    public ResponseEntity<UserResponseDTO> getAccountInfo(
            @AuthenticationPrincipal UserPrinciple userPrincipal
    ) {
        User currentUser = userPrincipal.getUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            UserResponseDTO userResponseDTO =
                    userToUserResponseDTO(currentUser);
            return ResponseEntity.ok( userResponseDTO);
        }
    }

// 2 UPDATE THÔNG TIN
    @PutMapping
    public ResponseEntity<UserResponseDTO> updateAccount(
            @ModelAttribute UserUpdateDTO user,
            @AuthenticationPrincipal UserPrinciple userPrincipal) throws IOException {
        User currentUser = userPrincipal.getUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            UserResponseDTO updatedUser= userService.update(user,currentUser);
            return ResponseEntity.ok(updatedUser);
        }
    }

//  3  CHANGE
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordDTO changePasswordDto,
            @AuthenticationPrincipal UserPrinciple userPrincipal
    ) throws CustomException {
        User currentUser = userPrincipal.getUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            userService.handleChangePassword(changePasswordDto);
            return new ResponseEntity<>("Change password successfully !!!", HttpStatus.OK);
        }
    }

    private static UserResponseDTO userToUserResponseDTO(User currentUser) {
        return UserResponseDTO.builder()
                .userId(currentUser.getId())
                .username(currentUser.getUsername())
                .email(currentUser.getEmail())
                .fullName(currentUser.getFullName())
                .phone(currentUser.getPhoneNumber())
                .status(currentUser.getStatus())
                .avatar(currentUser.getAvatar())
                .createdAt(currentUser.getCreatedAt())
                .updatedAt(currentUser.getUpdatedAt())
                .isDeleted(false)
                .roles(currentUser.getRoles())
                .build();
    }
}

