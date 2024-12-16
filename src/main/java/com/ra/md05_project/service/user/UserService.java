package com.ra.md05_project.service.user;

import com.ra.md05_project.dto.user.login.UserLoginRequestDTO;
import com.ra.md05_project.dto.user.login.UserLoginResponseDTO;
import com.ra.md05_project.dto.user.register.UserRequestDTO;
import com.ra.md05_project.dto.user.register.UserResponseDTO;
import com.ra.md05_project.dto.user.update.ChangePasswordDTO;
import com.ra.md05_project.dto.user.update.UserUpdateDTO;
import com.ra.md05_project.model.entity.ver1.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface UserService {
    UserResponseDTO create(UserRequestDTO userRequestDTO);

    UserLoginResponseDTO login(UserLoginRequestDTO userLoginRequestDTO);

    Page<User> findAll(String userName, Pageable pageable);

    void logout(String request);

    User update(User user);

    UserResponseDTO update(UserUpdateDTO userUpdateDTO,User user) throws IOException;

    User changeStatus(Long userId);

    User findById(Long userId);

    void handleChangePassword(ChangePasswordDTO changePasswordDto);

}
