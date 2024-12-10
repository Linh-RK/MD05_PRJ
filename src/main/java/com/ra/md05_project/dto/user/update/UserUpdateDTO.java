package com.ra.md05_project.dto.user.update;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
//@UniqueUpdate(fieldName = "email", idField = "id", entityClass = User.class, message = "Size name must be unique")
public class UserUpdateDTO {
    private Long id;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String fullName;
    private MultipartFile avatar;
    @NotBlank
    private String phone;
}
