package com.ra.md05_project.service.user;

import com.ra.md05_project.dto.user.*;
import com.ra.md05_project.dto.user.login.UserLoginRequestDTO;
import com.ra.md05_project.dto.user.login.UserLoginResponseDTO;
import com.ra.md05_project.dto.user.register.UserRequestDTO;
import com.ra.md05_project.dto.user.register.UserResponseDTO;
import com.ra.md05_project.dto.user.update.ChangePasswordDTO;
import com.ra.md05_project.dto.user.update.UserUpdateDTO;
import com.ra.md05_project.exception.CustomException;
import com.ra.md05_project.model.entity.*;
import com.ra.md05_project.repository.RoleRepository;
import com.ra.md05_project.repository.TokenRepository;
import com.ra.md05_project.repository.UserRepository;
import com.ra.md05_project.security.UserPrinciple;
import com.ra.md05_project.security.jwt.JwtProvider;
import com.ra.md05_project.model.entity.ver1.Token;
import com.ra.md05_project.model.entity.ver1.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RoleRepository roleRepository;
//    @Autowired
//    private UploadFileService uploadFileService;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO create(UserRequestDTO userRequestDTO) {
        User user = User.builder()
                .username(userRequestDTO.getUsername())
                .fullName(userRequestDTO.getFullName())
                .password(new BCryptPasswordEncoder().encode(userRequestDTO.getPassword()))
                .status(true)
                .createdAt(LocalDate.now())
                .isDeleted(false)
                .build();

        User userNew = userRepository.save(user);

        return UserResponseDTO.builder()
                .userId(userNew.getId())
                .username(userNew.getUsername())
                .email(userNew.getEmail())
                .fullName(userNew.getFullName())
                .status(userNew.getStatus())
                .avatar(userNew.getAvatar())
                .phone(userNew.getPhoneNumber())
                .createdAt(LocalDate.now())
                .isDeleted(false)
                .roles(userNew.getRoles())
                .build();
    }


    @Override
    public UserLoginResponseDTO login(UserLoginRequestDTO userLoginRequestDTO) {
        Authentication authentication;
        authentication = authenticationProvider
                .authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestDTO.getUsername(), userLoginRequestDTO.getPassword()));
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        String token = jwtProvider.generateToken(userPrinciple);
        // Lưu token vào database
        Token tokenEntity = new Token();
        tokenEntity.setToken(token);
        tokenEntity.setUser(userPrinciple.getUser());
        tokenEntity.setCreatedAt(LocalDateTime.now());
        tokenEntity.setExpiredAt(LocalDateTime.now().plusDays(1)); // ví dụ thời hạn token là 1 ngày
        tokenRepository.save(tokenEntity);

        return UserLoginResponseDTO.builder()
                .accessToken(token)
                .typeToken("Bearer")
                .userId(userPrinciple.getUser().getId())
                .username(userPrinciple.getUsername())
                .roles(userPrinciple.getUser().getRoles())
                .build();
    }

    @Override
    public Page<User> findAll(String searchTerm, Pageable pageable) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return userRepository.findAll(pageable);
        } else {
            return userRepository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(searchTerm, searchTerm, pageable);
        }
    }

    @Override
    public void logout(LogoutRequest request) {
        Token token = tokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new NoSuchElementException("Token not found"));
        token.setValid(false);  // Đánh dấu token là vô hiệu hóa
        tokenRepository.save(token);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserResponseDTO update( UserUpdateDTO userRequestDTO, User user) {
        // Retrieve existing user by id
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        // Update the fields
        existingUser.setEmail(userRequestDTO.getEmail());
        existingUser.setFullName(userRequestDTO.getFullName());
        existingUser.setPhoneNumber(userRequestDTO.getPhone());
        existingUser.setUpdatedAt(LocalDate.now());

        // Optionally handle avatar upload
//        if (userRequestDTO.getAvatar() != null  && userRequestDTO.getAvatar().getSize()>0) {
//            String avatar = uploadFileService.upload(userRequestDTO.getAvatar());
//            existingUser.setAvatar(avatar);
//        }
        // Save the updated user
        User updatedUser = userRepository.save(existingUser);

        // Return response DTO
        return UserResponseDTO.builder()
                .userId(updatedUser.getId())
                .username(updatedUser.getUsername())
                .email(updatedUser.getEmail())
                .fullName(updatedUser.getFullName())
                .status(updatedUser.getStatus())
                .avatar(updatedUser.getAvatar())
                .phone(updatedUser.getPhoneNumber())
                .createdAt(updatedUser.getCreatedAt())
                .updatedAt(LocalDate.now())
                .isDeleted(updatedUser.getIsDeleted())
                .roles(updatedUser.getRoles())
                .build();
    }

    @Override
    public User changeStatus(Long userId) {
        User user = userRepository.findUserByUserId(userId);
         user.setStatus(!user.getStatus());
        return userRepository.save(user);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findUserByUserId(userId);
    }

    @Override
    public void handleChangePassword(ChangePasswordDTO changePasswordDto) throws CustomException {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (passwordEncoder.matches(changePasswordDto.getOldPassword(), userPrinciple.getUser().getPassword()))
        {
            if (changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())) {
                User user = userPrinciple.getUser();
                user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
                user.setUpdatedAt(LocalDate.now());
                userRepository.save(user);
            } else {
                throw new CustomException("Confirm password not match");
            }
        }
        else {
            throw new CustomException("wrong password");
        }
    }

}
